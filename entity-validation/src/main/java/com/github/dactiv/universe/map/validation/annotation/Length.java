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
package com.github.dactiv.universe.map.validation.annotation;

import java.lang.annotation.*;

/**
 * 长度范围验证
 *
 * @author maurice
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

    /**
     * 错误信息
     *
     * @return String
     */
    String message() default "";

    /**
     * 最大长度
     *
     * @return int
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 最小长度
     *
     * @return int
     */
    int min() default 0;
}
