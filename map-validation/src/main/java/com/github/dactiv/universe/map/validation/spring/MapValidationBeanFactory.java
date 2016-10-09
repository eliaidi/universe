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

package com.github.dactiv.universe.map.validation.spring;

import com.github.dactiv.universe.map.validation.EntityValidation;
import com.github.dactiv.universe.map.validation.Validator;
import com.github.dactiv.universe.map.validation.MapValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Map 验证类的 spring 支持
 *
 * @author maurice
 */
public class MapValidationBeanFactory implements FactoryBean<MapValidation>, InitializingBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(MapValidationBeanFactory.class);

    // Map 验证类
    private EntityValidation entityValidation;
    // Map 映射文件路径
    private Resource[] mapperLocations;
    // 国际化文件路径
    private Resource localization;
    // 自定义验证器集合
    private List<Validator> validatorList = new ArrayList<Validator>();

    @Override
    public MapValidation getObject() throws Exception {
        return entityValidation;
    }

    @Override
    public Class<?> getObjectType() {
        return entityValidation == null ? MapValidation.class : entityValidation.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        entityValidation = new EntityValidation();

        if (localization != null) {
            LOGGER.info("setting i18n properties : " + localization.getFilename());
            entityValidation.setLocalization(localization.getInputStream());
        }

        for (Resource mapper : mapperLocations) {
            LOGGER.info("add " + mapper.getFilename() + " mapper");
            entityValidation.addMapper(mapper.getInputStream());
        }

        if (validatorList.size() > 0) {
            for (Validator validator : validatorList) {
                LOGGER.info("add " + validator.getName() + " validator");
                entityValidation.setValidator(validator);
            }
        }

    }

    /**
     * 设置 Map 映射文件路径
     *
     * @param mapperLocations Map 映射文件路径
     */
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    /**
     * 设置国际化文件路径
     *
     * @param localization 国际化文件路径
     */
    public void setLocalization(Resource localization) {
        this.localization = localization;
    }

    /**
     * 设置自定义验证器
     *
     * @param validatorList 验证器集合
     */
    public void setValidatorList(List<Validator> validatorList) {
        this.validatorList = validatorList;
    }
}
