package com.github.dactiv.universe.captcha.exception;

/**
 * 验证码异常
 * 
 * @author maurice
 */
public class CaptchaException extends RuntimeException {

    /**
     * 验证码异常
     */
    public CaptchaException() {
        super();
    }

    /**
     * 验证码异常
     *
     * @param message 异常信息
     */
    public CaptchaException(String message) {
        super(message);
    }

    /**
     * 验证码异常
     *
     * @param message 异常信息
     * @param cause   异常类
     * @since 1.4
     */
    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 验证码异常
     *
     * @param cause 异常类
     * @since 1.4
     */
    public CaptchaException(Throwable cause) {
        super(cause);
    }
}
