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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 由于当前的 spring OncePerRequestFilter 不能设置 servlet context 就用一个继承不是那么多的实现类
 *
 * @author maurice
 */
abstract class OncePerRequestFilter implements Filter{

    public static final String ALREADY_FILTERED_SUFFIX = ".FILTERED";

    private String alreadyFilteredAttributeName = getClass().getName().concat(ALREADY_FILTERED_SUFFIX);


    public final void doFilter(ServletRequest request, ServletResponse response,
                               FilterChain filterChain) throws ServletException, IOException {

        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("类 OncePerRequestFilter 仅支持 http servlet request");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        boolean hasAlreadyFilteredAttribute = request.getAttribute(this.alreadyFilteredAttributeName) != null;

        if (hasAlreadyFilteredAttribute) {

            filterChain.doFilter(request, response);
        }
        else {
            request.setAttribute(this.alreadyFilteredAttributeName, Boolean.TRUE);
            try {
                doFilterInternal(httpRequest, httpResponse, filterChain);
            }finally {
                request.removeAttribute(this.alreadyFilteredAttributeName);
            }
        }
    }

    protected abstract void doFilterInternal(HttpServletRequest request,
                                             HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException;

    public void init(FilterConfig config) {

    }

    public void destroy() {

    }
}
