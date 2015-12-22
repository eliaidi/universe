package com.github.dactiv.universe.map.validation.spring;

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
    private MapValidation mapValidation;
    // Map 映射文件路径
    private Resource[] mapperLocations;
    // 国际化文件路径
    private Resource localization;
    // 自定义验证器集合
    private List<Validator> validatorList = new ArrayList<Validator>();

    @Override
    public MapValidation getObject() throws Exception {
        return mapValidation;
    }

    @Override
    public Class<?> getObjectType() {
        return mapValidation == null ? MapValidation.class : mapValidation.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapValidation = new MapValidation();

        if (localization != null) {
            LOGGER.info("setting i18n properties : " + localization.getFilename());
            mapValidation.setLocalization(localization.getInputStream());
        }

        for (Resource mapper : mapperLocations) {
            LOGGER.info("add " + mapper.getFilename() + " mapper");
            mapValidation.addMapper(mapper.getInputStream());
        }

        if (validatorList.size() > 0) {
            for (Validator validator : validatorList) {
                LOGGER.info("add " + validator.getName() + " validator");
                mapValidation.setValidator(validator);
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
