package com.github.dactiv.universe.sso.server.ticket.generator.support;

import com.github.dactiv.universe.sso.server.ticket.generator.NumericGenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 简单的生成序列实现类
 *
 * @author maurice
 */
public class SimpleNumericGenerator implements NumericGenerator {

    // 最大的字符长度
    private static final int MAX_STRING_LENGTH = Long.toString(Long.MAX_VALUE).length();

    // 最小的字符长度
    private static final int MIN_STRING_LENGTH = 1;

    private final AtomicLong count;

    /**
     * 简单的生成序列实现类
     */
    public SimpleNumericGenerator() {
        this(0);
    }

    /**
     * 简单的生成序列实现类
     *
     * @param initValue 初始化序列值
     */
    public SimpleNumericGenerator(long initValue) {
        this.count = new AtomicLong(initValue);
    }

    /**
     * 获取下一个序列的 string 值
     *
     * @return 下一个序列的 string 值
     */
    @Override
    public String getNextNumberAsString() {
        return Long.toString(this.getNextValue());
    }

    /**
     * 序列最大长度值
     *
     * @return 最大长度值
     */
    @Override
    public int getMaximumLength() {
        return MAX_STRING_LENGTH;
    }

    /**
     * 序列最小长度值
     *
     * @return 最小值
     */
    @Override
    public int getMinimumLength() {
        return MIN_STRING_LENGTH;
    }

    /**
     * 获取下一个值
     *
     * @return 下一个值
     */
    protected long getNextValue() {
        if (this.count.compareAndSet(Long.MAX_VALUE, 0)) {
            return Long.MAX_VALUE;
        }
        return this.count.getAndIncrement();
    }
}
