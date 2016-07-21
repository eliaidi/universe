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

package com.github.dactiv.universe.sso.client.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *
 * sso 认证 filter
 *
 * @author maurice
 */
public class SsoClientFilter extends AuthenticatingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoClientFilter.class);

    // 票据名称
    private static final String TICKET_PARAM_NAME = "ticket";

    // 认证失败 url
    private String failureUrl;

    /**
     * 重写父类方法，当为 sso 登录时，通过票据信息创建 sso 令牌
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String ticket = WebUtils.getCleanParam(request, TICKET_PARAM_NAME);
        return new SsoToken(ticket);
    }

    /**
     * 当写父类方法，如果确定当前某个请求处理失败时，不在判断是否存在当前用户，必须要直接执行登录操作。
     * 不然会出现，应用A登录B用户，应用B登录B用户，但登录到应用B的当前用户是A时，会存在B用户信息的问题。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    /**
     * 重写父类方法，如果进入到该 filter，大致可以认为是 sso 登录，所以，不在判断当前用户是否已认证，必须重新认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return Boolean.FALSE;
    }

    /**
     * 重写父类方法，如果登录失败，并且 failureUrl 有值，跳转到 failureUrl 否则跳转到本系统的登录页面
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e,
                                     ServletRequest request,
                                     ServletResponse response) {

        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("sso 认证失败", e);
        }

        try {
            if(StringUtils.isNotEmpty(failureUrl)) {
                WebUtils.issueRedirect(request, response, failureUrl);
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } catch (IOException exception) {
            LOGGER.error("sso login failure than redirect to url:" + failureUrl + "error", exception);
        }
        return Boolean.FALSE;
    }

    /**
     * 重写父类方法，当登录成功后，跳转到成功后的页面
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {

        issueSuccessRedirect(request, response);

        return false;
    }

    /**
     * 认证失败跳转的 url
     *
     * @param failureUrl url
     */
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }
}
