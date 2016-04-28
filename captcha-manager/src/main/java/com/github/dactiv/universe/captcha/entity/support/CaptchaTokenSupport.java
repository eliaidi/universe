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

import com.github.dactiv.universe.captcha.entity.CaptchaToken;

import java.io.OutputStream;

/**
 * 验证码令牌实现类
 *
 * @author maurice
 */
public class CaptchaTokenSupport implements CaptchaToken {

    // 图像流
    private OutputStream outputStream;

    /**
     * 验证码令牌实现类
     */
    public CaptchaTokenSupport() {

    }

    /**
     * 获取图像流
     *
     * @return 图像流
     */
    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * 设置图像流
     *
     * @param outputStream 图像流
     */
    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
