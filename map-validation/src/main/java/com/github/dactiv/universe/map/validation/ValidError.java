package com.github.dactiv.universe.map.validation;

/**
 *
 * 验证错误接口，用于描述哪个字段验证不通过以及为什么。
 *
 * @author maurice
 */
public interface ValidError {

    /**
     * 获取验证错误的 map key 名称
     *
     * @return  如果 xml 里配置了 alias 属性, 将是 alias 值, 否则为 map key 名称
     */
    String getName();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
