/*
 * Copyright 2015 dactiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.dactiv.universe.session.http;

import com.github.dactiv.universe.session.ExpiringSession;
import com.github.dactiv.universe.session.Session;
import com.github.dactiv.universe.session.SessionRepository;
import com.github.dactiv.universe.session.http.cookie.CookieHttpSessionStrategy;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一 http session 管理 filter
 *
 * @author maurice
 */
public class SessionRepositoryFilter<S extends ExpiringSession> extends OncePerRequestFilter {

    public static final String SESSION_REPOSITORY_ATTR = SessionRepository.class.getName();
    public static final String INVALID_SESSION_ID_ATTR = SESSION_REPOSITORY_ATTR + ".invalidSessionId";

    private final SessionRepository<S> sessionRepository;

    private ServletContext servletContext;

    private MultiHttpSessionStrategy httpSessionStrategy = new CookieHttpSessionStrategy();

    private OnSessionFilterComplete onSessionFilterComplete;

    /**
     * 构造方法
     *
     * @param sessionRepository Session 存储库接口
     */
    public SessionRepositoryFilter(SessionRepository<S> sessionRepository) {
        if (sessionRepository == null) {
            throw new IllegalArgumentException("Session 存储库接口不能为 null");
        }
        this.sessionRepository = sessionRepository;
    }

    /**
     * 设置当 session filter 完成时触发完成事件的接口
     *
     * @param onSessionFilterComplete 接口实现
     */
    public void setOnSessionFilterComplete(OnSessionFilterComplete onSessionFilterComplete) {
        this.onSessionFilterComplete = onSessionFilterComplete;
    }

    /**
     * 设置 http session 策略
     *
     * @param httpSessionStrategy http session 策略实现
     */
    public void setHttpSessionStrategy(MultiHttpSessionStrategy httpSessionStrategy) {
        if (httpSessionStrategy == null) {
            throw new IllegalArgumentException("http session 策略不能为 null");
        }
        this.httpSessionStrategy = httpSessionStrategy;
    }

    /**
     * 设置 servlet 上下文
     *
     * @param servletContext  servlet 上下文
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        request.setAttribute(SESSION_REPOSITORY_ATTR, this.sessionRepository);

        SessionRepositoryRequestWrapper wrappedRequest = new SessionRepositoryRequestWrapper(request, response, this.servletContext);
        SessionRepositoryResponseWrapper wrappedResponse = new SessionRepositoryResponseWrapper(wrappedRequest, response);

        HttpServletRequest strategyRequest = this.httpSessionStrategy.wrapRequest(wrappedRequest, wrappedResponse);
        HttpServletResponse strategyResponse = this.httpSessionStrategy.wrapResponse(wrappedRequest, wrappedResponse);

        try {
            filterChain.doFilter(strategyRequest, strategyResponse);
        } finally {
            wrappedRequest.commitSession();
            if (onSessionFilterComplete != null) {
                onSessionFilterComplete.onComplete(strategyRequest, strategyResponse);
            }
        }
    }

    /**
     * 自定义 request 包装器
     */
    private final class SessionRepositoryRequestWrapper extends HttpServletRequestWrapper {

        // 放置在 session 的属性名
        private final String CURRENT_SESSION_ATTR = HttpServletRequestWrapper.class.getName();
        // 判断当前 session 是否跟 request 里面的 session id 相等，true 为是，否则 false
        private Boolean requestedSessionIdValid;
        // 判断当前 session 是否超时，true 为是，否则 false
        private boolean requestedSessionInvalidated;
        // http servlet response
        private final HttpServletResponse response;
        // servlet 上下文
        private final ServletContext servletContext;

        /**
         * 构造方法
         *
         * @param request http servlet request
         * @param response http servlet response
         * @param servletContext servlet 上下文
         */
        private SessionRepositoryRequestWrapper(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
            super(request);
            this.response = response;
            this.servletContext = servletContext;
        }

