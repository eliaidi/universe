package com.github.dactiv.universe.map.validation.mapping;

import com.github.dactiv.universe.map.validation.Constraint;
import org.dom4j.Element;

/**
 * 简单的验证约束接口
 *
 * @author maurice
 */
public class SimpleConstraint implements Constraint {

    private String name;
    private String message;
    private Element element;
    private String defaultMessage;

    public SimpleConstraint(String name, String message,String defaultMessage,  Element element) {
        this.name = name;
        this.message = message;
        this.element = element;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getErrorMessage() {
        return message == null ? this.defaultMessage : message;
    }

    @Override
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setDefaultMessage(String message) {
        this.defaultMessage = message;
    }
}
