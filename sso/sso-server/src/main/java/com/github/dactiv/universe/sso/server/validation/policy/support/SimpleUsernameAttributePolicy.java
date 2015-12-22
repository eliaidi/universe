package com.github.dactiv.universe.sso.server.validation.policy.support;

import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import com.github.dactiv.universe.sso.server.validation.policy.UsernameAttributePolicy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Map;

/**
 * 简单的获取用户名属性政策
 *
 * @author maurice
 */
public class SimpleUsernameAttributePolicy implements UsernameAttributePolicy {

    /**
     * 默认的用户名属性名称
     */
    public final static String DEFAULT_USERNAME_ATTRIBUTE_NAME = "username";
    // 用户名属性名称
    private String usernameAttributeName = DEFAULT_USERNAME_ATTRIBUTE_NAME;

    /**
     * 简单的获取用户名属性政策
     */
    public SimpleUsernameAttributePolicy() {

    }

    /**
     * 简单的获取用户名属性政策
     *
     * @param usernameAttributeName 用户名属性名称
     */
    public SimpleUsernameAttributePolicy(String usernameAttributeName) {
        this.usernameAttributeName = usernameAttributeName;
    }

    /**
     * 获取用户名
     *
     * @param principal    当事人（用户）
     * @param organization 家机构信息
     * @return 用户名
     */
    @Override
    public String getUsername(Object principal, Organization organization) {

        if (Map.class.isAssignableFrom(principal.getClass())) {
            return ((Map) principal).get(usernameAttributeName).toString();
        } else if (principal instanceof Principal) {
            return ((Principal) principal).getName();
        } else {
            return findUsernameValue(principal);
        }
    }

    /**
     * 获取用户名值
     *
     * @param principal 当时人
     * @return 用户名值
     */
    private String findUsernameValue(Object principal) {
        Field field = ReflectionUtils.findField(principal.getClass(), usernameAttributeName);
        return ReflectionUtils.getField(field, principal).toString();
    }

    /**
     * 设置用户名属性名称
     *
     * @param usernameAttributeName 用户名属性名称
     */
    public void setUsernameAttributeName(String usernameAttributeName) {
        this.usernameAttributeName = usernameAttributeName;
    }
}