        /**
         * 提交会话
         */
        private void commitSession() {
            // 获取当前 session
            HttpSessionWrapper wrappedSession = getCurrentSession();

            if (wrappedSession == null) {
                // 如果超时，触发 onInvalidateSession 方法
                if (isInvalidateClientSession()) {
                    SessionRepositoryFilter.this.httpSessionStrategy.onInvalidateSession(this, this.response);
                }
            } else {
                // 获取当前 session
                S session = wrappedSession.getSession();
                //保存当前 session
                SessionRepositoryFilter.this.sessionRepository.save(session);
                // 如果当前 session 是新创建的，触发 onNewSession 方法
                if (!isRequestedSessionIdValid() || !session.getId().equals(getRequestedSessionId())) {
                    SessionRepositoryFilter.this.httpSessionStrategy.onNewSession(session, this, this.response);
                }
            }
        }

        /**
         * 获取当前 session
         *
         * @return 当前 session
         */
        @SuppressWarnings("unchecked")
        private HttpSessionWrapper getCurrentSession() {
            return (HttpSessionWrapper) getAttribute(this.CURRENT_SESSION_ATTR);
        }

        /**
         * 设置当前 session
         *
         * @param currentSession http session 包装器
         */
        private void setCurrentSession(HttpSessionWrapper currentSession) {
            if (currentSession == null) {
                removeAttribute(this.CURRENT_SESSION_ATTR);
            }
            else {
                setAttribute(this.CURRENT_SESSION_ATTR, currentSession);
            }
        }

        /**
         * 更换 session id
         *
         * @return 更换后的 session id
         */
        public String changeSessionId() {
            // 获取 session
            HttpSession session = getSession(false);
            // 如果获取不到，什么都不做
            if (session == null) {
                throw new IllegalStateException("无法更改 session id，request 里面没有 session 可访问");
            }


            Map<String, Object> attrs = new HashMap<String, Object>();
            // 获取 session 的当前属性
            Enumeration<String> iAttrNames = session.getAttributeNames();
            // 拷贝到新的 map 中
            while (iAttrNames.hasMoreElements()) {
                String attrName = iAttrNames.nextElement();
                Object value = session.getAttribute(attrName);
                attrs.put(attrName, value);
            }
            // 删除 当前 session
            SessionRepositoryFilter.this.sessionRepository.delete(session.getId());

            // 下面创建新的 session

            HttpSessionWrapper original = getCurrentSession();
            setCurrentSession(null);

            HttpSessionWrapper newSession = getSession();
            original.setSession(newSession.getSession());

            newSession.setMaxInactiveInterval(session.getMaxInactiveInterval());
            for (Map.Entry<String, Object> attr : attrs.entrySet()) {
                String attrName = attr.getKey();
                Object attrValue = attr.getValue();
                newSession.setAttribute(attrName, attrValue);
            }
            return newSession.getId();
        }

        /**
         * 断当前 session 是否跟 request 里面的 session id 相等
         *
         * @return true 为是，否则 false
         */
        @Override
        public boolean isRequestedSessionIdValid() {
            if (this.requestedSessionIdValid == null) {
                String sessionId = getRequestedSessionId();
                S session = sessionId == null ? null : getSession(sessionId);
                return isRequestedSessionIdValid(session);
            }

            return this.requestedSessionIdValid;
        }

        /**
         * 判断 session 是否跟 request 里面的 session id 相等
         *
         * @param session session
         *
         * @return true 为是，否则 false
         */
        private boolean isRequestedSessionIdValid(S session) {
            if (this.requestedSessionIdValid == null) {
                this.requestedSessionIdValid = session != null;
            }
            return this.requestedSessionIdValid;
        }

        /**
         * 判断 session 是否超时
         *
         * @return true 为是，否则 false
         *
         */
        private boolean isInvalidateClientSession() {
            return getCurrentSession() == null && this.requestedSessionInvalidated;
        }

        /**
         * 获取 session
         *
         * @param sessionId session id
         *
         * @return session
         */
        private S getSession(String sessionId) {
            S session = SessionRepositoryFilter.this.sessionRepository.getSession(sessionId);
            if (session == null) {
                return null;
            }
            session.setLastAccessedTime(System.currentTimeMillis());
            return session;
        }

