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
package com.github.dactiv.universe.entity.validation.valid;

import com.github.dactiv.universe.entity.validation.Constraint;
import com.github.dactiv.universe.entity.validation.exception.ValidatorNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 允许 null 或 "" 值的自定义验证
 *
 * @author maurice
 */
public class EmptyValueCustomValidator extends AllowEmptyValueValidator {

    public static final String NAME = "emptyValueCustom";

    private static final String KEY_ATTR_NAME = "value";

    private Map<String, ContainsKeyValidator> customValidatorMap = new HashMap<String, ContainsKeyValidator>();

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {

        String key = constraint.getElement().attributeValue(KEY_ATTR_NAME);

        if (!customValidatorMap.containsKey(key)) {
            throw new ValidatorNotFoundException("not found [" + key + "] custom validator");
        }

        return customValidatorMap.get(key).valid(value, source, constraint);
    }

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * 设置自定义验证器
     *
     * @param customValidatorMap 自定义验证器集合
     */
    public void setCustomValidatorMap(Map<String, ContainsKeyValidator> customValidatorMap) {
        this.customValidatorMap = customValidatorMap;
    }

    /**
     * 获取自定义验证器
     *
     * @return 自定义验证器
     */
    public Map<String, ContainsKeyValidator> getCustomValidatorMap() {
        return customValidatorMap;
    }
}
