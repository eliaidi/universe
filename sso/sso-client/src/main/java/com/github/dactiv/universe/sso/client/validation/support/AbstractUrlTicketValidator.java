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

package com.github.dactiv.universe.sso.client.validation.support;

import com.github.dactiv.universe.sso.client.exception.TicketValidationException;
import com.github.dactiv.universe.sso.client.validation.Assertion;
import com.github.dactiv.universe.sso.client.validation.TicketValidator;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * url 形式的票据验证抽象类
 *
 * @author maurice
 */
public abstract class AbstractUrlTicketValidator implements TicketValidator {

    /**
     * UTF-8 编码
     */
    public final static String UTF8_ENCODING = "UTF-8";

    /**
     * 重定向 url 参数名称
     */
    public final static String REDIRECT_URL_PARAM_NAME = "redirectUrl";
    /**
     * 票据参数名
     */
    public final static String TICKET_PARAM_NAME = "ticket";
    /**
     * 票据验证的控制器前缀
     */
    public final static String TICKET_VALIDATE_SUFFIX = "ticket-validate";

    // 使用 ssl 请求 sso 服务的 host name 校验
    private HostnameVerifier hostnameVerifier;
    // sso 服务 url
    private String ssoServiceUrl;
    // 请求链接的编码格式
    private String encoding = UTF8_ENCODING;
    // 请求后附加的自定义参数
    private Map<String, String> customParameters;

    /**
     * url 形式的票据验证抽象类
     *
     * @param ssoServiceUrl sso 服务 url
     */
    public AbstractUrlTicketValidator(String ssoServiceUrl) {
        this.ssoServiceUrl = ssoServiceUrl;
    }

    /**
     * 获取验证 url
     *
     * @param ticket       票据信息
     * @param organization 机构重定向 url 地址
     *
     * @return 验证 url 路径
     */
    protected String getValidationUrl(String ticket, String organization) {
        Map<String, String> urlParameters = new HashMap<>();

        urlParameters.put(TICKET_PARAM_NAME, ticket);
        urlParameters.put(REDIRECT_URL_PARAM_NAME, organization);

        if (this.customParameters != null) {
            urlParameters.putAll(this.customParameters);
        }

        StringBuilder builder = new StringBuilder(urlParameters.size() * 10 + this.ssoServiceUrl.length() + TICKET_VALIDATE_SUFFIX.length() +1);

        builder.append(ssoServiceUrl);

        if (!StringUtils.endsWith(ssoServiceUrl,"/")) {
            builder.append("/");
        }

        builder.append(TICKET_VALIDATE_SUFFIX);

        int i = 0;

        for (Map.Entry<String, String> entry : urlParameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null) {
                builder.append(i++ == 0 ? "?" : "&").append(key).append("=").append(encodeValue(value));
            }
        }

        return builder.toString();
    }

    /**
     * 对 string 的值做 url 编码转换
     *
     * @param value 值
     *
     * @return 转换后的值
     */
    private String encodeValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        try {
            return URLEncoder.encode(value, UTF8_ENCODING);
        } catch (final UnsupportedEncodingException e) {
            return value;
        }
    }

    /**
     * 验证票据
     *
     * @param ticket       票据
     * @param organization 机构信息
     *
     * @return 验证断言结果
     *
     * @throws TicketValidationException
     */
    @Override
    public Assertion validate(String ticket, String organization) throws TicketValidationException {
        String url = getValidationUrl(ticket, organization);
        try {
            String response = getResponseFromServer(new URL(url), hostnameVerifier, encoding);

            if (StringUtils.isEmpty(response)) {
                throw new TicketValidationException("sso 无响应");
            }
            return parseResponseFromServer(response);
        } catch (MalformedURLException e) {
            throw new TicketValidationException(e);
        }
    }

    /**
     * 解析 sso 的响应
     *
     * @param response 响应信息
     *
     * @return 服务器响应的断言结果
     */
    public abstract Assertion parseResponseFromServer(String response);

    /**
     * 发送链接并获取响应信息
     *
     * @param url              链接地址
     * @param hostnameVerifier host name 校验
     * @param encoding         发送流的编码
     *
     * @return sso 服务响应信息
     */
    public String getResponseFromServer(final URL url, final HostnameVerifier hostnameVerifier, final String encoding) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if (conn instanceof HttpsURLConnection) {
                HostnameVerifier hv = hostnameVerifier == null ? HttpsURLConnection.getDefaultHostnameVerifier() : hostnameVerifier;
                ((HttpsURLConnection)conn).setHostnameVerifier(hv);
            }
            final BufferedReader in;

            if (StringUtils.isEmpty(encoding)) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
            }

            String line;
            final StringBuilder stringBuffer = new StringBuilder(255);

            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            return stringBuffer.toString();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null && conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }

    }

    /**
     * 设置 https 的 hostname 校验
     *
     * @param hostnameVerifier hostname 校验
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    /**
     * 设置 sso 服务地址
     *
     * @param ssoServiceUrl 地址
     */
    public void setSsoServiceUrl(String ssoServiceUrl) {
        this.ssoServiceUrl = ssoServiceUrl;
    }

    /**
     * 设置发送 url 的流编码
     *
     * @param encoding 编码
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setCustomParameters(Map<String, String> customParameters) {
        this.customParameters = customParameters;
    }
}
