package com.github.dactiv.universe.sso.server.exception;

/**
 * 
 * 找不到票据异常
 * 
 * @author maurice
 */
public class TicketNotFoundException extends SsoServerException {

    /**
     * 找不到票据异常
     */
    public TicketNotFoundException() {
        super();
    }

    /**
     * 找不到票据异常
     *
     * @param message 异常信息
     */
    public TicketNotFoundException(String message) {
        super(message);
    }

    /**
     * 找不到票据异常
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public TicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 找不到票据异常
     *
     * @param cause 异常类
     */
    public TicketNotFoundException(Throwable cause) {
        super(cause);
    }
}
