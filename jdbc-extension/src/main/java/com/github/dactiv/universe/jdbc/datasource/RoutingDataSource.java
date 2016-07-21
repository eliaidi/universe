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

import com.github.dactiv.universe.jdbc.datasource.policy.DataSourceObtainPolicy;
import com.github.dactiv.universe.jdbc.datasource.policy.support.SequenceDataSourceObtainPolicy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author maurice
 */
public class RoutingDataSource extends AbstractDataSource {

    private final static ThreadLocal<StatementType> READ_WRITE_TYPE_THREAD_LOCAL = new ThreadLocal<StatementType>();

    // 标目数据源映射(从库)
    private Map<Object, Object> targetDataSources;
    // 默认目标数据源映射(主库)
    private Object defaultTargetDataSource;
    // true 表示如果在从库找不到数据库，就使用主库的，否则 false
    private boolean lenientFallback = true;
    // jndi 数据源查找者
    private JndiDataSourceFinder jndiDataSourceFinder = new JndiDataSourceFinder();
    // 主库数据源
    private DataSource master;
    // 从库数据源获取政策
    private DataSourceObtainPolicy dataSourceObtainPolicy;

    /**
     * 设置标目数据源映射(从库)
     * 
     * @param targetDataSources 数据源映射(从库)
     */
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    /**
     * 设置默认目标数据源映射(主库)
     * 
     * @param defaultTargetDataSource 默认目标数据源映射(主库)
     */
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    /**
     * 设置是否在从库找不到时，使用主库的数据源
     * 
     * @param lenientFallback true 表示是，否则 false
     */
    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    /**
     * 设置 jndi 数据源操作者
     *
     * @param jndiDataSourceFinder jndi 数据源操作者
     */
    public void setJndiDataSourceFinder(JndiDataSourceFinder jndiDataSourceFinder) {
        this.jndiDataSourceFinder = jndiDataSourceFinder;
    }

    /**
     * 设置从库数据源获取政策
     * 
     * @param dataSourceObtainPolicy 从库数据源获取政策
     */
    public void setDataSourceObtainPolicy(DataSourceObtainPolicy dataSourceObtainPolicy) {
        this.dataSourceObtainPolicy = dataSourceObtainPolicy;
    }

    /**
     * 重写 get connection 方法，在获取 connection 时，先从{@link #getTargetDataSource()}处理一遍在 get connection
     * 
     * @return connection
     * 
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getTargetDataSource().getConnection();
    }

    /**
     * 重写 get connection 方法，在获取 connection 时，先从{@link #getTargetDataSource()}处理一遍在 get connection
     * 
     * @param username 用户名
     * @param password 密码
     * 
     * @return connection
     * 
     * @throws SQLException
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getTargetDataSource().getConnection(username, password);
    }

    /**
     *  重写 unwrap 方法，在 unwrap 时，先从{@link #getTargetDataSource()}处理一遍在 unwrap
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return getTargetDataSource().unwrap(iface);
    }

    /**
     *  重写 is wrapper for 方法，在 is wrapper for 时，先从{@link #getTargetDataSource()}处理一遍在 is wrapper for
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || getTargetDataSource().isWrapperFor(iface));
    }

    /**
     * 获取目标数据源
     *
     * @return 数据源
     *
     * @throws SQLException
     */
    protected DataSource getTargetDataSource() throws SQLException {
        DataSource target;

        if (isRead()) {
            target = dataSourceObtainPolicy.obtainDataSource();
        } else {
            target = master;
        }

        if (target == null && lenientFallback) {
            target = master;
        }

        if (target == null){
            throw new SQLException("找不到数据源");
        }

        return target;
    }

    public void init() throws Exception {

        if (this.targetDataSources == null) {
            throw new IllegalArgumentException("targetDataSources 不能为 null");
        }

        Map<Object, DataSource> slave = new HashMap<Object, DataSource>(this.targetDataSources.size());
        for (Map.Entry<Object, Object> entry : this.targetDataSources.entrySet()) {
            Object lookupKey = resolveSpecifiedLookupKey(entry.getKey());
            DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
            slave.put(lookupKey, dataSource);
        }
        
        if(dataSourceObtainPolicy == null) {
            dataSourceObtainPolicy = new SequenceDataSourceObtainPolicy();
        }
        
        dataSourceObtainPolicy.setTargetDataSources(slave);
        
        if (this.defaultTargetDataSource != null) {
            this.master = resolveSpecifiedDataSource(this.defaultTargetDataSource);
        }
    }

    /**
     * 获取指定的查找 key 名称
     *
     * @param lookupKey key 名称
     *
     * @return 处理后的 key 名称
     */
    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }

    /**
     * 获取指定的数据源
     *
     * @param dataSource 数据源值，String 或 javax.sql.DataSource 类型
     *
     * @return 数据源
     *
     * @throws IllegalArgumentException
     */
    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if (dataSource instanceof DataSource) {
            return (DataSource) dataSource;
        } else if (dataSource instanceof String) {
            return this.jndiDataSourceFinder.getDataSource((String) dataSource);
        } else {
            throw new IllegalArgumentException("only support [javax.sql.DataSource] and [java.lang.String] class type，the dataSource class type is: " + dataSource);
        }
    }

    /**
     * 设置本次操作为只读操作
     */
    public static void setRead() {
        READ_WRITE_TYPE_THREAD_LOCAL.set(StatementType.READ);
    }

    /**
     * 设置本次操作为写入操作
     */
    public static void setWrite() {
        READ_WRITE_TYPE_THREAD_LOCAL.set(StatementType.WRITE);
    }

    /**
     * 获取当前操作类型
     *
     * @return 操作类型
     */
    public static StatementType currentType() {
        return READ_WRITE_TYPE_THREAD_LOCAL.get();
    }

    /**
     * 获取当前是否只读状态
     *
     * @return true 表示是，否则 false
     */
    public static boolean isRead() {
        return currentType() == null || currentType().equals(StatementType.READ);
    }

    /**
     * 获取当前是hi否写入操作
     *
     * @return true 表示是，否则 false
     */
    public static boolean isWrite() {
        return currentType() != null && currentType().equals(StatementType.WRITE);
    }
}
