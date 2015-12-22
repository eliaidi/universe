package com.github.dactiv.universe.sso.server.organization.entity;


import com.github.dactiv.universe.sso.server.entity.IdEntity;
import com.github.dactiv.universe.sso.server.validation.policy.AccessPolicy;
import com.github.dactiv.universe.sso.server.validation.policy.ExpirationPolicy;
import com.github.dactiv.universe.sso.server.validation.policy.UsernameAttributePolicy;
import com.github.dactiv.universe.sso.server.validation.policy.AttributeReleasePolicy;

/**
 * 允许使用 SSO 的机构接口
 *
 * @author maurice
 */
public interface Organization extends IdEntity {

    /**
     * 获取域名，或通配符或可鉴定机构的信息。
     *
     * @return 鉴定信息
     */
    String getWildcard();

    /**
     * 获取机构名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取描述信息
     *
     * @return 描述信息
     */
    String getDescription();

    /**
     * 获取机构访问策略
     *
     * @return 访问策略信息
     */
    AccessPolicy getAccessPolicy();

    /**
     * 获取票据超时政策
     *
     * @return 超时政策信息
     */
    ExpirationPolicy getExpirationPolicy();

    /**
     * 获取发布用户属性政策
     *
     * @return 用户属性政策
     */
    AttributeReleasePolicy getAttributeReleasePolicy();

    /**
     * 获取如果获取当事人（用户）用户名政策
     *
     * @return 获取用户名政策
     */
    UsernameAttributePolicy getUsernameAttributePolicy();
}
