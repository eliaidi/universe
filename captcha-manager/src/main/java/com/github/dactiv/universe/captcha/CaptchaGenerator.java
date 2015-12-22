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

package com.github.dactiv.universe.captcha;

import com.github.dactiv.universe.captcha.exception.CaptchaException;

import java.io.OutputStream;

/**
 * 验证码生成器
 *
 * @author maurice
 */
public interface CaptchaGenerator {

    /**
     * 生成验证码
     *
     * @param stream 图像流
     *
     * @return 验证码
     */
    String generate(OutputStream stream) throws CaptchaException;
}
