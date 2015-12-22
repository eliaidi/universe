package com.github.dactiv.universe.sso.client.exception;

/**
 * 票据验证异常
 * 
 * @author maurice
 */
public class TicketValidationException extends SsoClientException {

    /**
     * 票据验证异常
     */
    public TicketValidationException() {
        super();
    }

    /**
     * 票据验证异常
     *
     * @param message 异常信息
     */
    public TicketValidationException(String message) {
        super(message);
    }

    /**
     * 票据验证异常
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public TicketValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 票据验证异常
     *
     * @param cause 异常类
     */
    public TicketValidationException(Throwable cause) {
        super(cause);
    }
}
