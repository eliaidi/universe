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

package com.github.dactiv.universe.sso.server;

import com.github.dactiv.universe.sso.server.ticket.TicketManager;
import com.github.dactiv.universe.sso.server.ticket.entity.AuthenticationTicket;
import com.github.dactiv.universe.shiro.filter.CaptchaAuthenticationFilter;
import com.github.dactiv.universe.sso.server.ticket.entity.support.IpSessionTicket;
import com.github.dactiv.universe.sso.server.utils.HttpServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Sso 认证 filter
 *
 * @author maurice
 */
public class SsoAuthenticationFilter extends CaptchaAuthenticationFilter {

    /**
     * 默认登录票据存储在 session 中的属性名称
     */
    public final static String DEFAULT_LOGIN_TICKET_SESSION_ATTRIBUTE_NAME = "LOGIN_TICKET";
    /**
     * 默认登录 form 中提交的票据参数名
     */
    public final static String DEFAULT_LOGIN_TICKET_PARAM_NAME = "lt";
    /**
     * 默认登录票据的前缀
     */
    public final static String DEFAULT_LOGIN_TICKET_PREFIX = "LT";
    /**
     * 默认重定向 url 参数名称
     */
    public final static String DEFAULT_REDIRECT_URL_PARAM_NAME = "redirectUrl";
    /**
     * 默认重定向 票据参数名
     */
    public final static String DEFAULT_REDIRECT_TICKET_PARAM_NAME = "ticket";
    /**
     * 默认认证票据存储在 session 中的属性名称
     */
    public final static String DEFAULT_AUTHENTICATION_TICKET_SESSION_ATTRIBUTE_NAME = "authenticationTicket";

    // 登录票据存储在 session 中的属性名称
    private String loginTicketSessionAttributeName = DEFAULT_LOGIN_TICKET_SESSION_ATTRIBUTE_NAME;
    // 认证票据存储在 session 中的属性名称
    private String authenticationTicketSessionAttributeName = DEFAULT_AUTHENTICATION_TICKET_SESSION_ATTRIBUTE_NAME;
    // 登录 form 中提交的票据参数名
    private String loginTicketParamName = DEFAULT_LOGIN_TICKET_PARAM_NAME;
    // 登录票据的前缀名称
    private String loginTicketPrefix = DEFAULT_LOGIN_TICKET_PREFIX;
    // 重定向 url 参数名称
    private String redirectUrlParamName = DEFAULT_REDIRECT_URL_PARAM_NAME;
    // 重定向的票据参数名称
    private String redirectTicketParamName = DEFAULT_REDIRECT_TICKET_PARAM_NAME;
    // 票据管理
    private TicketManager ticketManager;

    /**
     * 重写父类方法，在 shiro 执行登录时做一些验证工作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        if (!isSsoLogin(request)) {
            return super.executeLogin(request, response);
        }

        if (!checkLoginTicket(request)) {

            HttpSession session = WebUtils.toHttp(request).getSession();
            session.setAttribute(loginTicketSessionAttributeName, ticketManager.create(loginTicketPrefix));

            String url = getLoginUrl() + "?" + redirectUrlParamName + "=" + getRedirectUrl(request);
            WebUtils.issueRedirect(request, response, url);

            return Boolean.FALSE;
        }

        return super.executeLogin(request, response);
    }

    /**
     * 重写父类方法，当登录成功后，生成认证 ticket 发动重定向 url 地址中
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {
        if (isSsoLogin(request)) {

            String url = getRedirectUrl(request);
            Object principal = subject.getPrincipal();
            AuthenticationTicket ticket = ticketManager.createAuthenticationTicket(url, principal, token);

            ticketManager.save(ticket);
            WebUtils.toHttp(request).getSession().setAttribute(authenticationTicketSessionAttributeName, ticket);
            WebUtils.issueRedirect(request, response, url + "?" + redirectTicketParamName + "=" + ticket);

            return Boolean.FALSE;
        }

        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 判断是否 sso 登录
     *
     * @param request servlet request
     * @return 是返回 true，否则返回 false
     */
    private boolean isSsoLogin(ServletRequest request) {
        return StringUtils.isNotEmpty(getRedirectUrl(request)) && StringUtils.isNotEmpty(getLoginTicket(request));
    }

