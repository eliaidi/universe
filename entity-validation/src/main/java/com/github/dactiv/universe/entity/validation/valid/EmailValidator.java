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
import java.util.regex.Pattern;

/**
 * 邮箱验证器
 *
 * @author maurice
 */
public class EmailValidator extends AllowsNullValueValidator {

    public static final String NAME = "email";

    private static final String REG = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    private static final String REGULAR_EXPRESSION_ATTR_NAME = "reg";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        String el = constraint.getElement().attributeValue(REGULAR_EXPRESSION_ATTR_NAME);
        if (el != null && !"".equals(el)) {
            return Pattern.compile(el).matcher(value.toString()).matches();
        }
        return Pattern.compile(REG).matcher(value.toString()).matches();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
