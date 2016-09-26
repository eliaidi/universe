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

package com.github.dactiv.universe.captcha.generator;

import com.github.dactiv.universe.captcha.CaptchaGenerator;
import com.github.dactiv.universe.captcha.entity.CaptchaToken;
import com.github.dactiv.universe.captcha.exception.CaptchaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * jpeg图片的验证码生成器
 *
 * @author maurice
 *
 */
public class JpegImgMultiCaptchaGenerator implements CaptchaGenerator {

    private List<CaptchaGenerator> generatorList = new ArrayList<>();

    private Random random = new Random();

    public JpegImgMultiCaptchaGenerator() {
        generatorList.add(new TencentCaptchaGenerator());
    }

    public JpegImgMultiCaptchaGenerator(java.util.List<CaptchaGenerator> generatorList) {
        this.generatorList = generatorList;
    }

    @Override
    public String generate(CaptchaToken token) throws CaptchaException {
        if (generatorList.isEmpty()) {
            throw new CaptchaException("验证码生成器列表为空");
        }
        return generatorList.get(random.nextInt(generatorList.size())).generate(token);
    }

    public void setGeneratorList(java.util.List<CaptchaGenerator> generatorList) {
        this.generatorList = generatorList;
    }
}
