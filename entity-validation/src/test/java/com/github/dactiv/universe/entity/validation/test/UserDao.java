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
package com.github.dactiv.universe.entity.validation.test;

import com.github.dactiv.universe.entity.validation.annotation.Valid;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author maurice
 */
@Service
public class UserDao {
    public void saveUser(@Valid("user") Map<String, Object> user) {
        System.out.println(user);
    }

    public void saveUser(@Valid("user") User user) {
        System.out.println(user);
    }

    public void saveNotAnnUser(User user) {
        System.out.println(user);
    }
}
