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
package com.github.dactiv.universe.entity.validation;

import com.github.dactiv.universe.entity.validation.mapping.MapConstraintElement;
import com.github.dactiv.universe.entity.validation.annotation.Alias;
import com.github.dactiv.universe.entity.validation.annotation.Valid;
import com.github.dactiv.universe.entity.validation.annotation.ValidField;
import com.github.dactiv.universe.entity.validation.mapping.SimpleConstraint;
import com.github.dactiv.universe.entity.validation.mapping.SimpleMappingKey;
import com.github.dactiv.universe.entity.validation.mapping.SimpleMappingMetadata;
import com.github.dactiv.universe.entity.validation.valid.error.SimpleValidError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类验证器
 * 
 * @author maurice
 */
public class EntityValidation extends MapValidation {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MapValidation.class);

    // 是否忽略转换实体成 map 时出现的异常
    private boolean ignoreEntityConvertException = true;
    // 是否忽略 null 对象
    private boolean ignoreNullObject = true;

    /**
     * 验证对象
     *
     * @param entity 对象实体
     * @param mapperName xml 映射文件 mapper 节点 name 名称
     *
     * @return 如果验证成功，返回值的 size 为 0，否则 size 大于 0
     */
    public List<ValidError> valid(Object entity, String mapperName) {
        List<ValidError> validErrors = new ArrayList<ValidError>();

        if (entity == null && ignoreNullObject){
            return validErrors;
        }

        if (Map.class.isAssignableFrom(entity.getClass())) {
            validErrors = valid((Map<String, Object>)entity, mapperName);
        } else if (mapperName == null || "".equals(mapperName)) {
            validErrors = valid(entity);
        } else {
            try {
                Map<String, Object> map = convertObjectToMap(entity);
                validErrors = valid(map, mapperName);
            } catch (Exception e) {
                if(!ignoreEntityConvertException) {
                    validErrors.add(new SimpleValidError("convert-exception", e.getMessage()));
                }
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("convert entity to map exception:", e);
                }
            }
        }

        return validErrors;
    }

    /**
     * 验证对象
     *
     * @param entity 对象实体
     *
     * @return 如果验证成功，返回值的 size 为 0，否则 size 大于 0
     */
    public List<ValidError> valid(Object entity){
        List<ValidError> validErrors = new ArrayList<ValidError>();

        if (entity == null && ignoreNullObject){
            return validErrors;
        }

        Valid valid = getAnnotation(entity.getClass(), Valid.class);

        if (valid == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("not valid entity");
            }
            return validErrors;
        }

        if (!"".equals(valid.value())) {
            validErrors = valid(entity, valid.value());
        } else{
            MappingMetadata mappingMetadata = this.getMappingMetadataMap().get(entity.getClass().getName());

            if (mappingMetadata == null) {
                addClass(entity.getClass());
                mappingMetadata = this.getMappingMetadataMap().get(entity.getClass().getName());
            }

            try {
                Map<String, Object> map = convertObjectToMap(entity);
                validErrors = doValid(mappingMetadata, map);
            } catch (Exception e) {
                if (!ignoreEntityConvertException) {
                    validErrors.add(new SimpleValidError("convert-exception", e.getMessage()));
                }
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("convert entity to map exception:", e);
                }
            }
        }

        return validErrors;
    }

    /**
     * 将实转换成 map
     *
     * @param o 实体对象
     *
     * @return 转换后的 map
     */
    private Map<String, Object> convertObjectToMap(Object o) {
        Map<String, Object> entity = new LinkedHashMap<String, Object>();
        if (Annotation.class.isAssignableFrom(o.getClass())) {
            Class<?> targetClass =  ((Annotation)o).annotationType();
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                try {
                    entity.put(method.getName(), method.invoke(o));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Class<?> targetClass =  o.getClass();
            List<Field> fields = getFields(targetClass);
            for (Field field : fields) {
                Object value;
                try {
                    Method method = targetClass.getDeclaredMethod("get" + changeFirstCharacterCase(field.getName(), true));
                    if (method != null) {
                        value = method.invoke(o);
                    } else {
                        value = field.get(o);
                    }
                    entity.put(field.getName(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return entity;
    }

    /**
     * 将字符首字母转换大小写
     *
     * @param str 字符值
     * @param capitalize true 为转换大写，否则 false
     *
     * @return 新的字符值
     */
    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (capitalize) {
            sb.append(Character.toUpperCase(str.charAt(0)));
        }
        else {
            sb.append(Character.toLowerCase(str.charAt(0)));
        }
        sb.append(str.substring(1));
        return sb.toString();
    }

    /**
     * 添加类元数据说明
     *
     * @param entityClass  实体类 class
     */
    public void addClass(Class<?> entityClass) {

        Valid valid = getAnnotation(entityClass, Valid.class);

        if (valid == null) {
            return ;
        }

        List<MappingKey> mappingKeys = new ArrayList<MappingKey>();

        List<Field> fieldList = getFields(entityClass);

        for (Field field : fieldList) {

            String name = field.getName();
            String aliasName = "";

            Alias alias = getAnnotation(field.getClass(), Alias.class);

            if (alias != null && !"".equals(alias.value())) {
                aliasName = alias.value();
            }

            List<Constraint> constraints = new ArrayList<Constraint>();
            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation ann : annotations) {
                ValidField validField = getAnnotation(ann.annotationType(), ValidField.class);
                if (validField == null) {
                    continue;
                }
                String constraintName = changeFirstCharacterCase(ann.annotationType().getSimpleName(), false);
                String defaultMessage = getDefaultMessage(constraintName);
                String message = invokeMethod("message", ann).toString();
                Map<String, Object> map = convertObjectToMap(ann);
                MapConstraintElement element = new MapConstraintElement(map);
                constraints.add(new SimpleConstraint(constraintName, message, defaultMessage, element));
            }

            if (!constraints.isEmpty()) {
                mappingKeys.add(new SimpleMappingKey(name, aliasName, constraints));
            }

        }

        getMappingMetadataMap().put(entityClass.getName(),new SimpleMappingMetadata(mappingKeys));
    }

    /**
     * 获取类的注解
     *
     * @param entityClass 类 class 对象
     * @param annotationClass 注解 对象
     *
     * @return 找到返回注解实例否则返回 null
     */
    public <A extends Annotation> A getAnnotation(Class<?> entityClass, Class<A> annotationClass) {

        do {
            A valid = entityClass.getAnnotation(annotationClass);
            if (valid != null) {
                return valid;
            }
            entityClass = entityClass.getSuperclass();
        } while (entityClass != null);

        return null;
    }

    /**
     * 获取字段值
     *
     * @param name 字段名称
     * @param o 字段的所属对象
     *
     * @return 值
     */
    private Object getFieldValue(String name, Object o) {
        try {
            Field field = o.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 执行方法
     *
     * @param name 方法名
     * @param o 要执行方法的对象
     * @param args 方法参数
     *
     * @return 执行结果
     */
    private Object invokeMethod(String name, Object o, Object... args) {
        try {
            if (Annotation.class.isAssignableFrom(o.getClass())) {
                Method method = ((Annotation) o).annotationType().getDeclaredMethod(name);
                method.setAccessible(true);
                return method.invoke(o, args);
            } else {
                Method method = o.getClass().getDeclaredMethod(name);
                method.setAccessible(true);
                return method.invoke(o, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取类所有字段
     *
     * @param entityClass 类 class 对象
     *
     * @return 字段集合
     */
    private List<Field> getFields(Class<?> entityClass) {

        List<Field> fieldList = new ArrayList<Field>();
        do {
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                fieldList.add(field);
            }
            entityClass = entityClass.getSuperclass();
        } while(entityClass != null);

        return fieldList;
    }
    
    /**
     * 是否忽略实体转型到 map 时候出现的异常
     *
     * @param ignoreEntityConvertException true 表示，否则 false
     */
    public void setIgnoreEntityConvertException(boolean ignoreEntityConvertException) {
        this.ignoreEntityConvertException = ignoreEntityConvertException;
    }

    /**
     * 设置是否忽略 null 对象验证
     *
     * @param ignoreNullObject true 为是，否则 false
     */
    public void setIgnoreNullObject(boolean ignoreNullObject) {
        this.ignoreNullObject = ignoreNullObject;
    }
}
