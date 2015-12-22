package com.github.dactiv.universe.map.validation.exception;

/**
 * 验证异常，map 验证的异常基类
 *
 * @author maurice
 */
public class ValidationException extends RuntimeException {

    /**
     * 验证异常，map 验证的异常基类
     */
    public ValidationException() {
    }

    /**
     * 验证异常，map 验证的异常基类
     *
     * @param message 异常信息
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * 验证异常，map 验证的异常基类
     *
     * @param message 异常信息
     * @param cause 异常类
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 验证异常，map 验证的异常基类
     *
     * @param cause 异常类
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
