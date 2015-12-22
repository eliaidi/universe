package com.github.dactiv.universe.sso.server.validation.policy.support;

import com.github.dactiv.universe.sso.server.validation.repository.PrincipalAttributesRepository;
import com.github.dactiv.universe.sso.server.validation.repository.support.SimplePrincipalAttributesRepository;
import com.github.dactiv.universe.sso.server.validation.policy.AttributeReleasePolicy;

import java.util.Map;

/**
 * 抽象的发布当事人（用户）信息实现
 *
 * @author maurice
 */
public abstract class AbstractAttributeReleasePolicy implements AttributeReleasePolicy {

    // 是否发用户登录密码
    private boolean releaseCredentialPassword = Boolean.FALSE;
    // 是否发布票据信息
    private boolean releaseTicket = Boolean.FALSE;
    // 当事人（用户）属性库
    private PrincipalAttributesRepository principalAttributesRepository = new SimplePrincipalAttributesRepository();

    /**
     * 抽象的发布当事人（用户）信息实现
     */
    public AbstractAttributeReleasePolicy() {
    }

    /**
     * 抽象的发布当事人（用户）信息实现
     *
     * @param releaseCredentialPassword 是否发布用户的登录密码
     * @param releaseTicket             是否发布票据信息
     */
    public AbstractAttributeReleasePolicy(boolean releaseCredentialPassword, boolean releaseTicket) {
        this.releaseCredentialPassword = releaseCredentialPassword;
        this.releaseTicket = releaseTicket;
    }

    /**
     * 是否发用户登录密码
     *
     * @return true 表示是，否则 false
     */
    @Override
    public boolean isReleaseCredentialPassword() {
        return releaseCredentialPassword;
    }

    /**
     * 是否发布票据信息
     *
     * @return true 表示是，否则 false
     */
    @Override
    public boolean isReleaseTicket() {
        return releaseTicket;
    }

    /**
     * 获取发布属性
     *
     * @param principal 当事人（用户）
     * @return 属性 map
     */
    @Override
    public Map<String, Object> getAttributes(Object principal) {

        Map<String, Object> principalAttributes = principalAttributesRepository.getAttributes(principal);

        return getAttributesInternal(principalAttributes);
    }

    /**
     * 获取发布属性
     *
     * @param principalAttributes 当事人（用户）所有属性 map
     * @return 发布属性 map
     */
    protected abstract Map<String, Object> getAttributesInternal(Map<String, Object> principalAttributes);

    /**
     * 设置是否发用户登录密码
     *
     * @param releaseCredentialPassword true 为是，否则 false
     */
    public void setReleaseCredentialPassword(boolean releaseCredentialPassword) {
        this.releaseCredentialPassword = releaseCredentialPassword;
    }

    /**
     * 设置是否发布票据
     *
     * @param releaseTicket true 为是，否则 false
     */
    public void setReleaseTicket(boolean releaseTicket) {
        this.releaseTicket = releaseTicket;
    }

    /**
     * 设置 当事人（用户）属性库
     *
     * @param principalAttributesRepository 当事人（用户）属性库
     */
    public void setPrincipalAttributesRepository(PrincipalAttributesRepository principalAttributesRepository) {
        this.principalAttributesRepository = principalAttributesRepository;
    }
}
