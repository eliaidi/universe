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
package com.github.dactiv.universe.jdbc.datasource;

/**
 * 从库数据源 key 对象
 *
 * @author maurice
 */
public class SlaveDataSourceKey {

    // key 名称
    private Object key;
    // 最后使用时间
    private long lastUsedTime;
    // 使用次数
    private long useNumber;

    /**
     * 从库数据源 key 对象
     */
    public SlaveDataSourceKey() {
    }

    /**
     * 从库数据源 key 对象
     *
     * @param key key 名称
     * @param lastUsedTime 最后使用时间
     * @param useNumber useNumber;
     */
    public SlaveDataSourceKey(Object key, long lastUsedTime, long useNumber) {
        this.key = key;
        this.lastUsedTime = lastUsedTime;
        this.useNumber = useNumber;
    }

    /**
     * 获取最后使用时间
     *
     * @return 时间戳
     */
    public long getLastUsedTime() {
        return lastUsedTime;
    }

    /**
     * 获取使用次数
     *
     * @return 次数
     */
    public long getUseNumber() {
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
}
