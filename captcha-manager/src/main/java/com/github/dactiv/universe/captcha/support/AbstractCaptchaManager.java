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

package com.github.dactiv.universe.captcha.support;

import com.github.dactiv.universe.captcha.CaptchaGenerator;
import com.github.dactiv.universe.captcha.CaptchaManager;
import com.github.dactiv.universe.captcha.entity.Captcha;
import com.github.dactiv.universe.captcha.entity.CaptchaToken;
import com.github.dactiv.universe.captcha.entity.ValidResult;
import com.github.dactiv.universe.captcha.entity.support.SimpleCaptcha;
import com.github.dactiv.universe.captcha.entity.support.ValidResultSupport;
import com.github.dactiv.universe.captcha.exception.CaptchaException;
import com.github.dactiv.universe.captcha.exception.CaptchaNotFoundException;
import com.github.dactiv.universe.captcha.exception.CaptchaNotMatchException;
import com.github.dactiv.universe.captcha.exception.CaptchaTimeoutException;
import com.github.dactiv.universe.captcha.generator.JpegImgMultiCaptchaGenerator;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * 抽象验证码管理实现
 *
 * @author maurice
 */
public abstract class AbstractCaptchaManager implements CaptchaManager {

    /**
     * 验证码超时时间默认值
     */
    public final static long DEFAULT_EXPIRED_TIME = 30L * 60L * 1000L;
    /**
     * 验证码生成器
     */
    private CaptchaGenerator captchaGenerator = new JpegImgMultiCaptchaGenerator();
    /**
     * 当前验证码
     */
    private ThreadLocal<CaptchaToken> currentCaptchaToken = new ThreadLocal<>();
    /**
     * 验证码超时时间
     */
    private long expiredTime = DEFAULT_EXPIRED_TIME;

    /**
     * 设置验证码生成器
     *
     * @param captchaGenerator 生成器
     */
    public void setCaptchaGenerator(CaptchaGenerator captchaGenerator) {
        this.captchaGenerator = captchaGenerator;
    }

    /**
     * 设置验证码超时时间
     *
     * @param expiredTime 超时时间
     */
    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * 获取验证码生成器
     *
     * @return 验证码生成器
     */
    public CaptchaGenerator getCaptchaGenerator() {
        return captchaGenerator;
    }

    /**
     * 获取验证码超时时间
     *
     * @return 超时时间
     */
    public long getExpiredTime() {
        return expiredTime;
    }

    /**
     * 创建验证码
     *
     * @return 验证码实体
     */
    @Override
    public Captcha create(CaptchaToken token) {

        if (token == null) {
            return null;
        }

        currentCaptchaToken.set(token);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        token.setOutputStream(outputStream);
        String code = captchaGenerator.generate(token);
        SimpleCaptcha simpleCaptcha = new SimpleCaptcha(getNewId(), code.toUpperCase(), new Date(), outputStream.toByteArray());

        save(simpleCaptcha);

        return simpleCaptcha;
    }

    /**
     * 获取新的验证码主键 id
     *
     * @return 主键 id
     */
    protected abstract String getNewId();


    /**
     * 验证验证码
     *
     * @param id   操作仓库
     * @param code 验证码
     * @return 验证信息
     */
    @Override
    public ValidResult valid(String id, String code) {

        ValidResult validResult;

        try {

            if (code == null || "".equals(code)) {
                throw new CaptchaException("input captcha can't be null or \"\" ");
            }

            Captcha captcha = get(id);

            if (captcha == null) {
                throw new CaptchaNotFoundException("id: [" + id + "] captcha not found.");
            }

            if (System.currentTimeMillis() - captcha.getCreationTime().getTime() > expiredTime) {
                throw new CaptchaTimeoutException("id: [" + id + "] captcha time out.");
            }
            String input = code.toUpperCase();
            if (!captcha.getCode().equals(input) ) {
                throw new CaptchaNotMatchException("id: [" + id + "] captcha not match, input is :" + input + ", cache is :" + captcha.getCode());
            }

            delete(id);
            validResult = new ValidResultSupport(new Date(), "success", Boolean.TRUE);
        } catch (Exception e) {
            delete(id);
            validResult = new ValidResultSupport(new Date(), e.getMessage(), Boolean.FALSE);
        }

        return validResult;
    }

}
