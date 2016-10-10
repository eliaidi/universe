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

package com.github.dactiv.universe.map.validation.exception;

/**
 * 验证异常，map 验证的异常基类
 *
 * @author maurice
 */
public class ValidationException extends RuntimeException {

    /**
     * 验证异常，map 验证的异常基类
     */
    public ValidationException() {
    }

    /**
     * 验证异常，map 验证的异常基类
     *
     * @param message 异常信息
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * 验证异常，map 验证的异常基类
     *
     * @param message 异常信息
     * @param cause 异常类
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 验证异常，map 验证的异常基类
     *
     * @param cause 异常类
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
