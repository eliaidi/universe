package com.github.dactiv.universe.captcha.support;

import com.github.dactiv.universe.captcha.CaptchaGenerator;
import com.github.dactiv.universe.captcha.CaptchaManager;
import com.github.dactiv.universe.captcha.entity.Captcha;
import com.github.dactiv.universe.captcha.entity.ValidResult;
import com.github.dactiv.universe.captcha.entity.support.FailureValidResult;
import com.github.dactiv.universe.captcha.entity.support.SimpleCaptcha;
import com.github.dactiv.universe.captcha.entity.support.SuccessValidResult;
import com.github.dactiv.universe.captcha.exception.CaptchaException;
import com.github.dactiv.universe.captcha.generator.JpegImgCaptchaGenerator;

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
    private CaptchaGenerator captchaGenerator = new JpegImgCaptchaGenerator();
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
    public Captcha create() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String code = captchaGenerator.generate(outputStream);
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

            Captcha captcha = get(id);

            if (captcha == null) {
                throw new CaptchaException("找不到验证码");
            }

            if (!captcha.getCode().equals(code.toUpperCase())) {
                throw new CaptchaException("验证码不正确");
            }

            if (System.currentTimeMillis() - captcha.getCreationTime().getTime() > expiredTime) {
                throw new CaptchaException("验证码超时");
            }

            delete(id);
            validResult = new SuccessValidResult(new Date(), "验证成功");
        } catch (Exception e) {
            delete(id);
            Captcha nextCaptcha = create();
            validResult = new FailureValidResult(new Date(), e.getMessage(), nextCaptcha);
        }

        return validResult;
    }

}
