/*
 * Copyright 2016 dactiv
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
package com.github.dactiv.universe.jdbc.datasource.policy;

/**
 * 从库数据源 key 对象
 *
 * @author maurice
 */
public class DataSourceKey {

    // key 名称
    private Object key;
    // 最后使用时间
    private Long lastUsedTime;
    // 使用次数
    private Long useNumber;

    /**
     * 从库数据源 key 对象
     */
    public DataSourceKey() {
    }

    /**
     * 从库数据源 key 对象
     *
     * @param key key 名称
     * @param lastUsedTime 最后使用时间
     * @param useNumber useNumber;
     */
    public DataSourceKey(Object key, Long lastUsedTime, Long useNumber) {
        this.key = key;
        this.lastUsedTime = lastUsedTime;
        this.useNumber = useNumber;
    }

    /**
     * 获取最后使用时间
     *
     * @return 时间戳
     */
    public Long getLastUsedTime() {
        return lastUsedTime;
    }

    /**
     * 获取使用次数
     *
     * @return 次数
     */
    public Long getUseNumber() {
        return useNumber;
    }

    /**
     * 获取 key 名称
     *
     * @return key 名称
     */
    public Object getKey() {
        return key;
    }

    public void setLastUsedTime(Long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public void setUseNumber(Long useNumber) {
        this.useNumber = useNumber;
    }
}
