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

package com.github.dactiv.universe.sso.server.validation.policy;

import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;

import java.io.Serializable;

/**
 * 超时政策接口
 *
 * @author maurice
 */
public interface ExpirationPolicy extends Serializable {

    /**
     * 是否超时
     *
     * @param ticket 票据信息
     * @return true 表示是，否则表示 false
     */
    boolean isExpired(Ticket ticket);

}
