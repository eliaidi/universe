package com.github.dactiv.universe.session.http.cookie;

import com.github.dactiv.universe.session.Session;
import com.github.dactiv.universe.session.http.HttpSessionManager;
import com.github.dactiv.universe.session.http.MultiHttpSessionStrategy;
import com.github.dactiv.universe.session.http.cookie.support.SimpleCookieSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * cookie 形式的 http session 策略
 *
 * @author maurice
 */
public class CookieHttpSessionStrategy implements MultiHttpSessionStrategy, HttpSessionManager {

    private static final String SESSION_IDS_WRITTEN_ATTR = CookieHttpSessionStrategy.class.getName().concat(".SESSIONS_WRITTEN_ATTR");

    public static final String DEFAULT_ALIAS = "0";

    public static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_SESSION";

    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[\\w-]{1,50}$");

    private String sessionParam = DEFAULT_SESSION_ALIAS_PARAM_NAME;

    private CookieSerializer cookieSerializer = new SimpleCookieSerializer();

    @Override
    public String getCurrentSessionAlias(HttpServletRequest request) {
        if (this.sessionParam == null) {
            return DEFAULT_ALIAS;
        }
        String u = request.getParameter(this.sessionParam);
        if (u == null) {
            return DEFAULT_ALIAS;
        }
        if (!ALIAS_PATTERN.matcher(u).matches()) {
            return DEFAULT_ALIAS;
        }
        return u;
    }

    @Override
    public Map<String, String> getSessionIds(HttpServletRequest request) {
        List<String> cookieValues = this.cookieSerializer.readCookieValues(request);
        String sessionCookieValue = cookieValues.isEmpty() ? "" : cookieValues.iterator().next();
        Map<String, String> result = new LinkedHashMap<String, String>();
        StringTokenizer tokens = new StringTokenizer(sessionCookieValue, " ");
        if (tokens.countTokens() == 1) {
            result.put(DEFAULT_ALIAS, tokens.nextToken());
            return result;
        }
        while (tokens.hasMoreTokens()) {
            String alias = tokens.nextToken();
            if (!tokens.hasMoreTokens()) {
                break;
            }
            String id = tokens.nextToken();
            result.put(alias, id);
        }
        return result;
    }

    @Override
    public String encodeURL(String url, String sessionAlias) {
        String encodedSessionAlias = urlEncode(sessionAlias);
        int queryStart = url.indexOf("?");
        boolean isDefaultAlias = DEFAULT_ALIAS.equals(encodedSessionAlias);
        if (queryStart < 0) {
            return isDefaultAlias ? url : url + "?" + this.sessionParam + "=" + encodedSessionAlias;
        }
        String path = url.substring(0, queryStart);
        String query = url.substring(queryStart + 1, url.length());
        String replacement = isDefaultAlias ? "" : "$1" + encodedSessionAlias;
        query = query.replaceFirst("((^|&)" + this.sessionParam + "=)([^&]+)?", replacement);
        if (!isDefaultAlias && url.endsWith(query)) {
            // 不存在别名时候的处理方式
            if (!(query.endsWith("&") || query.length() == 0)) {
                query += "&";
            }
            query += this.sessionParam + "=" + encodedSessionAlias;
        }

        return path + "?" + query;
    }

    @Override
    public String getNewSessionAlias(HttpServletRequest request) {
        Set<String> sessionAliases = getSessionIds(request).keySet();
        if (sessionAliases.isEmpty()) {
            return DEFAULT_ALIAS;
        }
        long lastAlias = Long.decode(DEFAULT_ALIAS);
        for (String alias : sessionAliases) {
            long selectedAlias = safeParse(alias);
            if (selectedAlias > lastAlias) {
                lastAlias = selectedAlias;
            }
        }
        return Long.toHexString(lastAlias + 1);
    }

    @Override
    public String getRequestedSessionId(HttpServletRequest request) {
        Map<String, String> sessionIds = getSessionIds(request);
        String sessionAlias = getCurrentSessionAlias(request);
        return sessionIds.get(sessionAlias);
    }

