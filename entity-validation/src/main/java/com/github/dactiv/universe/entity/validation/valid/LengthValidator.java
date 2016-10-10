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

import java.text.MessageFormat;
import java.util.Map;

/**
 * 字符长度验证器
 *
 * @author maurice
 */
public class LengthValidator extends AllowsNullValueValidator {

    public static final String NAME = "length";
    public static final String MAX_ATTR_NAME = "max";
    public static final String MIN_ATTR_NAME = "min";
    public static final String MIN_DEFAULT_VALUE = "0";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {

        String maxValue = constraint.getElement().attributeValue(MAX_ATTR_NAME);
        String minValue = constraint.getElement().attributeValue(MIN_ATTR_NAME, MIN_DEFAULT_VALUE);

        setMessage(constraint,minValue, maxValue);

        try {
            Integer max = Integer.parseInt(maxValue);
            Integer min = Integer.parseInt(minValue);
            Integer length = value.toString().length();

            constraint.setDefaultMessage(MessageFormat.format(constraint.getDefaultMessage(), min, max));

            String message = constraint.getMessage();

            if (message != null && message.indexOf("{") > 0 && message.indexOf("}") > 0) {
                constraint.setMessage(MessageFormat.format(message, min, max));
            }
            return length >= min && length <= max;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
