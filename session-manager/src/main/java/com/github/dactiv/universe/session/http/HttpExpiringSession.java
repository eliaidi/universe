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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * http 超时 session 实现
 *
 * @author maurice
 */
@SuppressWarnings("deprecation")
public class HttpExpiringSession<S extends ExpiringSession> implements HttpSession {

    // session 实现
    private S session;
    // servlet context
    private final ServletContext servletContext;
    // 是否超时，true 为是，否则 false
    private boolean invalidated;
    // 是否新创建，true 为是，否则 false
    private boolean old;

    /**
     * http 超时 session 实现
     *
     * @param session session 实现
     * @param servletContext servlet context
     */
    public HttpExpiringSession(S session, ServletContext servletContext) {
        this.session = session;
        this.servletContext = servletContext;
    }

    @Override
    public long getCreationTime() {
        checkState();
        return this.session.getCreationTime();
    }

    @Override
    public String getId() {
        return this.session.getId();
    }

    @Override
    public long getLastAccessedTime() {
        checkState();
        return this.session.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.session.setMaxInactiveIntervalInSeconds(interval);
    }

    @Override
    public int getMaxInactiveInterval() {
        return this.session.getMaxInactiveIntervalInSeconds();
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return NOOP_SESSION_CONTEXT;
    }

    @Override
    public Object getAttribute(String name) {
        checkState();
        return this.session.getAttribute(name);
    }

    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        checkState();
        return Collections.enumeration(this.session.getAttributeNames());
    }

    @Override
    public String[] getValueNames() {
        checkState();
        Set<String> attrs = this.session.getAttributeNames();
        return attrs.toArray(new String[0]);
    }

    @Override
    public void setAttribute(String name, Object value) {
        checkState();
        this.session.setAttribute(name, value);
    }

    @Override
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        checkState();
        this.session.removeAttribute(name);
    }

    @Override
    public void removeValue(String name) {
        removeAttribute(name);
    }

    @Override
    public void invalidate() {
        checkState();
        this.invalidated = true;
    }

    @Override
    public boolean isNew() {
        checkState();
        return !this.old;
    }

    /**
     * 检测 session 是否超时
     */
    private void checkState() {
        if (this.invalidated) {
            throw new IllegalStateException("session invalidated");
        }
    }

    /**
     * 获取当前 session
     *
     * @return session
     */
    public S getSession() {
        return this.session;
    }

    /**
     * 设置当前 session
     * @param session session
     */
    public void setSession(S session) {
        this.session = session;
    }

    /**
     * 设置是否为新的 session
     *
     * @param isNew true 表示是，否则 false
     */
    public void setNew(boolean isNew) {
        this.old = !isNew;
    }

    private static final HttpSessionContext NOOP_SESSION_CONTEXT = new HttpSessionContext() {
        public HttpSession getSession(String sessionId) {
            return null;
        }

        public Enumeration<String> getIds() {
            return EMPTY_ENUMERATION;
        }
    };

    private static final Enumeration<String> EMPTY_ENUMERATION = new Enumeration<String>() {
        public boolean hasMoreElements() {
            return false;
        }

        public String nextElement() {
            throw new NoSuchElementException("a");
        }
    };
}
