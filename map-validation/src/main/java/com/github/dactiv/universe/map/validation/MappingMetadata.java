package com.github.dactiv.universe.map.validation;

import java.util.List;

/**
 * xml 映射元数据接口，主要存在 xml key 节点映射
 *
 * @author maurice
 */
public interface MappingMetadata {

    /**
     * 获取 key 映射信息
     *
     * @return key 映射信息集合
     */
    List<MappingKey> getKeys();
}
