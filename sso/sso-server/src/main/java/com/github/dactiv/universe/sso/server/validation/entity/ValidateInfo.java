package com.github.dactiv.universe.sso.server.validation.entity;

/**
 * 验证信息
 *
 * @author maurice
 */
public interface ValidateInfo {

    /**
     * 获取票据 id
     *
     * @return 票据 id
     */
    String getTicket();

    /**
     * 获取重定向 url
     *
     * @return 重定向 url
     */
    String getRedirectUrl();
}
