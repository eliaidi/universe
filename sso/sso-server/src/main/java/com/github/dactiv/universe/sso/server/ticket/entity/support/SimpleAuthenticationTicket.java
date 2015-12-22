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

package com.github.dactiv.universe.sso.server.ticket.entity.support;

import com.github.dactiv.universe.sso.server.ticket.entity.AuthenticationTicket;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 简单的认证票据实现
 *
 * @author maurice
 */
public class SimpleAuthenticationTicket extends AbstractTicket implements AuthenticationTicket {

    // 认证成功的当事人（用户）
    private Object principal;
    // 用户认证令牌
    private AuthenticationToken authenticationToken;

    /**
     * 简单的认证票据实现
     *
     * @param id                  票据 id
     * @param organization        超时政策
     * @param principal           当事人
     * @param authenticationToken 当事人
     */
    public SimpleAuthenticationTicket(String id, Organization organization, Object principal, AuthenticationToken authenticationToken) {
        super(id, organization);
        this.principal = principal;
        this.authenticationToken = authenticationToken;
    }

    /**
     * 获取认证成功的当事人（用户）
     *
     * @return 当事人（用户）信息
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * 获取认证令牌
     *
     * @return 认证令牌
     */
    @Override
    public AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    @Override
    public String toString() {
        return getId().toString();
    }
}
