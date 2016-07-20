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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * jndi 数据源查找者
 *
 * @author maurice
 */
public class JndiDataSourceFinder {

    public static final String CONTAINER_PREFIX = "java:comp/env/";

    private Properties environment;

    /**
     * jndi 数据源查找者
     */
    public JndiDataSourceFinder() {

    }

    /**
     * jndi 数据源查找者
     *
     * @param environment 配置信息
     *
     */
    public JndiDataSourceFinder(Properties environment) {
        this.environment = environment;
    }

    /**
     * 获取数据源
     *
     * @param name jndi 数据源名称
     *
     * @return 数据源
     */
    public DataSource getDataSource(String name) {
        try {
            return lookup(name);
        }catch (NamingException ex) {
            throw new IllegalArgumentException("查找 jndi 名称 [" + name + "] 失败: ", ex);
        }
    }

    /**
     * 查找数据源
     *
     * @param name jndi 数据源名称
     *
     * @return 数据源
     *
     * @throws NamingException
     */
    protected DataSource lookup(String name) throws NamingException {
        Context context = createInitialContext();
        DataSource target = (DataSource) context.lookup(convertJndiName(name));
        if (target == null) {
            throw new NameNotFoundException("找不到名称为[" + name + "] jndi 数据源");
        }
        return target;
    }

    /**
     * 创建 jndi 实现类
     *
     * @return jndi 上下文
     *
     * @throws NamingException
     */
    protected Context createInitialContext() throws NamingException {
        Hashtable<?, ?> icEnv = null;
        if (environment != null) {
            icEnv = new Hashtable<Object, Object>(environment.size());
            mergePropertiesIntoMap(environment, icEnv);
        }
        return new InitialContext(icEnv);
    }

    /**
     * 合并配置信息 到 map 中
     *
     * @param props 配置信息
     * @param map map
     */
    @SuppressWarnings("unchecked")
    private <K, V> void mergePropertiesIntoMap(Properties props, Map<K, V> map) {
        if (map == null) {
            throw new IllegalArgumentException("Map 不能为 null");
        }
        if (props != null) {
            for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
                String key = (String) en.nextElement();
                Object value = props.getProperty(key);
                if (value == null) {
                    value = props.get(key);
                }
                map.put((K) key, (V) value);
            }
        }
    }

    /**
     * 如果 jndi 名称前面没有 "java:comp/env/", 会在 jndiName 参数中加上 "java:comp/env/" 前准
     *
     * @param jndiName 名称
     *
     * @return 转型后的 jndi 名称
     */
    private String convertJndiName(String jndiName) {
        if (!jndiName.startsWith(CONTAINER_PREFIX) && jndiName.indexOf(':') == -1) {
            jndiName = CONTAINER_PREFIX + jndiName;
        }
        return jndiName;
    }
}
