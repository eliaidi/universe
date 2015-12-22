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

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * 显示验证码条件
 *
 * @author maurice
 *
 */
public interface DisplayCaptchaCondition {

    /**
     * 是否已显示验证码
     *
     * @param request servlet 请求对象
     * @param response servlet 响应对象
     *
     * @return true 表示是，否则 false
     */
    public boolean isDisplay(ServletRequest request,  ServletResponse response) throws Exception;

    /**
     * 登录失败后的处理
     *
     * @param token 认证令牌
     * @param e 认证异常
     * @param request servlet 请求对象
     * @param response servlet 响应对象
     */
    public void loginFailure(AuthenticationToken token,
                             AuthenticationException e,
                             ServletRequest request,
                             ServletResponse response);

    /**
     * 登录成功后的处理
     *
     * @param token 认证令牌
     * @param subject 当前 subject
     * @param request servlet 请求对象
     * @param response servlet 响应对象
     */
    public void loginSuccess(AuthenticationToken token,
                             Subject subject,
                             ServletRequest request,
                             ServletResponse response) throws Exception ;

}
