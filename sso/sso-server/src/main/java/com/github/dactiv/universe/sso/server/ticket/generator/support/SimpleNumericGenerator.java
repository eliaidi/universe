/*
 * Copyright 2015 dactiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
