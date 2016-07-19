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
package com.github.dactiv.universe.jdbc.datasource.support;

import com.github.dactiv.universe.jdbc.datasource.SlaveDataSourceKey;
import com.github.dactiv.universe.jdbc.datasource.SlaveDataSourceObtainPolicy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 按顺序获取的从库数据源获取政策
 *
 * @author maurice
 */
public class SequenceSlaveDataSourceObtainPolicy extends SlaveDataSourceObtainPolicy {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 获取从库数据源 key 对象
     *
     * @param keys key 对象集合
     *
     * @return 从库数据源 key 对象
     */
    @Override
    protected SlaveDataSourceKey getSlaveKey(List<SlaveDataSourceKey> keys) {
        Integer value = atomicInteger.decrementAndGet();

        if (value > keys.size()) {
            atomicInteger.compareAndSet(value, 0);
            value = atomicInteger.decrementAndGet();
        }

        return keys.get(value);
    }

}
