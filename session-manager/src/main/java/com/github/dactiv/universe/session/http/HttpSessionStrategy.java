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

import com.github.dactiv.universe.session.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 映射 request 和 responses 构造成 http session 的策略接口
 * @author maurice
 */
public interface HttpSessionStrategy {

    /**
     * 获取一个从 request 里获取的 session id
     *
     * @param request HttpServletRequest
     *
     * @return session id
     */
    String getRequestedSessionId(HttpServletRequest request);


    /**
     * 当一个 session 被创建的时候触发该方法
     *
     * @param session session
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    void onNewSession(Session session, HttpServletRequest request,
                      HttpServletResponse response);

    /**
     * 当 session 超时的时候触发该方法
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    void onInvalidateSession(HttpServletRequest request, HttpServletResponse response);

}
