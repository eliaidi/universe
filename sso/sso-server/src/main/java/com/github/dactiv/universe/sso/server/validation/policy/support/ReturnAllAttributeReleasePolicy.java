package com.github.dactiv.universe.sso.server.validation.policy.support;

import java.util.Map;

/**
 * 返回发布当事人（用户）所有信息的实现
 *
 * @author maurice
 */
public class ReturnAllAttributeReleasePolicy extends AbstractAttributeReleasePolicy {

    /**
     * 获取发布属性
     *
     * @param principalAttributes 当事人（用户）所有属性 map
     * @return 发布属性 map
     */
    @Override
    protected Map<String, Object> getAttributesInternal(Map<String, Object> principalAttributes) {
        return principalAttributes;
    }
}
