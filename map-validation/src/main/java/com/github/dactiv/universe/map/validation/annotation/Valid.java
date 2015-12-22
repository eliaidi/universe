package com.github.dactiv.universe.map.validation.annotation;

import java.lang.annotation.*;

/**
 * Map 对象 校验注解
 *
 * @author maurice
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {

    /**
     * xml 文件映射名称
     *
     * @return 名称
     */
    String value();
}
