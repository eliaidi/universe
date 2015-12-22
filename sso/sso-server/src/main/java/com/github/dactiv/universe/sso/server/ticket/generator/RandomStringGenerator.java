package com.github.dactiv.universe.sso.server.ticket.generator;

/**
 * 随机字符生成器
 *
 * @author maurice
 */
public interface RandomStringGenerator {

    /**
     * 获取需要生成字符的最大长度
     *
     * @return 最大长度值
     */
    int getMinimumLength();

    /**
     * 获取需要生成字符的最小长度
     *
     * @return 最小长度值
     */
    int getMaximumLength();

    /**
     * 创建一个新的随机字符串
     *
     * @return 新的随机字符串
     */
    String getNewString();

    /**
     * 获取当前随机字符串的 byte 数组
     *
     * @return 字符串的 byte 数组
     */
    byte[] getNewStringAsBytes();
}