    @Override
    public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
        Set<String> sessionIdsWritten = getSessionIdsWritten(request);
        if (sessionIdsWritten.contains(session.getId())) {
            return;
        }
        sessionIdsWritten.add(session.getId());

        Map<String, String> sessionIds = getSessionIds(request);
        String sessionAlias = getCurrentSessionAlias(request);
        sessionIds.put(sessionAlias, session.getId());

        String cookieValue = createSessionCookieValue(sessionIds);
        this.cookieSerializer.writeCookieValue(new SimpleCookieSerializer.CookieValue(request, response, cookieValue));
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> sessionIds = getSessionIds(request);
        String requestedAlias = getCurrentSessionAlias(request);
        sessionIds.remove(requestedAlias);

        String cookieValue = createSessionCookieValue(sessionIds);
        this.cookieSerializer.writeCookieValue(new SimpleCookieSerializer.CookieValue(request, response, cookieValue));
    }

    @Override
    public HttpServletRequest wrapRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(HttpSessionManager.class.getName(), this);
        return request;
    }

    @Override
    public HttpServletResponse wrapResponse(HttpServletRequest request, HttpServletResponse response) {
        return new MultiSessionHttpServletResponse(response, request);
    }

    private long safeParse(String hex) {
        try {
            return Long.decode("0x" + hex);
        }
        catch (NumberFormatException notNumber) {
            return 0;
        }
    }

    /**
     * URL 编码
     *
     * @param value 值
     *
     * @return 编码后的值
     */
    private String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有 session id
     *
     * @param request HttpServletRequest
     *
     * @return 所有 session id
     */
    @SuppressWarnings("unchecked")
    private Set<String> getSessionIdsWritten(HttpServletRequest request) {
        Set<String> sessionsWritten = (Set<String>) request.getAttribute(SESSION_IDS_WRITTEN_ATTR);
        if (sessionsWritten == null) {
            sessionsWritten = new HashSet<String>();
            request.setAttribute(SESSION_IDS_WRITTEN_ATTR, sessionsWritten);
        }
        return sessionsWritten;
    }

    /**
     * 创建一个 cookie 值
     *
     * @param sessionIds session id map
     *
     * @return 新的值
     */
    private String createSessionCookieValue(Map<String, String> sessionIds) {
        if (sessionIds.isEmpty()) {
            return "";
        }
        if (sessionIds.size() == 1 && sessionIds.keySet().contains(DEFAULT_ALIAS)) {
            return sessionIds.values().iterator().next();
        }

        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, String> entry : sessionIds.entrySet()) {
            String alias = entry.getKey();
            String id = entry.getValue();

            buffer.append(alias);
            buffer.append(" ");
            buffer.append(id);
            buffer.append(" ");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }


    /**
     *
     * 自定义的 HttpServletResponseWrapper
     *
     */
    class MultiSessionHttpServletResponse extends HttpServletResponseWrapper {
        private final HttpServletRequest request;

        MultiSessionHttpServletResponse(HttpServletResponse response, HttpServletRequest request) {
            super(response);
            this.request = request;
        }

        @Override
        public String encodeRedirectURL(String url) {
            url = super.encodeRedirectURL(url);
            return CookieHttpSessionStrategy.this.encodeURL(url,getCurrentSessionAlias(this.request));
        }

        @Override
        public String encodeURL(String url) {
            url = super.encodeURL(url);

            String alias = getCurrentSessionAlias(this.request);
            return CookieHttpSessionStrategy.this.encodeURL(url, alias);
        }
    }

    /**
     * 设置 cookie 序列化类
     *
     * @param cookieSerializer cookie 序列化类
     */
    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        this.cookieSerializer = cookieSerializer;
    }

    /**
     * 设置 session 参数名称
     *
     * @param sessionParam 参数名称
     */
    public void setSessionParam(String sessionParam) {
        this.sessionParam = sessionParam;
    }
}
