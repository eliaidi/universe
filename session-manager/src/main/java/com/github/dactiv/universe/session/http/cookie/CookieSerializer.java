package com.github.dactiv.universe.session.http.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * Cookie 序列化接口
 *
 * @author maurice
 */
public interface CookieSerializer {

    /**
     * 在 cookie 里写入一个 值
     *
     * @param cookieValue 值
     */
    void writeCookieValue(CookieValue cookieValue);

    /**
     * 读取 cookie 值
     *
     * @param request HttpServletRequest
     *
     * @return
     */
    List<String> readCookieValues(HttpServletRequest request);

    /**
     * cookie 值对象，用于构造一个cookie 值使用
     */
    class CookieValue {
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final String cookieValue;

        /**
         * 构造方法
         *
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param cookieValue 值
         */
        public CookieValue(HttpServletRequest request, HttpServletResponse response,
                           String cookieValue) {
            this.request = request;
            this.response = response;
            this.cookieValue = cookieValue;
        }

        /**
         * 获取 HttpServletRequest
         *
         * @return HttpServletRequest
         */
        public HttpServletRequest getRequest() {
            return this.request;
        }

        /**
         * 获取 HttpServletResponse
         *
         * @return HttpServletResponse
         */
        public HttpServletResponse getResponse() {
            return this.response;
        }

        /**
         * 获取 cookie 值
         *
         * @return 值
         */
        public String getCookieValue() {
            return this.cookieValue;
        }
    }

}
