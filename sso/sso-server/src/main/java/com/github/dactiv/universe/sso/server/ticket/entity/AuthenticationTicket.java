package com.github.dactiv.universe.sso.server.ticket.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 认证票据
 *
 * @author maurice
 */
public interface AuthenticationTicket extends Ticket {

    /**
     * 获取认证成功的当事人（用户）
     *
     * @return 当事人（用户）信息
     */
    Object getPrincipal();

    /**
     * 获取认证令牌
     *
     * @return 认证令牌
     */
    AuthenticationToken getAuthenticationToken();
}
