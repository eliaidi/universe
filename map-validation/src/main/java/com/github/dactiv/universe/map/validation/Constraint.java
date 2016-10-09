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

/**
 * 验证约束接口，该接口主要是描述本次验证的约束是什么，如果验证错误时提示的错误信息是什么。
 *
 * @author maurice
 */
public interface Constraint {

    /**
     * 获取验证约束名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取本约束自定义的错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * 获取最终的错误信息
     *
     * @return 如果 {@link #getMessage()} 存在值，将获取 {@link #getMessage()} 的值否则为 {@link #getDefaultMessage()}
     */
    String getErrorMessage();

    /**
     * 获取默认的错误信息
     *
     * @return 默认的错误信息
     */
    String getDefaultMessage();

    /**
     * 获取 xml 元素对象
     *
     * @return 元素对象
     */
    ConstraintElement getElement();

    /**
     * 设置本约束自定义的错误信息
     *
     * @param message 错误信息
     */
    void setMessage(String message);

    /**
     * 设置约束默认的错误信息
     *
     * @param message 错误信息
     */
    void setDefaultMessage(String message);
}
