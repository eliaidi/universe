package com.github.dactiv.universe.map.validation.test;

import com.github.dactiv.universe.map.validation.annotation.Valid;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author maurice
 */
@Service
public class TestTest {

    public void saveUser(@Valid("user")Map<String, Object> user) {
        System.out.println(user);
    }
}
