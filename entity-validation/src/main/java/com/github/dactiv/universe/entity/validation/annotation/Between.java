/*
 * Copyright 2016 dactiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.dactiv.universe.entity.validation.annotation;

import java.lang.annotation.*;

/**
 * 数值范围值验证
 *
 * @author maurice
 */
@ValidField
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Between {

    /**
     * 错误信息
     *
     * @return String
     */
    String message() default "";

    /**
     * 最大值
     *
     * @return String
     */
    String max();

    /**
     * 最小值
     *
     * @return String
     */
    String min();

    /**
     * 自定义验证正则表达式校验值的类型
     *
     * @return String
     */
    String reg() default "";

    /**
     * 时间格式
     *
     * @return String
     */
    String format() default "";
}
