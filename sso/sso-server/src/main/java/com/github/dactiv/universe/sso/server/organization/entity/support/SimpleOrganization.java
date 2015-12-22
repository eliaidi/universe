package com.github.dactiv.universe.sso.server.organization.entity.support;

import com.github.dactiv.universe.sso.server.validation.policy.AccessPolicy;
import com.github.dactiv.universe.sso.server.validation.policy.ExpirationPolicy;
import com.github.dactiv.universe.sso.server.validation.policy.support.SimpleAccessPolicy;
import com.github.dactiv.universe.sso.server.validation.policy.support.SimpleUsernameAttributePolicy;
import com.github.dactiv.universe.sso.server.validation.policy.UsernameAttributePolicy;
import com.github.dactiv.universe.sso.server.validation.policy.support.AuthenticationTicketExpirationPolicy;
import com.github.dactiv.universe.sso.server.entity.support.SimpleIdEntity;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import com.github.dactiv.universe.sso.server.validation.policy.AttributeReleasePolicy;

/**
 * 机构类的简单实现
 *
 * @author maurice
 */
public class SimpleOrganization extends SimpleIdEntity implements Organization {

    // 域名，或通配符或可鉴定机构的信息
    private String wildcard;
    // 名称
    private String name;
    // 描述
    private String description;
    // 超时政策
    private ExpirationPolicy expirationPolicy = new AuthenticationTicketExpirationPolicy();
    // 访问策略
    private AccessPolicy accessPolicy = new SimpleAccessPolicy();
    // 当事人（用户）属性发布政策
    private AttributeReleasePolicy attributeReleasePolicy;
    // 获取用户名政策
    private UsernameAttributePolicy usernameAttributePolicy = new SimpleUsernameAttributePolicy();

    /**
     * 简单的 id 实体类实现
     *
     * @param id          id 值
     * @param wildcard    唯一识别
     * @param name        机构名称
     * @param description 机构描述
     */
    public SimpleOrganization(Object id, String wildcard, String name, String description) {
        this(id, new SimpleAccessPolicy(), new AuthenticationTicketExpirationPolicy(), null, description, name, wildcard);
    }

    /**
     * 简单的 id 实体类实现
     *
     * @param id                     id 值
     * @param accessPolicy           访问策略
     * @param expirationPolicy       超时政策
     * @param attributeReleasePolicy 当事人（用户）属性发布政策
     * @param wildcard               唯一识别
     * @param name                   机构名称
     * @param description            机构描述
     */
    public SimpleOrganization(Object id, AccessPolicy accessPolicy, ExpirationPolicy expirationPolicy, AttributeReleasePolicy attributeReleasePolicy, String description, String name, String wildcard) {
        super(id);
        this.accessPolicy = accessPolicy;
        this.expirationPolicy = expirationPolicy;
        this.attributeReleasePolicy = attributeReleasePolicy;
        this.description = description;
        this.name = name;
        this.wildcard = wildcard;
    }

    /**
     * 机构类的简单实现
     */
    public SimpleOrganization() {
    }

    /**
     * 获取域名，或通配符或可鉴定机构的信息。
     *
     * @return 鉴定信息
     */
    @Override
    public String getWildcard() {
        return wildcard;
    }

    /**
     * 获取机构名称
     *
     * @return 名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取描述信息
     *
     * @return 描述信息
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 获取机构访问策略
     *
     * @return 访问策略信息
     */
    public AccessPolicy getAccessPolicy() {
        return accessPolicy;
    }

    /**
     * 获取票据超时政策
     *
     * @return 超时政策信息
     */
    @Override
    public ExpirationPolicy getExpirationPolicy() {
        return expirationPolicy;
    }

    /**
     * 获取发布用户属性政策
     *
     * @return 用户属性政策
     */
    @Override
    public AttributeReleasePolicy getAttributeReleasePolicy() {
        return attributeReleasePolicy;
    }

    /**
     * 获取如果获取当事人（用户）用户名政策
     *
     * @return 获取用户名政策
     */
    @Override
    public UsernameAttributePolicy getUsernameAttributePolicy() {
        return usernameAttributePolicy;
    }

    /**
     * 设置域名，或通配符或可鉴定机构的信息
     *
     * @param wildcard 域名，或通配符或可鉴定机构的信息
     */
    public void setWildcard(String wildcard) {
        this.wildcard = wildcard;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 设置超时政策
     *
     * @param expirationPolicy 超时政策
     */
    public void setExpirationPolicy(ExpirationPolicy expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
    }

    /**
     * 设置访问策略
     *
     * @param accessPolicy 访问策略
     */
    public void setAccessPolicy(AccessPolicy accessPolicy) {
        this.accessPolicy = accessPolicy;
    }

    /**
     * 设置当事人（用户）属性发布政策
     *
     * @param attributeReleasePolicy 当事人（用户）属性发布政策
     */
    public void setAttributeReleasePolicy(AttributeReleasePolicy attributeReleasePolicy) {
        this.attributeReleasePolicy = attributeReleasePolicy;
    }

    /**
     * 设置获取用户名政策
     *
     * @param usernameAttributePolicy 获取用户名政策
     */
    public void setUsernameAttributePolicy(UsernameAttributePolicy usernameAttributePolicy) {
        this.usernameAttributePolicy = usernameAttributePolicy;
    }
}
