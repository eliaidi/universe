package com.github.dactiv.universe.map.validation;

import com.github.dactiv.universe.map.validation.exception.MappingMetadataNotFoundException;
import com.github.dactiv.universe.map.validation.mapping.SimpleConstraint;
import com.github.dactiv.universe.map.validation.mapping.SimpleMappingKey;
import com.github.dactiv.universe.map.validation.mapping.SimpleMappingMetadata;
import com.github.dactiv.universe.map.validation.valid.*;
import com.github.dactiv.universe.map.validation.valid.error.SimpleValidError;
import com.github.dactiv.universe.map.validation.exception.ValidatorNotFoundException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Map 对象验证类
 *
 * @author maurice
 */
public class MapValidation {

    private static Logger logger = LoggerFactory.getLogger(MapValidation.class);

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
    // 映射 xml 文件的元数据说明
    private Map<String, MappingMetadata> mappingMetadataMap = new HashMap<String, MappingMetadata>();
    // 验证器 map
    private Map<String, Validator> validatorMap = new HashMap<String, Validator>();
    // dom4j xml reader 用于读取xml
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
    private void initValidatorMap() {
        validatorMap.put(NotEmptyValidator.NAME, new NotEmptyValidator());
        validatorMap.put(LengthValidator.NAME, new LengthValidator());
        validatorMap.put(NumberValidator.NAME, new NumberValidator());
        validatorMap.put(EmailValidator.NAME, new EmailValidator());
        validatorMap.put(BetweenValidator.NAME, new BetweenValidator());
        validatorMap.put(DateValidator.NAME, new DateValidator());
        validatorMap.put(MaxValidator.NAME, new MaxValidator());
        validatorMap.put(MinValidator.NAME, new MinValidator());
        validatorMap.put(EqualValidator.NAME, new EqualValidator());
        validatorMap.put(NotEqualValidator.NAME, new NotEqualValidator());
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
     * 设置自定义验证器
     *
     * @param validator 验证器
     */
    public void setValidator(Validator validator) {
        validatorMap.put(validator.getName(), validator);
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
                String defaultMessage = properties.getProperty(DEFAULT_I18N_KEY_PREFIX + constraintName);
                String message = constraint.attributeValue("message");
                constraints.add(new SimpleConstraint(constraintName, message, defaultMessage, constraint));
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

        List<ValidError> validateErrors = new ArrayList<ValidError>();

        if (!mappingMetadataMap.containsKey(mapperName)) {
            throw new MappingMetadataNotFoundException("找不到[" + mapperName + "]的映射文件");
        }

        MappingMetadata validateMapper = mappingMetadataMap.get(mapperName);

        List<MappingKey> mappeingKeys = validateMapper.getKeys();

        for (MappingKey mappingKey : mappeingKeys) {

            List<Constraint> constraints = mappingKey.getConstraints();

            for (Constraint constraint : constraints) {

                String constraintName = constraint.getName();

                if (!validatorMap.containsKey(constraintName)) {
                    throw new ValidatorNotFoundException("找不到[" + constraintName + "]验证器");
                }

                if (!validatorMap.get(constraintName).valid(mappingKey.getName(), map, constraint)) {
                    String message = constraint.getErrorMessage();
                    String name = mappingKey.getKeyName();
                    validateErrors.add(new SimpleValidError(name, message));
                }
            }
        }

        if (logger.isDebugEnabled() && validateErrors.size() > 0) {
            logger.debug("validate " + map + "failure:");
            for (ValidError ve : validateErrors) {
                logger.debug("name: " + ve.getName() + ", message:" + ve.getMessage());
            }
        }

        return validateErrors;
    }

}
