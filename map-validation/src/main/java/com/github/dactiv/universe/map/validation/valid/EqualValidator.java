package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;

/**
 * 相等验证器
 *
 * @author maurice
 */
public class EqualValidator extends AllowsNullValueValidator {

    public static final String NAME = "eq";

    private static final String EQUAL_TO_ATTR = "to";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {

        String to = constraint.getElement().attributeValue(EQUAL_TO_ATTR);

        setMessage(constraint,to);

        if (to == null || "".equals(to) || !source.containsKey(to)) {
            return Boolean.FALSE;
        }

        Object toValue = source.get(to);

        return value.equals(toValue);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
