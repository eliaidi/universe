package com.github.dactiv.universe.sso.client.exception;

/**
 * Sso 客户端异常
 * 
 * @author maurice
 */
public class SsoClientException extends RuntimeException {
    /**
     * Sso 客户端异常
     */
    public SsoClientException() {
        super();
    }

    /**
     * Sso 客户端异常
     *
     * @param message 异常信息
     */
    public SsoClientException(String message) {
        super(message);
    }

    /**
     * Sso 客户端异常
     *
     * @param message 异常信息
     * @param cause 异常类
     */
    public SsoClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Sso 客户端异常
     *
     * @param cause 异常类
     */
    public SsoClientException(Throwable cause) {
        super(cause);
    }
}
