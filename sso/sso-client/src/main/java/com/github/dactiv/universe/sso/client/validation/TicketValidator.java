package com.github.dactiv.universe.sso.client.validation;

import com.github.dactiv.universe.sso.client.exception.TicketValidationException;

/**
 * 票据验证器
 *
 * @author maurice
 */
public interface TicketValidator {

    /**
     * 验证票据
     *
     * @param ticket       票据
     * @param organization 机构信息
     *
     * @return 验证断言结果
     *
     * @throws TicketValidationException
     */
    Assertion validate(String ticket, String organization) throws TicketValidationException;
}
