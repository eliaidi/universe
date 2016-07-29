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


import com.github.dactiv.universe.jdbc.WrapperAdapter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 路由数据源
 *
 * @author maurice
 */
public abstract class AbstractDataSource extends WrapperAdapter implements DataSource {

    private PrintWriter logWriter = new PrintWriter(System.out);

    /**
     * 返回0, 使用系统默认的超时时间
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    /**
     * 不支持登录超时时间
     */
    @Override
    public void setLoginTimeout(int timeout) throws SQLException {
        throw new SQLFeatureNotSupportedException("setLoginTimeout");
    }

    /**
     * 获取 LogWriter
     */
    @Override
    public PrintWriter getLogWriter() {
        return logWriter;
    }

    /**
     * 不支持 LogWriter
     */
    @Override
    public void setLogWriter(PrintWriter pw) throws SQLException {
        this.logWriter = pw;
    }

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
}
