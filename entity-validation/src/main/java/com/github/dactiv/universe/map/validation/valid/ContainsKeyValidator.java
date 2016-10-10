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
import com.github.dactiv.universe.map.validation.Validator;

import java.text.MessageFormat;
import java.util.Map;

/**
 * 判断 key 是否存在验证器
 *
 * @author maurice
 */
public abstract class ContainsKeyValidator implements Validator {

    /**
     * 设置错误信息中的参数值到验证约束中
     *
     * @param constraint 验证约束接口
     * @param messages 参数值
     */
    protected void setMessage(Constraint constraint, Object... messages) {

        constraint.setDefaultMessage(MessageFormat.format(constraint.getDefaultMessage(), messages));

        String message = constraint.getMessage();

        if (message != null && message.indexOf("{") > 0 && message.indexOf("}") > 0) {
            constraint.setMessage(MessageFormat.format(message, messages));
        }

    }

    @Override
    public boolean valid(String key, Map<String, Object> source, Constraint constraint) {
        if (!source.containsKey(key)) {
            return false;
        }

        return valid(source.get(key), source, constraint);
    }

    /**
     * 验证方法
     *
     * @param value 值
     * @param source 数据源
     * @param constraint 条件对象
     *
     * @return 成功返回 true, 否则 false
     */
    public abstract boolean valid(Object value, Map<String, Object> source, Constraint constraint);
}
