package com.github.dactiv.universe.sso.server.organization.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dactiv.universe.sso.server.exception.OrganizationException;
import com.github.dactiv.universe.sso.server.organization.OrganizationManager;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * json 文件的机构管理
 *
 * @author maurice
 */
public class JsonFileOrganizationManager implements OrganizationManager {

    /**
     * 默认存放 json 文件的文件夹路径
     */
    public final static String DEFAULT_JSON_FILE_PATH = "./org/";
    // 存放 json 文件的文件夹路径
    private String jsonFilePath = DEFAULT_JSON_FILE_PATH;
    // 存放 json 文件的文件夹路径的 File 类
    private File jsonFile = new File(jsonFilePath);
    // jackson object mapper
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 初始化
     */
    public void install() {
        if (!jsonFile.exists() || !jsonFile.mkdirs()) {
            throw new OrganizationException("创建" + jsonFilePath + "文件夹失败");
        }
    }

    /**
     * 通过唯一识别获取机构实体
     *
     * @param identification 唯一识别
     * @return 机构实体
     */
    @Override
    public Organization getByWildcard(String identification) {

        if (StringUtils.isEmpty(identification)) {
            return null;
        }

        List<Organization> list = getOrganizationList();
        for (Organization organization : list) {
            Pattern pattern = Pattern.compile(organization.getWildcard(), Pattern.CASE_INSENSITIVE);

            if (pattern.matcher(identification).matches()) {
                return organization;
            }
        }

        return null;
    }

    /**
     * 获取所有机构信息
     *
     * @return 机构信息
     */
    private List<Organization> getOrganizationList() {

        List<Organization> result = new ArrayList<>();
        File[] files = jsonFile.listFiles();

        if (ArrayUtils.isEmpty(files)) {
            return result;
        }

        try {
            for (File file : files) {
                result.add(objectMapper.readValue(file, Organization.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 设置存放 json 文件的文件夹路径
     *
     * @param jsonFilePath 文件夹路径
     */
    public void setJsonFilePath(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.jsonFile = new File(jsonFilePath);
    }

    /**
     * 设置 jackson json 的 object mapper
     *
     * @param objectMapper object mapper
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
