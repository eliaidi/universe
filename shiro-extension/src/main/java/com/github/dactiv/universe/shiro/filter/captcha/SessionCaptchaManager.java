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

package com.github.dactiv.universe.shiro.filter.captcha;

import com.github.dactiv.universe.captcha.entity.Captcha;
import com.github.dactiv.universe.captcha.support.AbstractCaptchaManager;
import org.apache.shiro.session.Session;

/**
 * shiro session 的验证码管理实现
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
    /** 当前 session */
    private Session session;

    /**
     * 设置当前 session
     *
     * @param session 当前 session
     */
    public void setCurrentSession(Session session) {
        this.session = session;
    }

    /**
     * 获取新的验证码主键 id
     *
     * @return 主键 id
     */
    @Override
    protected String getNewId() {
        return session.getId().toString();
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
     *
     * @return 验证码实体
     */
    @Override
    public Captcha get(String id) {
        return (Captcha) session.getAttribute(captchaAttributeName);
    }
}
