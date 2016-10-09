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

package com.github.dactiv.universe.map.validation.test;

import com.github.dactiv.universe.map.validation.EntityValidation;
import com.github.dactiv.universe.map.validation.ValidError;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maurice
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class EntityValidationTest {

    @Test
    public void testValidate() throws Exception {
        EntityValidation entityValidation = new EntityValidation();
        entityValidation.addMapper("/user-map-validate.xml");
        
        User user = new User();
        List<ValidError> validErrorList = entityValidation.valid(user);
        Assert.assertEquals(validErrorList.size(), 5);
        
        user.setUsername("chenxiaobo");
        user.setPassword("123456");
        user.setNickname("chenxiaobo");
        user.setState(1);
        user.setBrithday(new Date("2015-02-05"));

        entityValidation.valid(user);

        Map<String, Object> map = new HashMap<String, Object>();
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 5);

        map.put("username","chenxiaobo");
        map.put("password","123456");
        map.put("nickname","chenxiaobo");
        map.put("state","1");
        map.put("email", "");
        map.put("brithday", "2015-02-05");

        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("username",null);
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("username", "cxb");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","1234567891011121314151617181920");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","123456");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","chenxiaobo");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("state", null);
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("state", "abc");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 4);

        map.put("state","-1");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","3");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","1");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email", null);
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email","123asd@");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc.com");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("brithday","20151102");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015年11月2日");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015.11.2");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015-11-02");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("brithday","2015-02-05");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);
    }

    @Test
    public void testSpringIntegration() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        EntityValidation entityValidation = applicationContext.getBean(EntityValidation.class);

        Map<String, Object> map = new HashMap<String, Object>();
        List<ValidError> validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 5);

        map.put("username","chenxiaobo");
        map.put("password","123456");
        map.put("nickname","chenxiaobo");
        map.put("state","1");
        map.put("email", "");
        map.put("brithday", "2015-02-05");

        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("username",null);
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("username", "cxb");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","1234567891011121314151617181920");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","123456");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","chenxiaobo");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("state", null);
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("state", "abc");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 4);

        map.put("state","-1");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","3");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","1");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email", null);
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email","123asd@");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc.com");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("brithday","20151102");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015年11月2日");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015.11.2");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015-11-02");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("brithday","2015-02-05");
        validErrorList = entityValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);
    }
}
