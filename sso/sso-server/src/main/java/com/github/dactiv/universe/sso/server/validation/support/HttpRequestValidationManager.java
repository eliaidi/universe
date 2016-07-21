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


package com.github.dactiv.universe.sso.server.validation.support;

import com.github.dactiv.universe.sso.server.exception.UrlTicketValidateException;
import com.github.dactiv.universe.sso.server.validation.entity.ValidateToken;
import com.github.dactiv.universe.sso.server.validation.entity.support.SimpleValidateToken;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maurice
 */
public class HttpRequestValidationManager extends AbstractTicketValidationManager {
    /**
     * 重定向的 url 参数名称
     */
    public final static String REDIRECT_URL_PARAM_NAME = "redirectUrl";

    /**
     * 创建验证票据的令牌
     *
     * @param requestPairSource 请求信息
     * @return 验证令牌
     */
    @Override
    protected ValidateToken createValidTicket(Object requestPairSource) {

        if (WebUtils.isHttp(requestPairSource)) {
            throw new UrlTicketValidateException("ticket valid only support http request");
        }

        HttpServletRequest request = (HttpServletRequest) requestPairSource;

        String ticket = WebUtils.getCleanParam(request, TICKET_PARAM_NAME);
        String redirectUrl = WebUtils.getCleanParam(request, REDIRECT_URL_PARAM_NAME);

        return new SimpleValidateToken(ticket, redirectUrl);
    }

}
