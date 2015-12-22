package com.github.dactiv.universe.sso.client.validation.support;

import com.github.dactiv.universe.sso.client.authentication.AttributePrincipal;
import com.github.dactiv.universe.sso.client.authentication.support.SimpleAttributePrincipal;
import com.github.dactiv.universe.sso.client.validation.Assertion;

import java.util.Date;
import java.util.Map;

/**
 *
 * 断言的简单实现
 *
 * @author maurice
 */
public class SimpleAssertion implements Assertion {

    private Date validFromDate;
    private Map<String, Object> attribute;
    private AttributePrincipal principal;

    /**
     * 断言的简单实现
     */
    public SimpleAssertion() {

    }

    /**
     * 断言的简单实现
     *
     * @param username 当前人（当前用户）用户名
     */
    public SimpleAssertion(String username) {
        this(new SimpleAttributePrincipal(username));
    }

    /**
     * 断言的简单实现
     *
     * @param principal 当前人（当前用户）
     */
    public SimpleAssertion(AttributePrincipal principal) {
        this(principal, null);
    }

    /**
     * 断言的简单实现
     *
     * @param principal 当前人（当前用户）
     * @param attribute 断言属性
     */
    public SimpleAssertion(AttributePrincipal principal, Map<String, Object> attribute) {
        this(new Date(), attribute, principal);
    }

    /**
     * 断言的简单实现
     *
     * @param validFromDate  断言有效时间
     * @param attribute      断言属性
     * @param principal      当前人（当前用户）
     */
    public SimpleAssertion(Date validFromDate, Map<String, Object> attribute, AttributePrincipal principal) {
        this.validFromDate = validFromDate;
        this.attribute = attribute;
        this.principal = principal;
    }

    /**
     * 获取断言有效时间
     *
     * @return 有效时间
     */
    @Override
    public Date getValidFromDate() {
        return validFromDate;
    }

    /**
     * 获取断言属性
     *
     * @return 断言属性
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    /**
     * 获取当前人（当前用户）
     *
     * @return 当前人（当前用户）
     */
    @Override
    public AttributePrincipal getPrincipal() {
        return principal;
    }
}
