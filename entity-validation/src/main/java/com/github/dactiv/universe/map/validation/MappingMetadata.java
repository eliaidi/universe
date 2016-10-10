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
