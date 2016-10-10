/*
 * Copyright 2015 dactiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    // 映射信息接口集合
    private List<MappingKey> mappingKeys = new ArrayList<MappingKey>();

    /**
     * 简单的 xml 映射元数据接口实现
     *
     * @param mappingKeys key 映射信息接口集合
     */
    public SimpleMappingMetadata(List<MappingKey> mappingKeys) {
        this.mappingKeys = mappingKeys;
    }

    @Override
    public List<MappingKey> getKeys() {
        return mappingKeys;
    }
}
