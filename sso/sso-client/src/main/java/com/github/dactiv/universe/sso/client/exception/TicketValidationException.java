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

package com.github.dactiv.universe.sso.client.exception;

/**
 * 票据验证异常
 * 
 * @author maurice
 */
public class TicketValidationException extends SsoClientException {

    /**
     * 票据验证异常
     */
    public TicketValidationException() {
        super();
    }

    /**
     * 票据验证异常
     *
     * @param message 异常信息
     */
    public TicketValidationException(String message) {
        super(message);
    }

    /**
     * 票据验证异常
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public TicketValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 票据验证异常
     *
     * @param cause 异常类
     */
    public TicketValidationException(Throwable cause) {
        super(cause);
    }
}
