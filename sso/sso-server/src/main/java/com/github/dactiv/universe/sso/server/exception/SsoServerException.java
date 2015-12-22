package com.github.dactiv.universe.sso.server.exception;

/**
 * sso 服务器异常处理
 *
 * @author maurice
 */
public class SsoServerException extends RuntimeException {

    /**
     * sso 异常处理
     */
    public SsoServerException() {
        super();
    }

    /**
     * sso 异常处理
     *
     * @param message 异常信息
     */
    public SsoServerException(String message) {
        super(message);
    }

    /**
     * sso 异常处理
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public SsoServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * sso 异常处理
     *
     * @param cause 异常类
     */
    public SsoServerException(Throwable cause) {
        super(cause);
    }

}
