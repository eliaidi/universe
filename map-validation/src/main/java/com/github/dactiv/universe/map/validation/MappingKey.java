package com.github.dactiv.universe.map.validation;

import java.util.List;

/**
 * key 映射信息接口，该接口主要是描述 key 节点里面有什么
 *
 * @author maurice
 */
public interface MappingKey {

    /**
     * 获取验证约束
     *
     * @return 验证约束集合
     */
    List<Constraint> getConstraints();

    /**
     * 获取 key 名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取 key 的别名
     *
     * @return 别名
     */
    String getAlias();

    /**
     * 获取最终的 key 名称
     *
     * @return 如果 {@link #getAlias()} 有值，将是 {@link #getAlias()} 的值，否则为 {@link #getName()}
     */
    String getKeyName();
}
