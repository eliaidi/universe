package com.github.dactiv.universe.sso.client.authentication;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

/**
 * 当属性的当事人（用户）信息
 *
 * @author maurice
 */
public interface AttributePrincipal extends Principal, Serializable {

    /**
     * 获取附加属性
     *
     * @return 附加属性
     */
    Map<String,Object> getAttributes();
}
