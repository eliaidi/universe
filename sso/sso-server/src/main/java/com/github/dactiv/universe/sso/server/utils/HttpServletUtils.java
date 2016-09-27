/*
 * Copyright 2016 dactiv
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
package com.github.dactiv.universe.sso.server.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * http serlvet 工具类
 *
 * @author maurice
 */
public class HttpServletUtils {

    /**
     * 获取 ip 地址
     *
     * @param request http servlet request
     *
     * @return ip 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 判断请求是否为代理IP
     *
     * @param request http servlet request
     *
     * @return http servlet request
     */
    public static boolean isProxyIp(HttpServletRequest request) {

        String ip = request.getHeader("Proxy-Client-IP");

        if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
            return Boolean.TRUE;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");

        if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * 添加 url 参数
     * @param url url
     * @param model 参数
     *
     * @return 新的 url 字符串
     *
     * @throws UnsupportedEncodingException
     */
    public static String appendUrlParams(String url, Map<String, Object> model) throws UnsupportedEncodingException {
        return appendUrlParams(url, null, model);
    }

    /**
     * 添加 url 参数
     *
     * @param url url
     * @param encodingScheme 字符编码
     * @param model 参数
     *
     * @return 新的 url 字符串
     *
     * @throws UnsupportedEncodingException
     */
    public static String appendUrlParams(String url, String encodingScheme, Map<String, Object> model)
            throws UnsupportedEncodingException {
        StringBuilder targetUrl = new StringBuilder(url);

        String fragment = null;
        int anchorIndex = targetUrl.toString().indexOf('#');
        if (anchorIndex > -1) {
            fragment = targetUrl.substring(anchorIndex);
            targetUrl.delete(anchorIndex, url.length());
        }

        boolean first = (targetUrl.toString().indexOf('?') < 0);

        if (model != null) {
            for (Object o : model.entrySet()) {

                if (first) {
                    targetUrl.append('?');
                    first = false;
                } else {
                    targetUrl.append('&');
                }

                Map.Entry entry = (Map.Entry) o;

                String encodedKey = entry.getKey().toString(); //URLEncoder.encode(entry.getKey().toString(), encodingScheme);
                String encodedValue = entry.getValue().toString();// (entry.getValue() != null ? URLEncoder.encode(entry.getValue().toString(), encodingScheme) : "");

                encodedKey = encodingScheme == null ? encodedKey : URLEncoder.encode(encodedKey, encodingScheme);
                encodedValue = encodingScheme == null ? encodedValue : URLEncoder.encode(encodedValue, encodingScheme);

                targetUrl.append(encodedKey).append('=').append(encodedValue);
            }
        }

        if (fragment != null) {
            targetUrl.append(fragment);
        }

        return targetUrl.toString();
    }

    /**
     * 是否异步请求
     *
     * @param request http servlet requet
     *
     * @return true 表示是，否则 false
     */
    public static boolean isAsynchronousRequest(HttpServletRequest request) {
        return StringUtils.equals(request.getHeader("X-Requested-With"),"XMLHttpRequest");
    }
}
