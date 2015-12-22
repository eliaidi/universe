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

import com.github.dactiv.universe.captcha.entity.Captcha;

import java.util.Date;

/**
 * 验证码验证失败的实现
 *
 * @author maurice
 */
public class FailureValidResult extends SuccessValidResult {

    // 下一个验证码
    private Captcha nextCaptcha;

    /**
     * 验证码验证成功的实现
     */
    public FailureValidResult(Captcha nextCaptcha) {
        this.nextCaptcha = nextCaptcha;
    }

    /**
     * 验证码验证成功的实现
     *
     * @param validTime 验证时间
     * @param message   验证信息
     */
    public FailureValidResult(Date validTime, String message, Captcha nextCaptcha) {
        super(validTime, message);
        this.nextCaptcha = nextCaptcha;
    }

    /**
     * 获取下一个验证码
     *
     * @return 下一个验证码
     */
    public Captcha getNextCaptcha() {
        return nextCaptcha;
    }
}