    /**
     * 判断 session 和 request 中的票据是否一致
     *
     * @param request servlet request
     * @return 一致返回 true，否则 false
     */
    protected boolean checkLoginTicket(ServletRequest request) {
        final String sessionLoginTicket = getLoginTicket(request);
        final String requestLoginTicket = getSessionLoginTicket();

        if (!StringUtils.equals(sessionLoginTicket, requestLoginTicket)) {
            return false;
        }

        IpSessionTicket ipSessionTicket = ticketManager.get(requestLoginTicket, IpSessionTicket.class);

        String ipAddress = ipSessionTicket.getIpAddress();
        String sessionId = ipSessionTicket.getSessionId();

        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String currentSessionId = httpRequest.getSession().getId();

        if (ipSessionTicket.isExpired() ||
                !StringUtils.equals(ipAddress, HttpServletUtils.getIpAddress(httpRequest)) ||
                !StringUtils.equals(currentSessionId, sessionId)) {
            return false;
        }

        return true;
    }

    /**
     * 获取 request 中的登录票据
     *
     * @param request servlet request
     * @return 存在返回票据信息，否则返回 null
     */
    public String getLoginTicket(ServletRequest request) {
        return WebUtils.getCleanParam(request, loginTicketParamName);
    }

    /**
     * 获取要重定向的 url
     *
     * @param request servlet request
     * @return 存在返回重定向 url，否则返回 null
     */
    public String getRedirectUrl(ServletRequest request) {
        return WebUtils.getCleanParam(request, redirectUrlParamName);
    }

    /**
     * 获取 session 作用于的登录票据
     *
     * @return 存在返回票据信息，否则返回 null
     */
    public String getSessionLoginTicket() {
        return (String) getSession().getAttribute(loginTicketSessionAttributeName);
    }

    /**
     * 获取当前 subject 的 session
     *
     * @return Session
     */
    public Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 设置登录票据存储在 session 中的属性名称
     *
     * @param loginTicketSessionAttributeName session 中的属性名称
     */
    public void setLoginTicketSessionAttributeName(String loginTicketSessionAttributeName) {
        this.loginTicketSessionAttributeName = loginTicketSessionAttributeName;
    }

    /**
     * 设置登录 form 中提交的票据参数名称
     *
     * @param loginTicketParamName 参数名称
     */
    public void setLoginTicketParamName(String loginTicketParamName) {
        this.loginTicketParamName = loginTicketParamName;
    }

    /**
     * 登录票据的前缀名称
     *
     * @param loginTicketPrefix 前缀名称
     */
    public void setLoginTicketPrefix(String loginTicketPrefix) {
        this.loginTicketPrefix = loginTicketPrefix;
    }

    /**
     * 重定向 url 参数名称
     *
     * @param redirectUrlParamName 参数名称
     */
    public void setRedirectUrlParamName(String redirectUrlParamName) {
        this.redirectUrlParamName = redirectUrlParamName;
    }

    /**
     * 设置认证票据存储在 session 中的属性名称
     *
     * @param authenticationTicketSessionAttributeName 属性名称
     */
    public void setAuthenticationTicketSessionAttributeName(String authenticationTicketSessionAttributeName) {
        this.authenticationTicketSessionAttributeName = authenticationTicketSessionAttributeName;
    }

    /**
     * 设置重定向的票据参数名称
     *
     * @param redirectTicketParamName 参数名称
     */
    public void setRedirectTicketParamName(String redirectTicketParamName) {
        this.redirectTicketParamName = redirectTicketParamName;
    }

    /**
     * 设置票据管理
     *
     * @param ticketManager 票据管理
     */
    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }
}
