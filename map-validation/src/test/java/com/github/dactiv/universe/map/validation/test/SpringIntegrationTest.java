package com.github.dactiv.universe.map.validation.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.UndeclaredThrowableException;
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
        testTest.saveUser(map);
    }

}
