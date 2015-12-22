package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 邮箱验证器
 *
 * @author maurice
 */
public class EmailValidator extends AllowsNullValueValidator {

    public static final String NAME = "email";

    private static final String REG = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {

        return Pattern.compile(REG).matcher(value.toString()).matches();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
