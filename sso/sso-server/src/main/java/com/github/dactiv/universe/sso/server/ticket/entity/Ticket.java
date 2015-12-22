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


import com.github.dactiv.universe.sso.server.entity.IdEntity;

import java.util.Date;

/**
 * SSO 认证需要的票据接口
 *
 * @author maurice
 */
public interface Ticket extends IdEntity {

    /**
     * 判断是否超时
     *
     * @return true 表示超时，否则 false
     */
    boolean isExpired();

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Date getCreationTime();

    /**
     * 获取该票据使用的次数
     *
     * @return 使用次数
     */
    int getCountOfUses();

    /**
     * 最后一次票据使用时间
     *
     * @return 时间对象
     */
    Date getLastTimeUsed();

    /**
     * 上一次使用票据的时间
     *
     * @return 时间对象
     */
    Date getPreviousTimeUsed();
}
