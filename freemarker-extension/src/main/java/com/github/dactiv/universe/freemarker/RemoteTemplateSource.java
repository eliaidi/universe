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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 远程模板源数据
 *
 * @author maurice
 */
public class RemoteTemplateSource {

    // URL 链接
    private URLConnection connection;
    // 当前返回 URLConnection 的 input 流
    private InputStream is;
    // url
    private URL url;

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
        this.url = url;
        this.connection = url.openConnection();
        // 如果为 https，判断是否需要加 hostname verifier
        if (connection instanceof HttpsURLConnection) {
            hv = hv == null ? HttpsURLConnection.getDefaultHostnameVerifier() : hv;
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
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RemoteTemplateSource && url.equals(((RemoteTemplateSource) o).url);
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
        is = connection.getInputStream();
        return is;

    }

    /**
     * 关闭 source 对象
     *
     * @throws IOException
     */
    public void close() throws IOException {
        try {

            if (connection != null && connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }

            if (is != null) {
                is.close();
            }

        } finally {
            connection = null;
            is = null;
        }
    }

    /**
     * 获取 connection 最后修改时间
     *
     * @return 时间戳
     */
    public long getLastModified() {
        long lastModified = connection.getLastModified();
        if (lastModified == -1L && url.getProtocol().equals("file")) {
            return new File(url.getFile()).lastModified();
        } else {
            return lastModified;
        }
    }
}
