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
