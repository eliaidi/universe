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
