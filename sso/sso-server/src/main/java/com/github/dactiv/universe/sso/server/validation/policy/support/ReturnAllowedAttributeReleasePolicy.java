package com.github.dactiv.universe.sso.server.validation.policy.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 返回发布当事人（用户）指定信息的实现
 *
 * @author maurice
 */
public class ReturnAllowedAttributeReleasePolicy extends AbstractAttributeReleasePolicy {

    // 允许发布的字段
    private List<String> allowedAttributes = new ArrayList<>();

    /**
     * 返回发布当事人（用户）指定信息的实现
     */
    public ReturnAllowedAttributeReleasePolicy() {

    }

    /**
     * 返回发布当事人（用户）指定信息的实现
     */
    public ReturnAllowedAttributeReleasePolicy(List<String> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
    }

    /**
     * 获取发布属性
     *
     * @param principalAttributes 当事人（用户）所有属性 map
     *
     * @return 发布属性 map
     */
    @Override
    protected Map<String, Object> getAttributesInternal(Map<String, Object> principalAttributes) {
        Map<String, Object> attributesToRelease = new HashMap<>(principalAttributes.size());

        for (final String attribute : this.allowedAttributes) {
            final Object value = principalAttributes.get(attribute);

            if (value != null) {
                attributesToRelease.put(attribute, value);
            }
        }
        return attributesToRelease;
    }


    /**
     * 设置允许发布的属性
     *
     * @param allowedAttributes 属性名称
     */
    public void setAllowedAttributes(List<String> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
    }
}
