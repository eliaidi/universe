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

    public abstract boolean valid(Object value, Map<String, Object> source, Constraint constraint);
}
