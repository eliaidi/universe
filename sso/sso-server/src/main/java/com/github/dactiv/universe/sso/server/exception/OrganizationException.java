package com.github.dactiv.universe.sso.server.exception;

/**
 * 机构异常
 *
 * @author maurice
 */
public class OrganizationException extends SsoServerException {

    /**
     * 机构异常处理
     */
    public OrganizationException() {
        super();
    }

    /**
     * 机构异常处理
     *
     * @param message 异常信息
     */
    public OrganizationException(String message) {
        super(message);
    }

    /**
     * 机构异常处理
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public OrganizationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 机构异常处理
     *
     * @param cause 异常类
     */
    public OrganizationException(Throwable cause) {
        super(cause);
    }
}
