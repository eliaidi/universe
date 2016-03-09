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


package com.github.dactiv.universe.sso.server.validation.entity;

/**
 * 验证票据的令牌实体接口
 *
 * @author maurice
 */
public interface ValidateToken {

    /**
     * 获取票据 id
     *
     * @return 票据 id
     */
    String getTicket();

    /**
     * 获取重定向 url
     *
     * @return 重定向 url
     */
    String getRedirectUrl();
}
