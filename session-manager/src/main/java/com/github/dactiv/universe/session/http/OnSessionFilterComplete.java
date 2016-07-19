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
 * 当 session filter 完成时，触发该接口方法
 *
 * @author maurice
 */
public interface OnSessionFilterComplete {

    /**
     * 当 filter 全部完成时候触发该方法
     *
     * @param request http servlet request
     * @param response http servlet response
     */
    void onComplete(HttpServletRequest request, HttpServletResponse response);
}
