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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 远程模板源数据
 *
 * @author maurice
 */
public class RemoteTemplateSource {

    // URL 链接
    private URLConnection connection;
    // https host name verifier;
    private HostnameVerifier hostnameVerifier;
    // 链接超时时间
    private int connectTimeout;
    // 发送的 request property
    private Map<String, String> requestProperty = new HashMap<>();
    // 读取超时时间
    private int readTimeout;
    // 是否使用缓存
    private Boolean useCaches;

    /**
     * 远程模板源数据
     *
     * @param url             url
     * @param hv              如果为 https 的 hostname verifier
     * @param connectTimeout  链接超时时间
     * @param requestProperty 请求属性
     * @param readTimeout     读取超时时间
     * @param useCaches       是hi否使用缓存
     *
     * @throws IOException
     */
    public RemoteTemplateSource(URL url, HostnameVerifier hv, int connectTimeout, Map<String, String> requestProperty, int readTimeout, Boolean useCaches) throws IOException {
        this.connection = url.openConnection();
        this.hostnameVerifier = hv;
        this.connectTimeout = connectTimeout;
        this.requestProperty = requestProperty;
        this.readTimeout = readTimeout;
        this.useCaches = useCaches;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RemoteTemplateSource && connection.equals(((RemoteTemplateSource) o).connection);
    }

    @Override
    public int hashCode() {
        return connection.hashCode();
    }

    @Override
    public String toString() {
        return connection.toString();
    }

    /**
     * 获取 url input 流
     *
     * @return 流信息
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        try {
            // 如果为 https，判断是否需要加 hostname verifier
            if (connection instanceof HttpsURLConnection) {
                HostnameVerifier hv = hostnameVerifier == null ? HttpsURLConnection.getDefaultHostnameVerifier() : hostnameVerifier;
                ((HttpsURLConnection) connection).setHostnameVerifier(hv);
            }
            // 设置链接超时时间
            connection.setConnectTimeout(connectTimeout);
            // 设置读取超时时间
            connection.setReadTimeout(readTimeout);

            if (useCaches != null) {
                connection.setUseCaches(useCaches);
            }

            // 设置请求配置信息
            if (requestProperty != null && !requestProperty.isEmpty()) {
                for (Map.Entry<String, String> entry : requestProperty.entrySet()) {
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.connect();

            return connection.getInputStream();
        } finally {
            if (connection != null && connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }

        }
    }

}
