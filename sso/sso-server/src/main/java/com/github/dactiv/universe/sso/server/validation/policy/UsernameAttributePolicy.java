package com.github.dactiv.universe.sso.server.validation.policy;

import com.github.dactiv.universe.sso.server.organization.entity.Organization;

import java.io.Serializable;

/**
 * 获取用户名政策
 *
 * @author maurice
 */
public interface UsernameAttributePolicy extends Serializable {

    /**
     * 获取用户名
     *
     * @param principal    当事人（用户）
     * @param organization 家机构信息
     * @return 用户名
     */
    String getUsername(Object principal, Organization organization);
}
