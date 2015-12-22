package com.github.dactiv.universe.sso.server.organization;

import com.github.dactiv.universe.sso.server.organization.entity.Organization;

/**
 * 允许使用 SSO 的机构数据访问
 *
 * @author maurice
 */
public interface OrganizationManager {

    /**
     * 通过通配符获取机构实体
     *
     * @param wildcard 通配符
     *
     * @return 机构实体
     */
    Organization getByWildcard(String wildcard);

}
