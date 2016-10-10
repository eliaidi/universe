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

package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;

/**
 * 最小值验证器
 *
 * @author maurice
 */
public class MinValidator extends AllowsNullValueValidator {

    public static final String NAME = "min";

    private static final String ATTR_NAME = "value";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {

        String av = constraint.getElement().attributeValue(ATTR_NAME);
        setMessage(constraint, av);

        try {
            Double v = new Double(value.toString());
            Double e = new Double(av);

            return v >= e;
        } catch (Exception e) {

            return Boolean.FALSE;
        }

    }

    @Override
    public String getName() {
        return NAME;
    }
}
