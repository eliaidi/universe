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

package com.github.dactiv.universe.captcha.entity.support;

import com.github.dactiv.universe.captcha.entity.ValidResult;

import java.util.Date;

/**
 * 验证码验证成功的实现
 *
 * @author maurice
 */
public class SuccessValidResult implements ValidResult{

    // 验证时间
    private Date validTime;
    // 验证信息
    private String message;

    /**
     * 验证码验证成功的实现
     */
    public SuccessValidResult() {
    }

    /**
     * 验证码验证成功的实现
     *
     * @param validTime 验证时间
     * @param message 验证信息
     */
    public SuccessValidResult(Date validTime, String message) {
        this.validTime = validTime;
        this.message = message;
    }

    /**
     * 获取验证时间
     *
     * @return 验证时间
     */
    @Override
    public Date getValidTime() {
        return validTime;
    }

    /**
     * 获取验证信息
     *
     * @return 信息
     */
    @Override
    public String getMessage() {
        return message;
    }

}
