package com.github.dactiv.universe.captcha.support;

import com.github.dactiv.universe.captcha.entity.Captcha;

import javax.servlet.http.HttpSession;

/**
 * session 验证码管理
 *
 * @author maurice
 */
public class SessionCaptchaManager extends AbstractCaptchaManager {

    /**
     * 存储在 session 的 attribute 名称的默认值
     */
    public final static String DEFAULT_CAPTCHA_ATTRIBUTE_NAME = "SESSION_CAPTCHA";
    /** 存储在 session 的 attribute 名称 */
    private String captchaAttributeName = DEFAULT_CAPTCHA_ATTRIBUTE_NAME;
    /** 当前session */
    private HttpSession session;

    /**
     * 设置当前 session
     *
     * @param session 当前 session
     */
    public void setCurrentSession(final HttpSession session) {
        this.session = session;
    }

    /**
     * 保存验证码
     *
     * @param captcha 验证码
     */
    @Override
    public void save(Captcha captcha) {
        session.setAttribute(captchaAttributeName, captcha);
    }

    /**
     * 删除验证码
     *
     * @param id 主键 id
     */
    @Override
    public void delete(String id) {
        session.removeAttribute(captchaAttributeName);
    }

    /**
     * 获取验证码
     *
     * @param id 验证码 id
     * @return 验证码实体
     */
    @Override
    public Captcha get(String id) {
        return (Captcha) session.getAttribute(captchaAttributeName);
    }

    /**
     * 获取新的验证码主键 id
     *
     * @return 主键 id
     */
    @Override
    protected String getNewId() {
        return session.getId();
    }
}
