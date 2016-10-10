/*
 * Copyright 2016 dactiv
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
package com.github.dactiv.universe.entity.validation.mapping;

import com.github.dactiv.universe.entity.validation.ConstraintElement;

import java.util.HashMap;
import java.util.Map;

/**
 * map 条件约束元素
 *
 * @author maurice
 */
public class MapConstraintElement implements ConstraintElement {

    // map 集合
    private Map<String, Object> map = new HashMap<>();

    /**
     * map 条件约束元素
     *
     * @param map map 集合
     */
    public MapConstraintElement(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String attributeValue(String name) {
        return map.get(name).toString();
    }

    @Override
    public String attributeValue(String name, String defaultValue) {
        String value = attributeValue(name);
        return value == null ? defaultValue : value;
    }
}
