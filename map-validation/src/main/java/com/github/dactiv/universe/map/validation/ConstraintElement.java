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
package com.github.dactiv.universe.map.validation;

/**
 * 约束条件元素
 *
 * @author maurice
 */
public interface ConstraintElement {

    /**
     * 获取属性值
     *
     * @param name 属性名称
     *
     * @return 值
     */
    String attributeValue(String name);

    /**
     * 获取属性值
     *
     * @param name 属性名称
     * @param defaultValue 如果找不到，默认值是什么
     *
     * @return 值
     */
    String attributeValue(String name,String defaultValue);

}
