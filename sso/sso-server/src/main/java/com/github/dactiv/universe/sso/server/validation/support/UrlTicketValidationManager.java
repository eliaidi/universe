package com.github.dactiv.universe.sso.server.validation.support;

import com.github.dactiv.universe.sso.server.validation.entity.support.SimpleValidateInfo;
import com.github.dactiv.universe.sso.server.exception.TicketNotFoundException;
import com.github.dactiv.universe.sso.server.exception.UrlTicketValidateException;
import com.github.dactiv.universe.sso.server.organization.OrganizationManager;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import com.github.dactiv.universe.sso.server.ticket.TicketManager;
import com.github.dactiv.universe.sso.server.ticket.entity.support.SimpleAuthenticationTicket;
import com.github.dactiv.universe.sso.server.validation.TicketValidationManager;
import com.github.dactiv.universe.sso.server.validation.entity.ValidateInfo;
import com.github.dactiv.universe.sso.server.validation.policy.AttributeReleasePolicy;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * url 形式的票据验证管理
 *
 * @author maurice
 */
public class UrlTicketValidationManager implements TicketValidationManager {
    /**
     * 票据的参数名称
     */
    public final static String TICKET_PARAM_NAME = "ticket";
    /**
     * 重定向的 url 参数名称
     */
    public final static String REDIRECT_URL_PARAM_NAME = "redirectUrl";
    /**
     * 用户属性的 key 名称
     */
    public final static String ATTRIBUTE_KEY_NAME="attributes";
    /**
     * 错误属性的 key 名称
     */
    public final static String ERROR_ATTRIBUTE_KEY_NAME = "error";
    /**
     * 是否记住我 key 名称
     */
    public final static String DEFAULT_REMEMBER_ME_ATTRIBUTE_KEY_NAME = "rememberMe";
    /**
     * 默认用户名的 key 名称
     */
    public final static String DEFAULT_USERNAME_ATTRIBUTE_KEY_NAME = "username";
    // 票据管理
    private TicketManager ticketManager;
    // 机构管理
    private OrganizationManager organizationManager;
    // 用户名属性 key 名称
    private String usernameAttributeKeyName = DEFAULT_USERNAME_ATTRIBUTE_KEY_NAME;
    // 记住我属性 key 名称
    private String rememberMeAttributeKeyName = DEFAULT_REMEMBER_ME_ATTRIBUTE_KEY_NAME;
    // 并发锁
    private Lock lock = new ReentrantLock();

    /**
     * 验证票据
     *
     * @param requestPairSource request
     * @return 验证结果
     */
    @Override
    public Map<String, Object> valid(Object requestPairSource) {

        if (WebUtils.isHttp(requestPairSource)) {
            throw new UrlTicketValidateException("该请求不是 http 请求");
        }
        HttpServletRequest request = (HttpServletRequest) requestPairSource;
        ValidateInfo validateInfo = createValidateInfo(request);

        Map<String, Object> result = new HashMap<>();

        try {
            result = valid(validateInfo);
        } catch (Exception e) {
            result.put(ERROR_ATTRIBUTE_KEY_NAME, e.getMessage());
            result.put(TICKET_PARAM_NAME, validateInfo.getTicket());
        }

        return result;
    }

    /**
     * 验证票据
     *
     * @param validateInfo 验证信息
     *
     * @return 验证结果
     */
    protected Map<String, Object> valid(ValidateInfo validateInfo) {

        SimpleAuthenticationTicket ticket = ticketManager.get(validateInfo.getTicket(), SimpleAuthenticationTicket.class);

        if (ticket == null) {
            throw new TicketNotFoundException("找不到 " + validateInfo.getTicket() + " 票据");
        }

        Organization organization = organizationManager.getByWildcard(validateInfo.getRedirectUrl());

        lock.lock();

        try {

            if (ticket.isExpired()) {
                throw new UrlTicketValidateException(validateInfo.getTicket() + " 票据超时");
            }

            if (!StringUtils.equals(ticket.getOrganization().getWildcard(), organization.getWildcard())) {
                throw new UrlTicketValidateException(validateInfo.getTicket() + " 票据的机构匹配");
            }

        } finally {
            ticketManager.delete(ticket.getId());
            lock.unlock();
        }

        Object principal = ticket.getPrincipal();
        AuthenticationToken authenticationToken = ticket.getAuthenticationToken();
        AttributeReleasePolicy attributeReleasePolicy = organization.getAttributeReleasePolicy();

        Map<String, Object> attributes = attributeReleasePolicy == null ?
                new HashMap<String, Object>() :
                attributeReleasePolicy.getAttributes(principal);

        String username = organization.getUsernameAttributePolicy().getUsername(principal, organization);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put(usernameAttributeKeyName, username);
        result.put(ATTRIBUTE_KEY_NAME, attributes);

        if (authenticationToken instanceof RememberMeAuthenticationToken) {
            RememberMeAuthenticationToken token = (RememberMeAuthenticationToken)authenticationToken;
            result.put(rememberMeAttributeKeyName, token.isRememberMe());
        }

        return result;
    }

    /**
     * 创建验证信息
     *
     * @param request http servlet request 对象
     *
     * @return 验证信息
     */
    protected ValidateInfo createValidateInfo(HttpServletRequest request) {

        String ticket = WebUtils.getCleanParam(request, TICKET_PARAM_NAME);
        String redirectUrl = WebUtils.getCleanParam(request, REDIRECT_URL_PARAM_NAME);

        return new SimpleValidateInfo(ticket, redirectUrl);
    }

    /**
     * 设置票据管理
     *
     * @param ticketManager 票据管理
     */
    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    /**
     * 设置机构管理
     *
     * @param organizationManager 机构管理
     */
    public void setOrganizationManager(OrganizationManager organizationManager) {
        this.organizationManager = organizationManager;
    }

    /**
     * 设置用户名返回的 key 名称
     *
     * @param usernameAttributeKeyName key 名称
     */
    public void setUsernameAttributeKeyName(String usernameAttributeKeyName) {
        this.usernameAttributeKeyName = usernameAttributeKeyName;
    }

    /**
     * 设置记住我属性 key 名称
     *
     * @param rememberMeAttributeKeyName key 名称
     */
    public void setRememberMeAttributeKeyName(String rememberMeAttributeKeyName) {
        this.rememberMeAttributeKeyName = rememberMeAttributeKeyName;
    }
}
