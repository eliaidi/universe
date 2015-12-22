package com.github.dactiv.universe.map.validation.exception;

/**
 * 验证器找不到异常
 *
 * @author maurice
 */
public class ValidatorNotFoundException extends ValidationException {

    /**
     * 验证器找不到异常
     */
    public ValidatorNotFoundException() {
    }

    /**
     * 验证器找不到异常
     *
     * @param message 异常信息
     */
    public ValidatorNotFoundException(String message) {
        super(message);
    }

    /**
     * 验证器找不到异常
     *
     * @param message 异常信息
     * @param cause   异常类
     */
    public ValidatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 验证器找不到异常
     *
     * @param cause 异常类
     */
    public ValidatorNotFoundException(Throwable cause) {
        super(cause);
    }
}
