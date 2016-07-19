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
package com.github.dactiv.universe.session.http;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * http 形式的 session 管理接口
 *
 * @author maurice
 */
public interface HttpSessionManager {

    /**
     * 获取当前 session 别名
     *
     * @param request HttpServletRequest
     *
     * @return 别名
     */
    String getCurrentSessionAlias(HttpServletRequest request);

    /**
     * 获取 session 中的所有 id
     *
     * @param request HttpServletRequest
     *
     * @return
     */
    Map<String, String> getSessionIds(HttpServletRequest request);

    /**
     *
     * 该方法能够给定会话URL编码的别名。
     *
     * @param url url
     * @param sessionAlias session 别名
     *
     * @return 编码后的 url
     */
    String encodeURL(String url, String sessionAlias);

    /**
     * 获取一个新的 session 别名
     *
     * @param request HttpServletRequest
     *
     * @return 别名
     */
    String getNewSessionAlias(HttpServletRequest request);
}
