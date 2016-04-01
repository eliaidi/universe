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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 远程模板源数据
 *
 * @author maurice
 */
public class RemoteTemplateSource {
    // URL 链接
    private URLConnection connection;
    private Boolean useCaches;

    /**
     * 远程模板源数据
     *
     * @param url url
     * @param useCaches 是否缓存
     *
     * @throws IOException
     */
    public RemoteTemplateSource(URL url, Boolean useCaches) throws IOException {
        this.connection = url.openConnection();
        this.useCaches = useCaches;
        if (useCaches != null) {
            connection.setUseCaches(useCaches);
        }
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
     *
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        try {
            return connection.getInputStream();
        } finally {
            if (connection != null && connection instanceof HttpURLConnection) {
                ((HttpURLConnection)connection).disconnect();
            }

        }
    }

}
