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

package com.github.dactiv.universe.map.validation.mapping;

import com.github.dactiv.universe.map.validation.Constraint;
import com.github.dactiv.universe.map.validation.MappingKey;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的key 映射信息接口实现
 *
 * @author maurice
 */
public class SimpleMappingKey implements MappingKey {

    private String name;
    private String alias;

    private List<Constraint> constraints = new ArrayList<Constraint>();

    public SimpleMappingKey(String name, String alias, List<Constraint> constraints) {
        this.name = name;
        this.alias = alias;
        this.constraints = constraints;
    }

    @Override
    public List<Constraint> getConstraints() {
        return constraints;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getKeyName() {
        String result = getAlias();
        if (result == null || result.equals("")) {
            result = getName();
        }
        return result;
    }
}
