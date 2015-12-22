package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 数字验证器
 *
 * @author maurice
 */
public class NumberValidator extends AllowsNullValueValidator {

    public static final String NAME = "number";

    private static final String REGULAR_EXPRESSION = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";

    private static final String REGULAR_EXPRESSION_ATTR_NAME = "el";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        String el = constraint.getElement().attributeValue(REGULAR_EXPRESSION_ATTR_NAME);
        if (el != null && !"".equals(el)) {
            return Pattern.compile(el).matcher(value.toString()).matches();
        }
        return Pattern.compile(REGULAR_EXPRESSION).matcher(value.toString()).matches();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
