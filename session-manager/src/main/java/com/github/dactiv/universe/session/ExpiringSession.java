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
package com.github.dactiv.universe.session;

/**
 * 会超时的 session 接口
 *
 * @author maurice
 */
public interface ExpiringSession extends Session {

    /**
     * 获取创建时间
     *
     * @return 创建时间戳
     */
    long getCreationTime();

    /**
     * 设置最后访问时间
     *
     * @param lastAccessedTime 最后访问创建时间戳
     */
    void setLastAccessedTime(long lastAccessedTime);

    /**
     * 获取最后访问时间
     *
     * @return 最后访问创建时间戳
     */
    long getLastAccessedTime();

    /**
     * 设置被请求之间最大的不活跃的时间间隔(以秒为单位)
     *
     * @param interval 如果为负数，就永远不超时
     */
    void setMaxInactiveIntervalInSeconds(int interval);


    /**
     * 获取被请求之间最大的不活跃的时间间隔(以秒为单位)，如果为负数，就永远不超时
     *
     * @return 时间
     */
    int getMaxInactiveIntervalInSeconds();

    /**
     * 判断是否超时
     *
     * @return true 表示是，否则 false
     */
    boolean isExpired();
}
