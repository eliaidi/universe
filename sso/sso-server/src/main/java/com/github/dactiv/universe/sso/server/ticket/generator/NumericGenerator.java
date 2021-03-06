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
