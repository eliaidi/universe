package com.github.dactiv.universe.sso.server.ticket.generator;

/**
 * 用于生成序列的接口
 *
 * @author maurice
 */
public interface NumericGenerator {

    /**
     * 获取下一个序列的 string 值
     *
     * @return 下一个序列的 string 值
     */
    String getNextNumberAsString();

    /**
     * 获取序列最大长度值
     *
     * @return 最大长度值
     */
    int getMaximumLength();

    /**
     * 获取序列最小长度值
     *
     * @return 最小值
     */
    int getMinimumLength();
}
