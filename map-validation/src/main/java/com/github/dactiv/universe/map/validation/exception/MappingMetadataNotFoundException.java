package com.github.dactiv.universe.map.validation.exception;

/**
 * 找不到映射元数据异常
 *
 * @author maurice
 */
public class MappingMetadataNotFoundException extends ValidationException {

    /**
     * 找不到映射元数据异常
     */
    public MappingMetadataNotFoundException() {
    }

    /**
     * 找不到映射元数据异常
     *
     * @param message 异常信息
     */
    public MappingMetadataNotFoundException(String message) {
        super(message);
    }

    /**
     * 找不到映射元数据异常
     *
     * @param message 异常信息
     * @param cause 异常类
     */
    public MappingMetadataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 找不到映射元数据异常
     * @param cause 异常类
     */
    public MappingMetadataNotFoundException(Throwable cause) {
        super(cause);
    }
}
