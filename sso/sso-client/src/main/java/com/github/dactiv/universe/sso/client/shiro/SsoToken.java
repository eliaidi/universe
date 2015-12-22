package com.github.dactiv.universe.sso.client.shiro;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * sso 令牌
 *
 * @author maurice
 */
public class SsoToken implements RememberMeAuthenticationToken{

    // sso 认证票据
    private String ticket;
    // 是否记住我
    private boolean rememberMe;
    // 认证后的当事人（用户）
    private Object principal;

    /**
     * sso 令牌
     *
     * @param ticket 认证票据
     */
    public SsoToken(String ticket) {
        this.ticket = ticket;
    }

    /**
     * 设置当事人（用户）
     *
     * @param principal 当事人（用户）
     */
    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    /**
     * 设置是否为记住我
     *
     * @param rememberMe true 表示是，否则 false
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * 是否为记住我
     *
     * @return true 表示是，否则 false
     */
    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * 获取当事人（用户）
     *
     * @return 当事人（用户）
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * 获取认证令牌
     *
     * @return 令牌信息
     */
    @Override
    public Object getCredentials() {
        return ticket;
    }
}
