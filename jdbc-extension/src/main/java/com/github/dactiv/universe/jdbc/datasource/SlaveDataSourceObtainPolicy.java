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

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 从库数据获取政策
 *
 * @author maurice
 */
public abstract class SlaveDataSourceObtainPolicy {

    // 从库数据源映射
    private Map<Object, DataSource> targetDataSources;
    // 从库数据源 key 集合
    private List<SlaveDataSourceKey> keys = new ArrayList<>();

    /**
     * 从库数据获取政策
     */
    public SlaveDataSourceObtainPolicy() {
    }

    /**
     * 从库数据获取政策
     *
     * @param targetDataSources 从库数据源映射
     */
    public SlaveDataSourceObtainPolicy(Map<Object, DataSource> targetDataSources) {
        setTargetDataSources(targetDataSources);
    }

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    public DataSource obtainDataSource() {
        SlaveDataSourceKey key = getSlaveKey(keys);
        key.setLastUsedTime(System.currentTimeMillis());
        key.setUseNumber(key.getUseNumber() + 1);
        return targetDataSources.get(key.getKey());
    }

    /**
     * 获取从库数据源 key 对象
     *
     * @param keys key 对象集合
     *
     * @return 从库数据源 key 对象
     */
    protected abstract SlaveDataSourceKey getSlaveKey(List<SlaveDataSourceKey> keys);

    /**
     * 设置从库数据源映射
     *
     * @param targetDataSources 从库数据源映射
     */
    public void setTargetDataSources(Map<Object, DataSource> targetDataSources) {
        this.targetDataSources = targetDataSources;
        for (Object o : targetDataSources.entrySet()) {
            keys.add(new SlaveDataSourceKey(o, System.currentTimeMillis(), 0));
        }
    }

}
