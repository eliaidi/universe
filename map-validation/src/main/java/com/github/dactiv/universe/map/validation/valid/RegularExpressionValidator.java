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
package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 *
 * @author maurice
 */
public class RegularExpressionValidator extends AllowsNullValueValidator{

    public static final String NAME = "re";

    private static final String ATTR_NAME = "value";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        String av = constraint.getElement().attributeValue(ATTR_NAME);

        setMessage(constraint,av);

        return Pattern.compile(av).matcher(value.toString()).matches();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
