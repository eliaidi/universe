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

package com.github.dactiv.universe.shiro.filter;

import com.github.dactiv.universe.captcha.entity.ValidResult;
import com.github.dactiv.universe.captcha.generator.JpegImgCaptchaGenerator;
import com.github.dactiv.universe.captcha.support.HttpSessionCaptchaManager;
import com.github.dactiv.universe.shiro.exception.CaptchaException;
import com.github.dactiv.universe.shiro.filter.captcha.DisplayCaptchaCondition;
import com.github.dactiv.universe.shiro.filter.captcha.support.SimpleDisplayCaptchaCondition;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证码登录 过滤器
 *
 * @author maurice
 */
public class CaptchaAuthenticationFilter extends FormAuthenticationFilter implements InitializingBean {

    /**
     * 默认验证码参数名称
     */
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    /**
     * 默认验证码的超时时间
     */
    private static final long DEFAULT_CAPTCHA_EXPIRED_TIME = 1000 * 60;

    // 验证码参数名称
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    // 显示验证码条件
    private List<DisplayCaptchaCondition> displayCaptchaConditions = new ArrayList<DisplayCaptchaCondition>();
    // session 验证码管理
    private HttpSessionCaptchaManager httpSessionCaptchaManager;

    /**
     * 验证码登录认证 Filter
     */
    public CaptchaAuthenticationFilter() {
        displayCaptchaConditions.add(new SimpleDisplayCaptchaCondition());
    }

    /**
     * 验证是否已显示验证码
     *
     * @param request servlet 请求
     * @param response servlet 响应
     *
     * @return 不显示返回 true， 否则 false
     *
     * @throws Exception
     */
    protected boolean isDisplayCaptcha(ServletRequest request, ServletResponse response) throws Exception {
        for (DisplayCaptchaCondition dcc : getDisplayCaptchaConditions()) {
            if (dcc.isDisplay(request, response)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 重写父类方法，在 shiro 执行登录时先对比验证码，正确后在登录，否则直接登录失败
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        // 验证是否已展示了验证码，如果没展示验证码，直接做登录操作。
        if (!isDisplayCaptcha(request, response)) {
            return super.executeLogin(request, response);
        }
        HttpSession session = WebUtils.getHttpRequest(request).getSession();
        httpSessionCaptchaManager.setCurrentSession(session);
        // 获取当前验证码
        String currentCaptcha = getCaptcha(request);

        ValidResult validResult = httpSessionCaptchaManager.valid(session.getId(), currentCaptcha);

        if (!validResult.getIsValid()) {
            AuthenticationToken token = createToken(request, response);
            return onLoginFailure(token, new CaptchaException(validResult.getMessage()), request, response);
        }

        return super.executeLogin(request, response);
    }

    /**
     * 重写父类方法，当登录失败后，将 allowFailureCount（允许登错误录次） + 1
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e,
                                     ServletRequest request,
                                     ServletResponse response) {

        for (DisplayCaptchaCondition dcc : displayCaptchaConditions) {
           dcc.loginFailure(token, e, request, response);
        }

        return super.onLoginFailure(token, e, request, response);
    }

    /**
     * 重写父类方法，当登录成功后，将 allowFailureCount（允许登错误录次）设置为 0，重置下一次登录的状态
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {

        for (DisplayCaptchaCondition dcc : displayCaptchaConditions) {
            dcc.loginSuccess(token, subject, request, response);
        }

        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 设置验证码提交的参数名称
     *
     * @param captchaParam 验证码提交的参数名称
     */
    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    /**
     * 获取验证码提交的参数名称
     *
     * @return 验证码提交的参数名称
     */
    public String getCaptchaParam() {
        return captchaParam;
    }

    /**
     * 获取用户输入的验证码
     *
     * @param request ServletRequest
     *
     * @return 验证码
     */
    public String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    /**
     * 设置显示验证码条件集合
     *
     * @param displayCaptchaConditions 显示验证码条件集合
     */
    public void setDisplayCaptchaConditions(List<DisplayCaptchaCondition> displayCaptchaConditions) {
        this.displayCaptchaConditions = displayCaptchaConditions;
    }

    /**
     * 获取显示验证码条件集合
     *
     * @return 显示验证码条件集合
     */
    public List<DisplayCaptchaCondition> getDisplayCaptchaConditions() {
        return displayCaptchaConditions;
    }

    /**
     * 设置 session 验证码管理
     *
     * @param httpSessionCaptchaManager 验证码管理
     */
    public void setHttpSessionCaptchaManager(HttpSessionCaptchaManager httpSessionCaptchaManager) {
        this.httpSessionCaptchaManager = httpSessionCaptchaManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(httpSessionCaptchaManager == null) {
            httpSessionCaptchaManager = new HttpSessionCaptchaManager();
            httpSessionCaptchaManager.setCaptchaGenerator(new JpegImgCaptchaGenerator());
            httpSessionCaptchaManager.setExpiredTime(DEFAULT_CAPTCHA_EXPIRED_TIME);
        }
    }
}