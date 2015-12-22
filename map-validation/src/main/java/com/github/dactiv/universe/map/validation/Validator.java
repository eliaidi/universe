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
