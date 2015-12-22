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

package com.github.dactiv.universe.sso.server.ticket.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 认证票据
 *
 * @author maurice
 */
public interface AuthenticationTicket extends Ticket {

    /**
     * 获取认证成功的当事人（用户）
     *
     * @return 当事人（用户）信息
     */
    Object getPrincipal();

    /**
     * 获取认证令牌
     *
     * @return 认证令牌
     */
    AuthenticationToken getAuthenticationToken();
}
