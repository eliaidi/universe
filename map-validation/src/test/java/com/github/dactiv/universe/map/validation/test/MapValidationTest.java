package com.github.dactiv.universe.map.validation.test;

import com.github.dactiv.universe.map.validation.ValidError;
import com.github.dactiv.universe.map.validation.MapValidation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maurice
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class MapValidationTest {

    @Test
    public void testValidate() throws Exception {

        MapValidation mapValidation = new MapValidation();
        mapValidation.addMapper("/user-map-validate.xml");

        Map<String, Object> map = new HashMap<String, Object>();
        List<ValidError> validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 5);

        map.put("username","chenxiaobo");
        map.put("password","123456");
        map.put("nickname","chenxiaobo");
        map.put("state","1");
        map.put("email", "");
        map.put("brithday", "2015-02-05");

        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("username",null);
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("username", "cxb");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","1234567891011121314151617181920");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","123456");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","chenxiaobo");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("state", null);
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("state", "abc");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 4);

        map.put("state","-1");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","3");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","1");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email", null);
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email","123asd@");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc.com");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("brithday","20151102");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015年11月2日");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015.11.2");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015-11-02");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("brithday","2015-02-05");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);
    }

    @Test
    public void testSpringIntegration() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        MapValidation mapValidation = applicationContext.getBean(MapValidation.class);

        Map<String, Object> map = new HashMap<String, Object>();
        List<ValidError> validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 5);

        map.put("username","chenxiaobo");
        map.put("password","123456");
        map.put("nickname","chenxiaobo");
        map.put("state","1");
        map.put("email", "");
        map.put("brithday", "2015-02-05");

        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("username",null);
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("username", "cxb");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","1234567891011121314151617181920");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","123456");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("username","chenxiaobo");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("state", null);
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("state", "abc");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 4);

        map.put("state","-1");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","3");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("state","1");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email", null);
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("email","123asd@");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("email","123asd@gxc.com");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);

        map.put("brithday","20151102");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015年11月2日");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015.11.2");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 2);

        map.put("brithday","2015-11-02");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 1);

        map.put("brithday","2015-02-05");
        validErrorList = mapValidation.valid(map,"user");
        Assert.assertEquals(validErrorList.size(), 0);
    }
}
