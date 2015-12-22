package com.github.dactiv.universe.shiro.filter.captcha.support;

import com.github.dactiv.universe.shiro.filter.captcha.DisplayCaptchaCondition;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * 简单的展示验证码条件，当用户登录失败达到指定次数时，显示验证码
 *
 * @author maurice
 */
public class SimpleDisplayCaptchaCondition implements DisplayCaptchaCondition {

    /**
     * 默认允许登录错误次数
     */
    private static final int DEFAULT_ALLOW_FAILURE_COUNT = 1;

    /**
     * 默认在 session 中存储的允许登录错误次数的 key 名称
     */
    private static final String DEFAULT_LOGIN_FAILURE_COUNT_KEY_ATTRIBUTE = "LOGIN_FAILURE_COUNT";

    // 允许登录错误次数，当登录次数大于该数值时，会在页面中显示验证码
    private int allowFailureCount = DEFAULT_ALLOW_FAILURE_COUNT;

    // 在 session 中存储的允许登录错误次数的 key 名称
    private String loginFailureCountKeyAttribute = DEFAULT_LOGIN_FAILURE_COUNT_KEY_ATTRIBUTE;

    /**
     * 是否已显示验证码
     *
     * @param request  servlet 请求对象
     * @param response servlet 响应对象
     *
     * @return true 表示是，否则 false
     */
    @Override
    public boolean isDisplay(ServletRequest request, ServletResponse response) throws Exception {

        Session session = SecurityUtils.getSubject().getSession();

        // 获取登录错误次数
        Integer number = (Integer) session.getAttribute(getLoginFailureCountKeyAttribute());

        // 首次登录，将该数量记录在session中
        if (number == null) {
            number = 1;
            session.setAttribute(getLoginFailureCountKeyAttribute(), number);
        }

        return number > getAllowFailureCount();
    }

    /**
     * 登录失败后的处理
     *
     * @param token    认证令牌
     * @param e        认证异常
     * @param request  servlet 请求对象
     * @param response servlet 响应对象
     */
    @Override
    public void loginFailure(AuthenticationToken token,
                             AuthenticationException e,
                             ServletRequest request,
                             ServletResponse response) {
        // 将错误次数 + 1
        Session session = SecurityUtils.getSubject().getSession();
        Integer number = (Integer) session.getAttribute(getLoginFailureCountKeyAttribute());
        session.setAttribute(getLoginFailureCountKeyAttribute(), ++number);
    }

    /**
     * 登录成功后的处理
     *
     * @param token    认证令牌
     * @param subject  当前 subject
     * @param request  servlet 请求对象
     * @param response servlet 响应对象
     */
    @Override
    public void loginSuccess(AuthenticationToken token,
                             Subject subject,
                             ServletRequest request,
                             ServletResponse response) throws Exception {
        // 清除存储在 session 中的登录错误次数
        Session session = subject.getSession();
        session.removeAttribute(getLoginFailureCountKeyAttribute());

    }

    /**
     * 获取允许登录错误的次数
     *
     * @return 允许登录错误的次数
     */
    public int getAllowFailureCount() {
        return allowFailureCount;
    }

    /**
     * 设置允许登录错误的次数
     *
     * @param allowFailureCount 允许登录错误的次数
     */
    public void setAllowFailureCount(int allowFailureCount) {
        this.allowFailureCount = allowFailureCount;
    }

    /**
     * 获取在 session 中存储的允许登录错误次数的 key 名称
     *
     * @return key 名称
     */
    public String getLoginFailureCountKeyAttribute() {
        return loginFailureCountKeyAttribute;
    }

    /**
     * 设置在 session 中存储的允许登录错误次数的 key 名称
     *
     * @param loginFailureCountKeyAttribute key 名称
     */
    public void setLoginFailureCountKeyAttribute(String loginFailureCountKeyAttribute) {
        this.loginFailureCountKeyAttribute = loginFailureCountKeyAttribute;
    }
}
