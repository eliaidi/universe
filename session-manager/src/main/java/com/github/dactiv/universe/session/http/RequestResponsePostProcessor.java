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
import javax.servlet.http.HttpServletResponse;

/**
 * 可定制的 HttpServletRequest 和 HttpServletResponse 的处理接口
 *
 * @author maurice
 */
public interface RequestResponsePostProcessor {

    /**
     * 获取一个自定义的 HttpServletRequest
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return 自定义的 HttpServletRequest
     */
    HttpServletRequest wrapRequest(HttpServletRequest request,
                                   HttpServletResponse response);

    /**
     * 获取一个自定义的 HttpServletResponse
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return 自定义的 HttpServletResponse
     */
    HttpServletResponse wrapResponse(HttpServletRequest request,
                                     HttpServletResponse response);
}
