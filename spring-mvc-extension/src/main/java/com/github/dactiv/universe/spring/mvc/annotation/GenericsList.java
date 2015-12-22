package com.github.dactiv.universe.spring.mvc.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 泛型 List 注解
 * @author maurice
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericsList {

    /**
     * 值
     *
     * @return 值
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 名称
     *
     * @return 名称
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 是否必须项
     *
     * @return true 为是，否则 false
     */
    boolean required() default true;

}
