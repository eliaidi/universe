package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;

/**
 * 不能为null或""验证器
 *
 * @author maurice
 */
public class NotEmptyValidator extends ContainsKeyValidator{

    public static final String NAME = "notEmpty";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        return value != null && !"".equals(value);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
