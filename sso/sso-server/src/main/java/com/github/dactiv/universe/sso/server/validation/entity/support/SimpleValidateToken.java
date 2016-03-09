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


package com.github.dactiv.universe.sso.server.validation.entity.support;

import com.github.dactiv.universe.sso.server.validation.entity.ValidateToken;

/**
 *
 * 简单的验证票据令牌接口实现
 *
 * @author maurice
 */
public class SimpleValidateToken implements ValidateToken {

    // 票据信息
    private String ticket;
    // 重定向的 url
    private String redirectUrl;

    /**
     * 简单的验证票据令牌接口实现
     *
     * @param ticket      票据信息
     * @param redirectUrl 重定向的 url
     */
    public SimpleValidateToken(String ticket, String redirectUrl) {
        this.ticket = ticket;
        this.redirectUrl = redirectUrl;
    }

    /**
     * 获取票据 id
     *
     * @return 票据 id
     */
    @Override
    public String getTicket() {
        return ticket;
    }

    /**
     * 获取重定向 url
     *
     * @return 重定向 url
     */
    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }
}
