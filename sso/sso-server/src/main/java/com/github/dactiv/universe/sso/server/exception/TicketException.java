package com.github.dactiv.universe.sso.server.exception;

/**
 * 票据异常处理
 *
 * @author maurice
 */
public class TicketException extends SsoServerException {

    /**
     * 票据异常处理
     */
    public TicketException() {
        super();
    }

    /**
     * 票据异常处理
     *
     * @param message 异常信息
     */
    public TicketException(String message) {
        super(message);
    }

    /**
     * 票据异常处理
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public TicketException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 票据异常处理
     *
     * @param cause 异常类
     */
    public TicketException(Throwable cause) {
        super(cause);
    }
}
