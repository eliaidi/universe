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
