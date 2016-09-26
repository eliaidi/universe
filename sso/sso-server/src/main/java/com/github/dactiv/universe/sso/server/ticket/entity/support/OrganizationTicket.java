/*
 * Copyright 2016 dactiv
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

import com.github.dactiv.universe.sso.server.organization.entity.Organization;

/**
 * 机构票据实现
 *
 * @author maurice
 */
public class OrganizationTicket extends SimpleTicket {

    // 机构信息
    private Organization organization;

    /**
     * 机构票据实现
     *
     * @param id 主键 id
     * @param organization 机构信息
     */
    public OrganizationTicket(Object id, Organization organization) {
        super(id);
        this.organization = organization;
    }

    /**
     * 获取机构信息
     *
     * @return 机构信息
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * 重写父类方法，不给设置超时时间，设置也没用。机构会有超时的判断
     *
     * @param expiredTime 超时时间
     */
    @Override
    public void setExpiredTime(long expiredTime) {
        throw new UnsupportedOperationException("OrganizationTicket is not supported setExpiredTime");
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
}
