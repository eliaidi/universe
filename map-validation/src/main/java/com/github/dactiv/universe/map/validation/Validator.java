package com.github.dactiv.universe.map.validation;

import java.util.Map;

/**
 * 验证器
 *
 * @author maurice
 *
 */
public interface Validator {

    /**
     * 验证 map
     *
     * @param key 当前验证的 map key 名称
     * @param source map 原始数据
     * @param constraint 本次验证的约束条件对象
     *
     * @return 通过返回 true, 否则返回 false.
     */
    boolean valid(String key, Map<String, Object> source, Constraint constraint);

    /**
     * 获取验证器名称
     *
     * @return 名称
     */
    String getName();
}
