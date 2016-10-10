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

package com.github.dactiv.universe.entity.validation.valid;

import com.github.dactiv.universe.entity.validation.Constraint;

import java.util.Map;

/**
 * 相等验证器
 *
 * @author maurice
 */
public class EqualValidator extends AllowsNullValueValidator {

    public static final String NAME = "equal";

    private static final String EQUAL_TO_ATTR = "value";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {

        String to = constraint.getElement().attributeValue(EQUAL_TO_ATTR);

        setMessage(constraint,to);

        if (to == null || "".equals(to) || !source.containsKey(to)) {
            return Boolean.FALSE;
        }

        Object toValue = source.get(to);

        return value.equals(toValue);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
