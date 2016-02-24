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

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Sso 登出 filter
 *
 * @author maurice
 */
public class SsoSignOutFilter extends LogoutFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoSignOutFilter.class);

    /**
     * 默认重定向 url 参数名称
     */
    public final static String DEFAULT_REDIRECT_URL_PARAM_NAME = "redirectUrl";

    // 重定向 url 参数名称
    private String redirectUrlParamName = DEFAULT_REDIRECT_URL_PARAM_NAME;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        //try/catch added for SHIRO-298:
        try {
            subject.logout();
        } catch (SessionException ise) {
            LOGGER.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        String url = getRedirectUrl(request);

        Map<String, String> queryParams = new HashMap<>();

        if (StringUtils.isNotEmpty(url)) {
            queryParams.put(redirectUrlParamName, url);
        }

        WebUtils.issueRedirect(request, response, redirectUrl, queryParams);
        return false;
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
}
