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

package com.github.dactiv.universe.entity.validation;

import com.github.dactiv.universe.entity.validation.mapping.SimpleConstraint;
import com.github.dactiv.universe.entity.validation.mapping.SimpleMappingMetadata;
import com.github.dactiv.universe.entity.validation.valid.*;
import com.github.dactiv.universe.entity.validation.valid.error.SimpleValidError;
import com.github.dactiv.universe.entity.validation.exception.MappingMetadataNotFoundException;
import com.github.dactiv.universe.entity.validation.exception.ValidatorNotFoundException;
import com.github.dactiv.universe.entity.validation.mapping.Dom4jConstraintElement;
import com.github.dactiv.universe.entity.validation.mapping.SimpleMappingKey;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Map 对象验证类
 *
 * @author maurice
 */
@SuppressWarnings("unchecked")
public class MapValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapValidation.class);

    /**
     * 默认的 i18n 配置文件
     */
    public static final String DEFAULT_I18N_PROPERTIES = "/validation.i18n.zh_cn.properties";

    /**
     * 默认 i18n 标准前缀名
     */
    public static final String DEFAULT_I18N_KEY_PREFIX = "validate.i18n.";

    // i18n 配置文件
    private Properties properties = new Properties();
    // 映射 xml 文件或类实体的元数据说明
    private Map<String, MappingMetadata> mappingMetadataMap = new HashMap<String, MappingMetadata>();
    // key 形式的验证器 map
    private Map<String, Validator> validatorMap = new HashMap<String, Validator>();
    // dom4j xml reader 用于读取 xml
    private SAXReader reader = new SAXReader();

    /**
     * Map 对象验证类
     */
    public MapValidation() {
        InputStream inputStream = this.getClass().getResourceAsStream(DEFAULT_I18N_PROPERTIES);
        setLocalization(inputStream);
        initValidatorMap();
    }

    /**
     * 初始化验证器
     */
    protected void initValidatorMap() {
        validatorMap.put(RequiredValidator.NAME, new RequiredValidator());
        validatorMap.put(LengthValidator.NAME, new LengthValidator());
        validatorMap.put(NumberValidator.NAME, new NumberValidator());
        validatorMap.put(EmailValidator.NAME, new EmailValidator());
        validatorMap.put(BetweenValidator.NAME, new BetweenValidator());
        validatorMap.put(DateValidator.NAME, new DateValidator());
        validatorMap.put(MaxValidator.NAME, new MaxValidator());
        validatorMap.put(MinValidator.NAME, new MinValidator());
        validatorMap.put(EqualValidator.NAME, new EqualValidator());
        validatorMap.put(NotEqualValidator.NAME, new NotEqualValidator());
        validatorMap.put(RegularExpressionValidator.NAME, new RegularExpressionValidator());
        validatorMap.put(EmptyValueCustomValidator.NAME, new EmptyValueCustomValidator());
        validatorMap.put(CustomValidator.NAME, new CustomValidator());
    }

    /**
     * 设置自定义验证器
     *
     * @param validator 验证器集合
     */
    public void setValidator(List<Validator> validator) {
        for (Validator v : validator) {
            setValidator(v);
        }
    }

    /**
     * 设置允许 null 或 "" 值的自定义验证器
     *
     * @param validator 自定义验证器集合
     */
    public void setEmptyValueCustomValidator(List<ContainsKeyValidator> validator) {
        EmptyValueCustomValidator vm = (EmptyValueCustomValidator)validatorMap.get(EmptyValueCustomValidator.NAME);
        for (ContainsKeyValidator v : validator) {
            vm.getCustomValidatorMap().put(v.getName(), v);
        }
    }

    /**
     * 设置非允许 null 或 "" 值的自定义验证器
     *
     * @param validator 自定义验证器集合
     */
    public void setCustomValidator(List<ContainsKeyValidator> validator) {
        CustomValidator vm = (CustomValidator)validatorMap.get(EmptyValueCustomValidator.NAME);
        for (ContainsKeyValidator v : validator) {
            vm.getCustomValidatorMap().put(v.getName(), v);
        }
    }

    /**
     * 设置自定义验证器
     *
     * @param validator 验证器
     */
    public void setValidator(Validator validator) {
        validatorMap.put(validator.getName(), validator);
    }

    /**
     * 获取自定义验证器
     *
     * @return 自定义验证器
     */
    public Map<String, Validator> getValidatorMap() {
        return validatorMap;
    }

    /**
     * 设置 i18n 国际化配置文件
     *
     * @param is  文件 input stream
     */
    public void setLocalization(InputStream is) {
        try {
            properties.load(new InputStreamReader(is,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取验证元数据 map
     *
     * @return 验证元数据 map
     */
    public Map<String, MappingMetadata> getMappingMetadataMap() {
        return mappingMetadataMap;
    }

    /**
     * 添加映射 xml 文件的元数据说明
     *
     * @param mapperPath 映射文件路径
     *
     * @throws DocumentException
     */
    public void addMapper(String mapperPath) throws DocumentException {
        addMapper(this.getClass().getResourceAsStream(mapperPath));
    }

    /**
     * 映射 xml 文件的元数据说明
     *
     * @param mapper input stream
     *
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public void addMapper(InputStream mapper) throws DocumentException {
        Document document = reader.read(mapper);

        Element root = document.getRootElement();

        List<Element> keyElements = root.elements("key");
        List<MappingKey> mappingKeys = new ArrayList<MappingKey>();

        for (Element key : keyElements) {

            String name = key.attributeValue("name");
            String ailas = key.attributeValue("alias");
            List<Constraint> constraints = new ArrayList<Constraint>();

            List<Element> constraintElement = key.elements();

            for (Element constraint : constraintElement) {
                String constraintName = constraint.getName();
                String defaultMessage = getDefaultMessage(constraintName);
                String message = constraint.attributeValue("message");
                Dom4jConstraintElement element = new Dom4jConstraintElement(constraint);
                constraints.add(new SimpleConstraint(constraintName, message, defaultMessage, element));
            }

            mappingKeys.add(new SimpleMappingKey(name,ailas,constraints));

        }

        mappingMetadataMap.put(root.attributeValue("name"),new SimpleMappingMetadata(mappingKeys));

    }

    /**
     * 验证，如果验证失败，通过返回值的验证错误对象集合获取错误信息
     *
     * @param map map
     * @param mapperName xml 映射文件 mapper 节点 name 名称
     *
     * @return 如果验证成功，返回值的 size 为 0，否则 size 大于 0
     */
    public List<ValidError> valid(Map<String, Object> map, String mapperName) {

        if (!mappingMetadataMap.containsKey(mapperName)) {
            throw new MappingMetadataNotFoundException("[" + mapperName + "] mapper file not found.");
        }

        MappingMetadata validateMapper = mappingMetadataMap.get(mapperName);

        List<ValidError> validateErrors = doValid(validateMapper, map);

        if (LOGGER.isDebugEnabled() && validateErrors.size() > 0) {
            LOGGER.debug("validate " + map + "failure:");
            for (ValidError ve : validateErrors) {
                LOGGER.debug("name: " + ve.getName() + ", message:" + ve.getMessage());
            }
        }

        return validateErrors;
    }

    /**
     * 执行验证
     *
     * @param validateMapper 验证映射
     * @param map 数据对象
     *
     * @return 如果验证成功，返回值的 size 为 0，否则 size 大于 0
     */
    protected List<ValidError> doValid(MappingMetadata validateMapper, Map<String, Object> map) {
        List<MappingKey> mappingKeys = validateMapper.getKeys();
        List<ValidError> validateErrors = new ArrayList<ValidError>();

        for (MappingKey mappingKey : mappingKeys) {

            List<Constraint> constraints = mappingKey.getConstraints();

            for (Constraint constraint : constraints) {

                String constraintName = constraint.getName();

                if (!validatorMap.containsKey(constraintName)) {
                    throw new ValidatorNotFoundException("[" + constraintName + "] validator not found.");
                }

                if (!validatorMap.get(constraintName).valid(mappingKey.getName(), map, constraint)) {
                    String message = constraint.getErrorMessage();
                    String name = mappingKey.getKeyName();
                    validateErrors.add(new SimpleValidError(name, message));
                }
            }
        }

        return validateErrors;
    }

    /**
     * 获取默认的信息
     *
     * @param constraintName 约束名称
     *
     * @return 信息
     */
    protected String getDefaultMessage(String constraintName) {
        return properties.getProperty(DEFAULT_I18N_KEY_PREFIX + constraintName);
    }
}
