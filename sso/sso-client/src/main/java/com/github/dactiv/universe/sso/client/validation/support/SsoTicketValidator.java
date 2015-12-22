package com.github.dactiv.universe.sso.client.validation.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dactiv.universe.sso.client.authentication.support.SimpleAttributePrincipal;
import com.github.dactiv.universe.sso.client.exception.TicketValidationException;
import com.github.dactiv.universe.sso.client.validation.Assertion;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * sso ticket 验证器
 *
 * @author maurice
 */
public class SsoTicketValidator extends AbstractUrlTicketValidator {

    /**
     * 用户名的 key 名称
     */
    public final static String USERNAME_KEY_NAME = "username";
    /**
     * 用户其他属性 key 名称
     */
    public final static String ATTRIBUTE_KEY_NAME = "attributes";
    /**
     * 错误属性 key 名称
     */
    public final static String ERROR_KEY_NAME = "error";
    // jackson json object mapper
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * url 形式的票据验证抽象类
     *
     * @param ssoServiceUrl sso 服务 url
     */
    public SsoTicketValidator(String ssoServiceUrl) {
        super(ssoServiceUrl);
    }

    /**
     * 解析 sso 的响应
     *
     * @param response 响应信息
     *
     * @return 服务器响应的断言结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public Assertion parseResponseFromServer(String response) {

        try {
            Map<String, Object> map = objectMapper.readValue(response, Map.class);

            if(map.containsKey(ERROR_KEY_NAME)) {
                String error = map.get(ERROR_KEY_NAME).toString();
                throw new TicketValidationException(error);
            }

            String username = map.get(USERNAME_KEY_NAME).toString();
            map.remove(USERNAME_KEY_NAME);
            Map<String, Object> attributes = (Map<String, Object>) map.get(ATTRIBUTE_KEY_NAME);
            map.remove(ATTRIBUTE_KEY_NAME);

            return new SimpleAssertion(new Date(), map, new SimpleAttributePrincipal(username, attributes));
        } catch (IOException e) {
            throw new TicketValidationException(e);
        }
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
