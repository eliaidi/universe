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

package com.github.dactiv.universe.map.validation.spring.aop;

import com.github.dactiv.universe.map.validation.ValidError;
import com.github.dactiv.universe.map.validation.annotation.Valid;
import com.github.dactiv.universe.map.validation.MapValidation;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.validation.BindException;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 使用 AOP 针对方法带有 Valid 注解参数的方法进行Map验证的 Advisor
 *
 * @author maurice
 */
public class MapValidationSourceAdvisor extends StaticMethodMatcherPointcutAdvisor implements MethodBeforeAdvice {

    // Map 验证器
    private MapValidation mapValidation;

    /**
     * 使用 AOP 针对方法带有 Valid 注解参数的方法进行Map验证的 Advisor
     */
    public MapValidationSourceAdvisor() {
        setAdvice(this);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Annotation[][] annotations = method.getParameterAnnotations();

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

            // 如果参数里存在 Valid 注解并且该参数是 Map 的子类，就进行校验
            if (valid != null && o != null && Map.class.isAssignableFrom(o.getClass())) {
                valid((Map<String, Object>) o, valid.value());
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
     * 校验 map
     *
     * @param map        map 对象
     * @param mapperName 校验文件的映射名称
     *
     * @throws org.springframework.validation.BindException
     */
    private void valid(Map<String, Object> map, String mapperName) throws BindException {
        List<ValidError> errorList = mapValidation.valid(map, mapperName);

        if (errorList.isEmpty()) {
            return;
        }

        MapBindingResult mapBindingResult = new MapBindingResult(map, mapperName);

        for (ValidError ve : errorList) {
            mapBindingResult.addError(new ObjectError(ve.getName(), ve.getMessage()));
        }

        throw new BindException(mapBindingResult);
    }

    /**
     * 设置 map validationg
     *
     * @param mapValidation map validationg
     */
    public void setMapValidation(MapValidation mapValidation) {
        this.mapValidation = mapValidation;
    }

}
