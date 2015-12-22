package com.github.dactiv.universe.map.validation;

import org.dom4j.Element;

/**
 * 验证约束接口，该接口主要是描述本次验证的约束是什么，如果验证错误时提示的错误信息是什么。
 *
 * @author maurice
 */
public interface Constraint {

    /**
     * 获取验证约束名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取本约束自定义的错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * 获取最终的错误信息
     *
     * @return 如果 {@link #getMessage()} 存在值，将获取 {@link #getMessage()} 的值否则为 {@link #getDefaultMessage()}
     */
    String getErrorMessage();

    /**
     * 获取默认的错误信息
     *
     * @return 默认的错误信息
     */
    String getDefaultMessage();

    /**
     * 获取 xml 元素对象
     *
     * @return 元素对象
     */
    Element getElement();

    /**
     * 设置本约束自定义的错误信息
     *
     * @param message 错误信息
     */
    void setMessage(String message);

    /**
     * 设置约束默认的错误信息
     *
     * @param message 错误信息
     */
    void setDefaultMessage(String message);
}
