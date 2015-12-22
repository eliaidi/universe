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

package com.github.dactiv.universe.sso.server.ticket;

import com.github.dactiv.universe.sso.server.ticket.entity.AuthenticationTicket;
import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;
import com.github.dactiv.universe.sso.server.validation.TicketValidationManager;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 票据管理
 *
 * @author maurice
 */
public interface TicketManager extends TicketValidationManager {

    /**
     * 生成一个新的票据
     *
     * @param prefix 票据前缀
     * @return 票据字符串
     */
    String create(String prefix);

    /**
     * 创建认证票据
     *
     * @param organization 机构识别
     * @param principal    当事人(登录成功后的用户)
     * @param token        认证令牌
     * @return 认证票据
     */
    AuthenticationTicket createAuthenticationTicket(String organization, Object principal, AuthenticationToken token);

    /**
     * 保存票据
     *
     * @param ticket 票据信息
     */
    void save(Ticket ticket);

    /**
     * 通过票据 id 获取票据
     *
     * @param id          票据 id
     * @param ticketClass 票据 class 类型
     * @return 票据
     */
    <T extends Ticket> T get(String id, Class<? extends Ticket> ticketClass);

    /**
     * 删除票据
     *
     * @param id 票据 id
     */
    void delete(Object id);
}
