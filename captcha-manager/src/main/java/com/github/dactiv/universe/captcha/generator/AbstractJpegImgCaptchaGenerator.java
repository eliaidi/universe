/*
 * Copyright 2016 dactiv
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
package com.github.dactiv.universe.captcha.generator;

import com.github.dactiv.universe.captcha.CaptchaGenerator;

import java.util.Random;

/**
 * 抽象的图片验证码实现，该实现用于定义验证码内容，由子类集成实现如何绘制
 *
 * @author maurice
 */
public abstract class AbstractJpegImgCaptchaGenerator implements CaptchaGenerator{

    /**
     * 验证码内容
     */
    private static final String CODE = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 使用指定源生成验证码
     *
     * @param size 验证码长度
     *
     * @return 验证码
     */
    public static String generateCaptcha(int size){
        String sources = CODE;
        int codesLen = sources.length();
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(size);
        for(int i = 0; i < size; i++){
            verifyCode.append(sources.charAt(random.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }
}
