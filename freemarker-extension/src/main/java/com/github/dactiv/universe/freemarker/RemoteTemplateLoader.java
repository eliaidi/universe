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
package com.github.dactiv.universe.freemarker;

import freemarker.cache.URLTemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 远程模板加载器,
 *
 * @author maurice
 */
public class RemoteTemplateLoader extends URLTemplateLoader{

    private final static Logger LOGGER = LoggerFactory.getLogger(RemoteTemplateLoader.class);

    // 默认匹配模板名称正则表达式字符串
    private final static String DEFAULT_PATTERN_STRING = "(http|ftp|https|file):\\/\\/([\\w.]+\\/?)\\S*";
    // 匹配模板名称正则表达式字符串
    private String patternString = DEFAULT_PATTERN_STRING;
    // 匹配模板名称正则表达式
    private Pattern pattern = Pattern .compile(patternString);
    // https host name verifier;
    private HostnameVerifier hostnameVerifier;
    // 链接超时时间
    private int connectTimeout;
    // 发送的 request property
    private Map<String, String> requestProperty = new HashMap<>();
    // 读取超时时间
    private int readTimeout;

    /**
     * 设置匹配模板名称正则表达式, 默认为:(http|ftp|https|file):\/\/([\w.]+\/?)\S*
     *
     * @param patternString 正则表达式
     */
    public void setPatternString(String patternString) {
        this.patternString = patternString;
    }

    /**
     * 设置读取超时时间
     *
     * @param readTimeout 时间
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 设置请求配置
     *
     * @param requestProperty 配置 map
     */
    public void setRequestProperty(Map<String, String> requestProperty) {
        this.requestProperty = requestProperty;
    }

    /**
     * 设置链接超时时间
     *
     * @param connectTimeout 超时时间
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 设置 https host name verifier;
     *
     * @param hostnameVerifier name verifier
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }


    @Override
    public Object findTemplateSource(String name) throws IOException {
        URL url = getURL(name);
        return url == null ? null : new RemoteTemplateSource(url, hostnameVerifier, connectTimeout, requestProperty, readTimeout, getURLConnectionUsesCaches());
    }

    @Override
    protected URL getURL(String name) {

        Matcher matcher = pattern.matcher(name);
        // 判断是否匹配本类的正则，如果不匹配什么都做
        if(!matcher.matches()) {
            return null;
        }

        URL url;

        try {
            // 创建 URL
            url = new URL(name);
        } catch (IOException e) {
            if (LOGGER.isDebugEnabled()) {
                e.printStackTrace();
                LOGGER.warn("加载" + name + "错误", e);
            }
            url = null;
        }

        return url;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new InputStreamReader(((RemoteTemplateSource) templateSource).getInputStream(),encoding);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        ((RemoteTemplateSource) templateSource).close();
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((RemoteTemplateSource) templateSource).getLastModified();
    }
}
