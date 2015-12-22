package com.github.dactiv.universe.sso.client.validation;

import com.github.dactiv.universe.sso.client.authentication.AttributePrincipal;

import java.util.Date;
import java.util.Map;

/**
 * 验证断言
 *
 * @author maurice
 */
public interface Assertion {

    /**
     * 获取断言有效时间
     *
     * @return 有效时间
     */
    Date getValidFromDate();

    /**
     * 获取断言属性
     *
     * @return 断言属性
     */
    Map<String, Object> getAttributes();

    /**
     * 获取当前人（当前用户）
     *
     * @return 当前人（当前用户）
     */
    AttributePrincipal getPrincipal();
}
