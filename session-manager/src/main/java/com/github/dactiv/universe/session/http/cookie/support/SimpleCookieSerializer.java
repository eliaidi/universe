package com.github.dactiv.universe.session.http.cookie.support;

import com.github.dactiv.universe.session.http.cookie.CookieSerializer;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单的 cookie 序列化实现
 *
 * @author maurice
 */
public class SimpleCookieSerializer implements CookieSerializer {

    // cookie 名称
    private String cookieName = "SESSION";
    // 是否使用原 cookie
    private Boolean useSecureCookie;
    // 是否使用 http cookie，如果当前容器支持 servlet 3 为 true，否则 false
    private boolean useHttpOnlyCookie = isServlet3();
    // cookie 路径
    private String cookiePath;
    // cookie 最大值
    private int cookieMaxAge = -1;
    // 域名
    private String domainName;
    // 域名正则
    private Pattern domainNamePattern;
    // jvm 路由地址
    private String jvmRoute;

    @Override
    public void writeCookieValue(CookieValue cookieValue) {
        HttpServletRequest request = cookieValue.getRequest();
        HttpServletResponse response = cookieValue.getResponse();

        String requestedCookieValue = cookieValue.getCookieValue();
        String actualCookieValue = this.jvmRoute == null ? requestedCookieValue : requestedCookieValue + this.jvmRoute;

        Cookie sessionCookie = new Cookie(this.cookieName, actualCookieValue);
        sessionCookie.setSecure(isSecureCookie(request));
        sessionCookie.setPath(getCookiePath(request));
        String domainName = getDomainName(request);

        if (domainName != null) {
            sessionCookie.setDomain(domainName);
        }

        if (this.useHttpOnlyCookie) {
            sessionCookie.setHttpOnly(true);
        }

        if ("".equals(requestedCookieValue)) {
            sessionCookie.setMaxAge(0);
        } else {
            sessionCookie.setMaxAge(this.cookieMaxAge);
        }

        response.addCookie(sessionCookie);
    }

    @Override
    public List<String> readCookieValues(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<String> matchingCookieValues = new ArrayList<String>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (this.cookieName.equals(cookie.getName())) {
                    String sessionId = cookie.getValue();
                    if (sessionId == null) {
                        continue;
                    }
                    if (this.jvmRoute != null && sessionId.endsWith(this.jvmRoute)) {
                        sessionId = sessionId.substring(0,sessionId.length() - this.jvmRoute.length());
                    }
                    matchingCookieValues.add(sessionId);
                }
            }
        }
        return matchingCookieValues;
    }

    /**
     * 设置是否使用原 cookie
     *
     * @param useSecureCookie true 表示是，否则 false
     */
    public void setUseSecureCookie(boolean useSecureCookie) {
        this.useSecureCookie = useSecureCookie;
    }

    /**
     * 如果当前容器支持 servlet 3+ 可以设置 cookie 的 useHttpOnlyCookie 为 true， 否则无法使用该属性
     *
     * @param useHttpOnlyCookie true 为使用 useHttpOnlyCookie ，但前提容器必须支持 servlet 3+，否则 false
     */
    public void setUseHttpOnlyCookie(boolean useHttpOnlyCookie) {
        if (useHttpOnlyCookie && !isServlet3()) {
            throw new IllegalArgumentException("无法设置 useHttpOnlyCookie 为 true，因为系统不支持 servlet 3+");
        }
        this.useHttpOnlyCookie = useHttpOnlyCookie;
    }

    /**
     * 判断是否原 cookie
     *
     * @param request HttpServletRequest
     *
     * @return true 表示是，否则 false
     */
    private boolean isSecureCookie(HttpServletRequest request) {
        if (this.useSecureCookie == null) {
            return request.isSecure();
        }
        return this.useSecureCookie;
    }

    /**
     * 设置 cookie 路径
     *
     * @param cookiePath cookie 路径
     */
    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    /**
     * 设置 cookie 名称
     *
     * @param cookieName cookie 名称
     */
    public void setCookieName(String cookieName) {
        if (cookieName == null) {
            throw new IllegalArgumentException("cookieName cannot be null");
        }
        this.cookieName = cookieName;
    }

    /**
     * 设置 cookie 最大值
     *
     * @param cookieMaxAge 最大值
     */
    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    /**
     * 设置域名
     *
     * @param domainName 域名
     */
    public void setDomainName(String domainName) {
        if (this.domainNamePattern != null) {
            throw new IllegalStateException("域名表达式不能为 null");
        }
        this.domainName = domainName;
    }

    /**
     * 设置域名正则表达式
     *
     * @param domainNamePattern 域名正则表达式
     */
    public void setDomainNamePattern(String domainNamePattern) {
        if (this.domainName != null) {
            throw new IllegalStateException("域名表达式不能为 null");
        }
        this.domainNamePattern = Pattern.compile(domainNamePattern, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 设置用于识别哪个 JVM session 关联的途径,这可以帮助跟踪一个特定用户的日志
     *
     * @param jvmRoute session JVM 路由
     */
    public void setJvmRoute(String jvmRoute) {
        this.jvmRoute = "." + jvmRoute;
    }

    /**
     * 获取域名, 如果 domainName 有值，获取 domainName 的值，
     * 否则如果 domainNamePattern 不为 null,
     * 并且 domainNamePattern matcher request 的 server name 获取 matcher.group(1)
     * 否则返回 null
     *
     * @param request HttpServletRequest
     *
     * @return 域名
     */
    private String getDomainName(HttpServletRequest request) {
        if (this.domainName != null) {
            return this.domainName;
        }
        if (this.domainNamePattern != null) {
            Matcher matcher = this.domainNamePattern.matcher(request.getServerName());
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 获取 cookie 路径
     *
     * @param request HttpServletRequest
     *
     * @return 路径
     */
    private String getCookiePath(HttpServletRequest request) {
        if (this.cookiePath == null) {
            return request.getContextPath() + "/";
        }
        return this.cookiePath;
    }

    /**
     * 判断是否 servlet 3
     *
     * @return true 表示是，否则 false
     */
    private boolean isServlet3() {
        try {
            ServletRequest.class.getMethod("startAsync");
            return Boolean.TRUE;
        } catch (NoSuchMethodException e) {
        }
        return Boolean.FALSE;
    }
}