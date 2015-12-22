package com.github.dactiv.universe.sso.client.exception;

/**
 * sso 认证异常
 * 
 * @author maurice
 */
public class SsoAuthenicationException extends SsoClientException {

    /**
     * sso 认证异常
     */
    public SsoAuthenicationException() {
        super();
    }

    /**
     * sso 认证异常
     *
     * @param message 异常信息
     */
    public SsoAuthenicationException(String message) {
        super(message);
    }

    /**
     * sso 认证异常
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public SsoAuthenicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * sso 认证异常
     *
     * @param cause 异常类
     */
    public SsoAuthenicationException(Throwable cause) {
        super(cause);
    }
}
