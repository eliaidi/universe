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

package com.github.dactiv.universe.map.validation.valid.error;

import com.github.dactiv.universe.map.validation.ValidError;

/**
 * 简单的验证错误接口实现
 *
 * @author maurice
 */
public class SimpleValidError implements ValidError {
    // 错误字段名
    private String name;
    // 错误信息
    private String message;

    /**
     * 简单的验证错误接口实现
     * @param name 错误字段名
     * @param message 错误信息
     */
    public SimpleValidError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
