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

import com.github.dactiv.universe.sso.server.validation.entity.ValidateInfo;

/**
 * 简单的验证信息实现
 *
 * @author maurice
 */
public class SimpleValidateInfo implements ValidateInfo {

    private String ticket;
    private String redirectUrl;

    /**
     * 简单的验证信息实现
     */
    public SimpleValidateInfo() {
    }

    /**
     * 简单的验证信息实现
     *
     * @param ticket      票据 id
     * @param redirectUrl 机构信息
     */
    public SimpleValidateInfo(String ticket, String redirectUrl) {
        this.ticket = ticket;
        this.redirectUrl = redirectUrl;
    }

    /**
     * 获取票据 id
     *
     * @return 票据 id
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 获取机构信息
     *
     * @return 机构信息
     */
    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }
}
