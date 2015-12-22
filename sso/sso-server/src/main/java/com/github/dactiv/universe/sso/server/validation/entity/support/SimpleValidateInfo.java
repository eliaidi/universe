package com.github.dactiv.universe.sso.server.validation.entity.support;

import com.github.dactiv.universe.sso.server.validation.entity.ValidateInfo;

/**
 * 简单的验证信息实现
 *
 * @author maurice
 */
public class SimpleValidateInfo implements ValidateInfo {

    private String ticket;
    private String redirectUrl;

    /**
     * 简单的验证信息实现
     */
    public SimpleValidateInfo() {
    }

    /**
     * 简单的验证信息实现
     *
     * @param ticket      票据 id
     * @param redirectUrl 机构信息
     */
    public SimpleValidateInfo(String ticket, String redirectUrl) {
        this.ticket = ticket;
        this.redirectUrl = redirectUrl;
    }

    /**
     * 获取票据 id
     *
     * @return 票据 id
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 获取机构信息
     *
     * @return 机构信息
     */
    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }
}
