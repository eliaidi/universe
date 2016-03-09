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


package com.github.dactiv.universe.sso.server.validation.support;

import com.github.dactiv.universe.sso.server.exception.TicketNotFoundException;
import com.github.dactiv.universe.sso.server.exception.UrlTicketValidateException;
import com.github.dactiv.universe.sso.server.organization.OrganizationManager;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import com.github.dactiv.universe.sso.server.ticket.TicketManager;
import com.github.dactiv.universe.sso.server.ticket.entity.support.SimpleAuthenticationTicket;
import com.github.dactiv.universe.sso.server.validation.TicketValidationManager;
import com.github.dactiv.universe.sso.server.validation.entity.ValidateToken;
import com.github.dactiv.universe.sso.server.validation.policy.AttributeReleasePolicy;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象票据验证实现
 *
 * @author maurice
 */
public abstract class AbstractTicketValidationManager implements TicketValidationManager{
    /**
     * 票据的参数名称
     */
    public final static String TICKET_PARAM_NAME = "ticket";
    /**
     * 用户属性的 key 名称
     */
    public final static String ATTRIBUTE_KEY_NAME = "attributes";
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
     * @param requestPairSource 请求信息
     * @return 断言结果
     */
    @Override
    public Map<String, Object> valid(Object requestPairSource) {

        Map<String, Object> result = new HashMap<>();
        ValidateToken token = createValidToken(requestPairSource);

        try {
            result = valid(token);
        } catch (Exception e) {
            result.put(ERROR_ATTRIBUTE_KEY_NAME, e.getMessage());
            result.put(TICKET_PARAM_NAME, token.getTicket());
        }

        return result;
    }

    /**
     * 创建验证票据的令牌
     *
     * @param requestPairSource 请求信息
     *
     * @return 验证令牌
     */
    protected abstract ValidateToken createValidToken(Object requestPairSource);

    /**
     * 验证票据
     *
     * @param token 验证令牌
     * @return 断言结果
     */
    protected Map<String, Object> valid(ValidateToken token) {
        SimpleAuthenticationTicket ticket = ticketManager.get(token.getTicket(), SimpleAuthenticationTicket.class);

        if (ticket == null) {
            throw new TicketNotFoundException("找不到 " + token.getTicket() + " 票据");
        }

        Organization organization = organizationManager.getByWildcard(token.getRedirectUrl());

        lock.lock();

        try {

            if (ticket.isExpired()) {
                throw new UrlTicketValidateException(token.getTicket() + " 票据超时");
            }

            if (!StringUtils.equals(ticket.getOrganization().getWildcard(), organization.getWildcard())) {
                throw new UrlTicketValidateException(token.getTicket() + " 票据的机构匹配");
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
            RememberMeAuthenticationToken t = (RememberMeAuthenticationToken)authenticationToken;
            result.put(rememberMeAttributeKeyName, t.isRememberMe());
        }

        return result;
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
