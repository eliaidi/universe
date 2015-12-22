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

import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import com.github.dactiv.universe.sso.server.entity.support.SimpleIdEntity;

import java.util.Date;

/**
 * 抽象票据实现
 *
 * @author maurice
 */
public abstract class AbstractTicket extends SimpleIdEntity implements Ticket {

    // 最后一次使用时间
    private Date lastTimeUsed;
    // 上一次使用时间
    private Date previousTimeUsed;
    // 创建时间
    private Date creationTime;
    // 使用次数
    private int countOfUses;
    // 机构信息
    private Organization organization;

    /**
     * 抽象票据实现
     */
    public AbstractTicket() {

    }

    /**
     * 抽象票据实现
     *
     * @param id           id 值
     * @param organization 对应的机构信息
     */
    public AbstractTicket(Object id, Organization organization) {
        super(id);
        this.creationTime = new Date();
        this.lastTimeUsed = new Date();
        this.organization = organization;
    }

    /**
     * 获取票据最后使用时间
     *
     * @param lastTimeUsed 最后使用时间
     */
    public void setLastTimeUsed(Date lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }

    /**
     * 获取票据上一次使用时间
     *
     * @param previousTimeUsed 上一次使用时间
     */
    public void setPreviousTimeUsed(Date previousTimeUsed) {
        this.previousTimeUsed = previousTimeUsed;
    }

    /**
     * 获取票据创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取票据使用次数
     *
     * @param countOfUses 使用次数
     */
    public void setCountOfUses(int countOfUses) {
        this.countOfUses = countOfUses;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    @Override
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 获取该票据使用的次数
     *
     * @return 使用次数
     */
    @Override
    public int getCountOfUses() {
        return countOfUses;
    }

    /**
     * 最后一次票据使用时间
     *
     * @return 时间对象
     */
    @Override
    public Date getLastTimeUsed() {
        return lastTimeUsed;
    }

    /**
     * 上一次使用票据的时间
     *
     * @return 时间对象
     */
    @Override
    public Date getPreviousTimeUsed() {
        return previousTimeUsed;
    }

    /**
     * 判断是否超时
     *
     * @return true 表示超时，否则 false
     */
    @Override
    public boolean isExpired() {
        return this.organization.getExpirationPolicy().isExpired(this);
    }

    /**
     * 获取机构信息
     *
     * @return 机构信息
     */
    public Organization getOrganization() {
        return organization;
    }
}
