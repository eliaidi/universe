package com.github.dactiv.universe.map.validation.mapping;

import com.github.dactiv.universe.map.validation.MappingKey;
import com.github.dactiv.universe.map.validation.MappingMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的 xml 映射元数据接口实现
 *
 * @author maurice
 */
public class SimpleMappingMetadata implements MappingMetadata {

    private List<MappingKey> mappingKeys = new ArrayList<MappingKey>();

    /**
     * 简单的 xml 映射元数据接口实现
     *
     * @param mappingKeys
     */
    public SimpleMappingMetadata(List<MappingKey> mappingKeys) {
        this.mappingKeys = mappingKeys;
    }

    @Override
    public List<MappingKey> getKeys() {
        return mappingKeys;
    }
}
