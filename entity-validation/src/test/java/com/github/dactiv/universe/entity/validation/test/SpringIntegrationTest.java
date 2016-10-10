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

package com.github.dactiv.universe.entity.validation.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maurice
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SpringIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Test(expected = UndeclaredThrowableException.class)
    public void testValidateMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        userDao.saveUser(map);
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void testValidateEntity() {
        User user = new User();
        userDao.saveUser(user);
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void testValidateNotAnnEntity() {
        User user = new User();
        userDao.saveNotAnnUser(user);
    }

}
