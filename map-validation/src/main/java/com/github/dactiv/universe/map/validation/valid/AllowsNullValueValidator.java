package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;

/**
 * 允许空值验证器
 *
 * @author maurice
 */
public abstract class AllowsNullValueValidator extends ContainsKeyValidator {

    @Override
    public boolean valid(String key, Map<String, Object> source, Constraint constraint) {

        return !source.containsKey(key) ||
                source.get(key) == null ||
                "".equals(source.get(key)) ||
                valid(source.get(key), source, constraint);
    }

}
