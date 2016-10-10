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

package com.github.dactiv.universe.entity.validation.mapping;

import com.github.dactiv.universe.entity.validation.Constraint;
import com.github.dactiv.universe.entity.validation.ConstraintElement;

/**
 * 简单的验证约束接口
 *
 * @author maurice
 */
public class SimpleConstraint implements Constraint {

    // 验证约束名称
    private String name;
    // 验证约束自定义信息
    private String message;
    // 验证约束默认信息
    private ConstraintElement element;
    // 验证约束元素
    private String defaultMessage;

    /**
     * 简单的验证约束接口
     *
     * @param name 验证约束名称
     * @param message 验证约束自定义信息
     * @param defaultMessage 验证约束默认信息
     * @param element 验证约束元素
     */
    public SimpleConstraint(String name, String message, String defaultMessage, ConstraintElement element) {
        this.name = name;
        this.message = message;
        this.element = element;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getErrorMessage() {
        return message == null || "".equals(message) ? this.defaultMessage : message;
    }

    @Override
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    @Override
    public ConstraintElement getElement() {
        return element;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setDefaultMessage(String message) {
        this.defaultMessage = message;
    }
}
