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

package com.github.dactiv.universe.sso.server.exception;

/**
 * sso 服务器异常处理
 *
 * @author maurice
 */
public class SsoServerException extends RuntimeException {

    /**
     * sso 异常处理
     */
    public SsoServerException() {
        super();
    }

    /**
     * sso 异常处理
     *
     * @param message 异常信息
     */
    public SsoServerException(String message) {
        super(message);
    }

    /**
     * sso 异常处理
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public SsoServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * sso 异常处理
     *
     * @param cause 异常类
     */
    public SsoServerException(Throwable cause) {
        super(cause);
    }

}
