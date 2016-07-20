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
package com.github.dactiv.universe.jdbc.test;

import com.github.dactiv.universe.jdbc.datasource.UniverseDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 读写分离数据源单元测试
 *
 * @author maurice
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UniverseDataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TransactionOperator transactionOperator;

    @Test
    public void testGetConnection() throws SQLException {
        UniverseDataSource.setRead();
        for (int i = 0,t = 1; i < 30; i++) {
            Connection connection = dataSource.getConnection();
            Assert.assertTrue(connection.toString().contains("universe_s_0" + (t++)));
            if (t > 3) {
                t = 1;
            }
            connection.close();
        }

        UniverseDataSource.setWrite();
        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            Assert.assertTrue(connection.toString().contains("universe_master"));
            connection.close();
        }

    }

    @Test
    public void testSpringTransactionGetConnection() throws SQLException {
        for (int i = 0,t = 1; i < 30; i++) {
            String name = transactionOperator.get();
            Assert.assertTrue(name.contains("universe_s_0" + (t++)));
            if (t > 3) {
                t = 1;
            }
        }

        for (int i = 0; i < 10; i++) {
            String name = transactionOperator.save();
            Assert.assertTrue(name.contains("universe_master"));
        }
    }

}
