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