        /**
         * 获取 session (包装器)
         *
         * @param create true 表示没有时创建一个，否则获取当前的 session，false 表示如果没有返回 null，否则获取当前的 session
         *
         * @return session (包装器)
         */
        @Override
        public HttpSessionWrapper getSession(boolean create) {
            // 获取当前 session (包装器)
            HttpSessionWrapper currentSession = getCurrentSession();
            // 如果当前有，返回当前的 session
            if (currentSession != null) {
                return currentSession;
            }
            // 获取当前 request 的 session id
            String requestedSessionId = getRequestedSessionId();
            // 如果 id 存在，并且超时 session id 值为没设置过，
            if (requestedSessionId != null && getAttribute(INVALID_SESSION_ID_ATTR) == null) {
                // 获取 session
                S session = getSession(requestedSessionId);
                // 如果找到了，重置一些状态，返回获取到的 session
                if (session != null) {
                    this.requestedSessionIdValid = true;
                    currentSession = new HttpSessionWrapper(session, getServletContext());
                    currentSession.setNew(false);
                    setCurrentSession(currentSession);
                    return currentSession;
                } else {
                    setAttribute(INVALID_SESSION_ID_ATTR, "true");
                }
            }

            if (!create) {
                return null;
            }

            // 如果以上动作还获取不了 session，并且 create 为 true，那就创建一个新的。
            S session = SessionRepositoryFilter.this.sessionRepository.createSession();
            session.setLastAccessedTime(System.currentTimeMillis());
            currentSession = new HttpSessionWrapper(session, getServletContext());
            setCurrentSession(currentSession);

            return currentSession;
        }

        @Override
        public ServletContext getServletContext() {
            if (this.servletContext != null) {
                return this.servletContext;
            }
            // Servlet 3.0+
            return super.getServletContext();
        }

        /**
         * 重写获取 http session (包装器) 方法，获取时，如果找不到，就创建一个新的
         *
         * @return http session (包装器)
         */
        @Override
        public HttpSessionWrapper getSession() {
            return getSession(true);
        }

        /**
         * 获取 当前 request 的 session id
         *
         * @return session id
         */
        @Override
        public String getRequestedSessionId() {
            return SessionRepositoryFilter.this.httpSessionStrategy.getRequestedSessionId(this);
        }

        /**
         * http session 包装器
         */
        private final class HttpSessionWrapper extends HttpExpiringSession<S> {

            /**
             * http session 包装器
             *
             * @param session session
             * @param servletContext servlet 上下文
             */
            HttpSessionWrapper(S session, ServletContext servletContext) {
                super(session, servletContext);
            }

            /**
             * 重写超时方法，当超时时候，设置 requestedSessionInvalidated = true 和删除 session
             */
            @Override
            public void invalidate() {
                super.invalidate();
                SessionRepositoryRequestWrapper.this.requestedSessionInvalidated = true;
                setCurrentSession(null);
                SessionRepositoryFilter.this.sessionRepository.delete(getId());
            }
        }
    }

    /**
     * 自定义一个 http response 包装器
     */
    private final class SessionRepositoryResponseWrapper extends OnCommittedResponseWrapper {
        // http request 包装器
        private final SessionRepositoryRequestWrapper request;

        /**
         * 构造方法
         *
         * @param request  http request 包装器
         * @param response http response 包装器
         */
        SessionRepositoryResponseWrapper(SessionRepositoryRequestWrapper request, HttpServletResponse response) {
            super(response);
            if (request == null) {
                throw new IllegalArgumentException("request 不能为 null");
            }
            this.request = request;
        }

        @Override
        protected void onResponseCommitted() {
            this.request.commitSession();
        }
    }

    /**
     * 一个多 http session 策略适配器
     */
    private static class MultiHttpSessionStrategyAdapter implements MultiHttpSessionStrategy {
        private HttpSessionStrategy delegate;

        /**
         * 构造方法
         *
         * @param delegate http session 策略
         */
        MultiHttpSessionStrategyAdapter(HttpSessionStrategy delegate) {
            this.delegate = delegate;
        }

        @Override
        public String getRequestedSessionId(HttpServletRequest request) {
            return this.delegate.getRequestedSessionId(request);
        }

        @Override
        public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
            this.delegate.onNewSession(session, request, response);
        }

        @Override
        public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
            this.delegate.onInvalidateSession(request, response);
        }

        @Override
        public HttpServletRequest wrapRequest(HttpServletRequest request, HttpServletResponse response) {
            return request;
        }

        @Override
        public HttpServletResponse wrapResponse(HttpServletRequest request, HttpServletResponse response) {
            return response;
        }

    }
}
