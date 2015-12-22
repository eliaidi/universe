package com.github.dactiv.universe.sso.server.validation.policy;

import java.io.Serializable;
import java.util.Map;

/**
 * 发布当事人（用户）信息接口
 *
 * @author maurice
 */
public interface AttributeReleasePolicy extends Serializable {

    /**
     * 是否发用户登录密码
     *
     * @return true 表示是，否则 false
     */
    boolean isReleaseCredentialPassword();

    /**
     * 是否发布票据信息
     *
     * @return true 表示是，否则 false
     */
    boolean isReleaseTicket();

    /**
     * 获取发布属性
     *
     * @param principal 当事人（用户）
     * @return 属性 map
     */
    Map<String, Object> getAttributes(Object principal);
}
