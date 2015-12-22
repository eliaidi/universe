package com.github.dactiv.universe.sso.server.validation.policy.support;

import com.github.dactiv.universe.sso.server.validation.policy.AccessPolicy;
import com.github.dactiv.universe.sso.server.organization.entity.Status;

/**
 * 简单的访问策略实现
 *
 * @author maurice
 */
public class SimpleAccessPolicy implements AccessPolicy {

    // 状态
    private Status status = Status.DISABLE;
    // 是否启用 sso
    private boolean ssoEnabled = Boolean.FALSE;

    /**
     * 简单的访问策略实现
     */
    public SimpleAccessPolicy() {
    }

    /**
     * 简单的访问策略实现
     *
     * @param status     状态
     * @param ssoEnabled 是否启用 sso
     */
    public SimpleAccessPolicy(Status status, boolean ssoEnabled) {
        this.status = status;
        this.ssoEnabled = ssoEnabled;
    }

    /**
     * 获取当前状态
     *
     * @return 启用返回 {@link Status#ENABLED}, 禁用返回 {@link Status#DISABLE}
     * @see Status
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * 是否允许访问 sso
     *
     * @return true 表示是，否则 false
     */
    @Override
    public boolean isAccessAllowedForSso() {
        return ssoEnabled;
    }

}
