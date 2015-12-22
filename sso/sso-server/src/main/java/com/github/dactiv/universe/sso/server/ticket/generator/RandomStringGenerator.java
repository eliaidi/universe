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
