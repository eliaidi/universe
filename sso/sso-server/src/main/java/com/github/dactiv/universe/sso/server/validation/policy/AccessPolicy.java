package com.github.dactiv.universe.sso.server.validation.policy;

import com.github.dactiv.universe.sso.server.organization.entity.Status;

import java.io.Serializable;

/**
 * 机构访问策略
 *
 * @author maurice
 */
public interface AccessPolicy extends Serializable {

    /**
     * 获取当前状态
     *
     * @return 启用返回 {@link Status#ENABLED}, 禁用返回 {@link Status#DISABLE}
     * @see Status
     */
    Status getStatus();

    /**
     * 是否允许访问 sso
     *
     * @return true 表示是，否则 false
     */
    boolean isAccessAllowedForSso();
}
