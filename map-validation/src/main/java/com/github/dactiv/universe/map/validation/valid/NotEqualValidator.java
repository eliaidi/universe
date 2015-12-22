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
 * 不相等验证器
 *
 * @author maurice
 */
public class NotEqualValidator extends EqualValidator {

    public static final String NAME = "neq";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        return !super.valid(value, source, constraint);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
