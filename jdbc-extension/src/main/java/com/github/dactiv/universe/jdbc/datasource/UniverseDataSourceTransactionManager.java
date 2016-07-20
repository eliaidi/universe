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

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 动态数据源的 spring 事务管理实现
 *
 * @author maurice
 */
public class UniverseDataSourceTransactionManager extends DataSourceTransactionManager {

    /**
     * 从写父类方法，在开启事务前，把当前读写状态设置
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        if (!definition.isReadOnly()) {
            UniverseDataSource.setWrite();
        }
        super.doBegin(transaction, definition);
    }
}
