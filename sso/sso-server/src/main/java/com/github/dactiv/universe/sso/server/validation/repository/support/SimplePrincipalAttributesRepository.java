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

package com.github.dactiv.universe.sso.server.validation.repository.support;

import com.github.dactiv.universe.sso.server.validation.repository.PrincipalAttributesRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 简单的当时人（用户）属性库实现
 *
 * @author maurice
 */
public class SimplePrincipalAttributesRepository implements PrincipalAttributesRepository {

    /**
     * 获取当时人（用户）所有属性
     *
     * @param principal 当时人（用户）
     * @return 所有属性
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getAttributes(Object principal) {

        if (Map.class.isAssignableFrom(principal.getClass())) {
            return (Map<String, Object>) principal;
        }

        return convertToMap(principal);
    }

    /**
     * 将当时人（用户）实体转换为 map
     *
     * @param principal 当时人（用户）
     * @return map
     */
    private Map<String, Object> convertToMap(Object principal) {

        Map<String, Object> result = new LinkedHashMap<>();

        for (Field field : getPrincipalField(principal)) {

            String key = field.getName();
            Object value = ReflectionUtils.getField(field, principal);

            result.put(key, value);
        }

        return result;

    }

    /**
     * 获取当时人（用户）的所有字段
     *
     * @param principal 当时人（用户）
     * @return 字段集合
     */
    private List<Field> getPrincipalField(Object principal) {

        List<Field> fields = new LinkedList<>();
        Class<?> superClass = principal.getClass();

        do {
            Field[] result = superClass.getDeclaredFields();

            if (!ArrayUtils.isEmpty(result)) {

                for (Field field : result) {
                    field.setAccessible(true);
                }

                CollectionUtils.addAll(fields, result);
            }

            superClass = superClass.getSuperclass();
        } while (superClass != Object.class);

        return fields;
    }


}
