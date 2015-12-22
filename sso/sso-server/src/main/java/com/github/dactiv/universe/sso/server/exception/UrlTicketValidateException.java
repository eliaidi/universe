package com.github.dactiv.universe.sso.server.exception;

/**
 * url 验证票据异常处理
 *
 * @author maurice
 */
public class UrlTicketValidateException extends SsoServerException {

    /**
     * url 验证票据异常处理
     */
    public UrlTicketValidateException() {
    }

    /**
     * url 验证票据异常处理
     *
     * @param message 异常信息
     */
    public UrlTicketValidateException(String message) {
        super(message);
    }

    /**
     * url 验证票据异常处理
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public UrlTicketValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * url 验证票据异常处理
     *
     * @param cause 异常类
     */
    public UrlTicketValidateException(Throwable cause) {
        super(cause);
    }
}
