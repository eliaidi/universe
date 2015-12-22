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
