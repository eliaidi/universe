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
    private TestTest testTest;

    @Test(expected = UndeclaredThrowableException.class)
    public void testValidate() {
        Map<String, Object> map = new HashMap<String, Object>();
        //testTest.saveUser(map);
        testTest.saveUser((User)null);
        User user = new User();
        //testTest.saveUser(user);

        user.setUsername("chenxiaobo");
        user.setPassword("123456");
        user.setNickname("chenxiaobo");
        user.setState(1);
        user.setBrithday(new Date());

        testTest.saveUser(user);
    }

}
