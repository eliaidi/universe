package com.github.dactiv.universe.sso.server.validation.repository;

import java.io.Serializable;
import java.util.Map;

/**
 * 当事人（用户）属性库
 *
 * @author maurice
 */
public interface PrincipalAttributesRepository extends Serializable {

    /**
     * 获取当时人（用户）所有属性
     *
     * @param principal 当时人（用户）
     * @return 所有属性
     */
    Map<String, Object> getAttributes(Object principal);

}
