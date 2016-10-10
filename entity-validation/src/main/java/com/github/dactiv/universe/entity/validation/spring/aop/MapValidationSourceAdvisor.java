/*
 * Copyright 2015 dactiv
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

package com.github.dactiv.universe.entity.validation.spring.aop;

import com.github.dactiv.universe.entity.validation.EntityValidation;
import com.github.dactiv.universe.entity.validation.ValidError;
import com.github.dactiv.universe.entity.validation.annotation.Valid;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用 AOP 针对方法带有 Valid 注解参数的方法进行Map验证的 Advisor
 *
 * @author maurice
 */
public class MapValidationSourceAdvisor extends StaticMethodMatcherPointcutAdvisor implements MethodBeforeAdvice {

    // Map 验证器
    private EntityValidation entityValidation;

    /**
     * 使用 AOP 针对方法带有 Valid 注解参数的方法进行Map验证的 Advisor
     */
    public MapValidationSourceAdvisor() {
        setAdvice(this);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Annotation[][] annotations = method.getParameterAnnotations();

        Class<?>[] classes = method.getParameterTypes();

        if (classes.length == 0) {
            return Boolean.FALSE;
        }

        for (Class<?> c : classes) {
            Valid valid = entityValidation.getAnnotation(c, Valid.class);
            if (valid != null) {
                return Boolean.TRUE;
            }
        }

        if (annotations.length == 0) {
            return Boolean.FALSE;
        }

        for (Annotation[] annotation : annotations) {
            for (Annotation a : annotation) {
                if (Valid.class.isInstance(a)) {
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void before(Method method, Object[] args, Object target) throws Throwable {

        Annotation[][] annotations = method.getParameterAnnotations();

        // 循环参数逐个去判断是否存在 Valid 的注解
        for (int i = 0; i < args.length; i++) {

            Valid valid = getMapValidAnnotation(annotations, i);

            Object o = args[i];

            if (o == null) {
                return ;
            }
            List<ValidError> errorList;
            // 如果参数里存在 Valid 注解就进行校验
            if (valid != null) {
                errorList = entityValidation.valid(o, valid.value());
            } else {
                errorList = entityValidation.valid(o);
            }

            if (errorList.size() > 0) {

                String objectName = valid == null || "".equals(valid.value()) ? o.getClass().getName() :  valid.value();

                BeanPropertyBindingResult propertyBindingResult = new BeanPropertyBindingResult(o, objectName);

                for (ValidError ve : errorList) {
                    propertyBindingResult.addError(new ObjectError(ve.getName(), ve.getMessage()));
                }

                throw new BindException(propertyBindingResult);
            }

        }
    }

    /**
     * 获取 Valid 注解
     *
     * @param annotations 注解列表
     * @param index       索引位
     *
     * @return Valid 注解
     */
    private Valid getMapValidAnnotation(Annotation[][] annotations, int index) {

        for (Annotation annotation : annotations[index]) {
            if (Valid.class.isInstance(annotation)) {
                return (Valid) annotation;
            }
        }

        return null;
    }

    /**
     * 设置实体验证器
     *
     * @param entityValidation 实体验证器
     */
    public void setEntityValidation(EntityValidation entityValidation) {
        this.entityValidation = entityValidation;
    }
}
