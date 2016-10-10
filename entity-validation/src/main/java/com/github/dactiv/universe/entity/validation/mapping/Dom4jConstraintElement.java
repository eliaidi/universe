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
import org.dom4j.Element;

/**
 * Dom4j 形式的约束元素时间
 *
 * @author maurice
 */
public class Dom4jConstraintElement implements ConstraintElement {
    // 元素接口
    private Element element;

    /**
     * Dom4j 形式的约束元素时间
     *
     * @param element 元素接口
     */
    public Dom4jConstraintElement(Element element) {
        this.element = element;
    }

    @Override
    public String attributeValue(String name) {
        return element.attributeValue(name);
    }

    @Override
    public String attributeValue(String name, String defaultValue) {
        return element.attributeValue(name, defaultValue);
    }
}
