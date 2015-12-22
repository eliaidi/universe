package com.github.dactiv.universe.map.validation.valid.error;

import com.github.dactiv.universe.map.validation.ValidError;

/**
 * 简单的验证错误接口实现
 *
 * @author maurice
 */
public class SimpleValidError implements ValidError {
    // 错误字段名
    private String name;
    // 错误信息
    private String message;

    /**
     * 简单的验证错误接口实现
     * @param name 错误字段名
     * @param message 错误信息
     */
    public SimpleValidError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
