package com.github.dactiv.universe.sso.server.ticket.support;

import com.github.dactiv.universe.sso.server.ticket.generator.NumericGenerator;
import com.github.dactiv.universe.sso.server.ticket.generator.RandomStringGenerator;
import com.github.dactiv.universe.sso.server.ticket.generator.support.SimpleNumericGenerator;
import com.github.dactiv.universe.sso.server.validation.support.UrlTicketValidationManager;
import com.github.dactiv.universe.sso.server.organization.OrganizationManager;
import com.github.dactiv.universe.sso.server.organization.entity.Organization;
import com.github.dactiv.universe.sso.server.ticket.TicketManager;
import com.github.dactiv.universe.sso.server.ticket.entity.AuthenticationTicket;
import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;
import com.github.dactiv.universe.sso.server.ticket.entity.support.SimpleAuthenticationTicket;
import com.github.dactiv.universe.sso.server.ticket.generator.support.SimpleRandomStringGenerator;
import com.github.dactiv.universe.sso.server.exception.TicketException;
import com.github.dactiv.universe.sso.server.validation.TicketValidationManager;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * 简单的票据管理实现
 *
 * @author maurice
 */
public class CacheTicketManager implements TicketManager, InitializingBean {
    /**
     * 默认票据缓存名称
     */
    public final static String DEFAULT_TICKET_CACHE_NAME = "shiroSsoTickCache";
    // 序列生成器
    private NumericGenerator numericGenerator;
    // 随机字符串生成器
    private RandomStringGenerator stringGenerator;
    // 机构管理
    private OrganizationManager organizationManager;
    // 缓存管理
    private CacheManager cacheManager;
    // 票据缓存名称
    private String ticketCacheName = DEFAULT_TICKET_CACHE_NAME;
    // 票据验证器
    private TicketValidationManager ticketValidationManager;

    /**
     * 生成一个新的票据
     *
     * @param prefix 票据前缀
     * @return 票据字符串
     */
    @Override
    public String create(String prefix) {
        return prefix + '-' + numericGenerator.getNextNumberAsString() + '-' + this.stringGenerator.getNewString();
    }

    /**
     * 创建认证票据
     *
     * @param organization 机构识别
     * @param principal    当事人(登录成功后的用户)
     * @param token        认证令牌
     * @return 认证票据
     */
    @Override
    public AuthenticationTicket createAuthenticationTicket(String organization, Object principal, AuthenticationToken token) {
        Organization organization1 = organizationManager.getByWildcard(organization);
        return new SimpleAuthenticationTicket(create("AT"), organization1, principal, token);
    }

    /**
     * 通过票据 id 获取票据
     *
     * @param id          票据 id
     * @param ticketClass 票据 class 类型
     * @return 票据
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Ticket> T get(String id, Class<? extends Ticket> ticketClass) {
        Ticket ticket = getTicketCache().get(id);

        if (ticket != null && !ticketClass.isAssignableFrom(ticket.getClass())) {
            throw new TicketException("id 为:" + id + " 的票据类型不是" + ticketClass + "的类型");
        }

        return (T) ticket;
    }

    /**
     * 验证票据
     *
     * @param requestPairSource 请求信息
     * @return 断言结果
     */
    @Override
    public Map<String, Object> valid(Object requestPairSource) {
        return ticketValidationManager.valid(requestPairSource);
    }

    /**
     * 删除票据
     *
     * @param id 票据 id
     */
    @Override
    public void delete(Object id) {
        getTicketCache().remove(id);
    }

    private Cache<Object, Ticket> getTicketCache() {
        return cacheManager.getCache(ticketCacheName);
    }

    /**
     * 保存票据
     *
     * @param ticket 票据信息
     */
    @Override
    public void save(Ticket ticket) {

        Ticket cacheTicket = getTicketCache().get(ticket.getId());

        if (cacheTicket != null) {
            delete(ticket.getId());
        }

        getTicketCache().put(ticket.getId(), ticket);
    }

    /**
     * 设置序列生成器
     *
     * @param numericGenerator 序列生成器
     */
    public void setNumericGenerator(NumericGenerator numericGenerator) {
        this.numericGenerator = numericGenerator;
    }

    /**
     * 设置字符串生成器
     *
     * @param stringGenerator 字符串生成器
     */
    public void setStringGenerator(RandomStringGenerator stringGenerator) {
        this.stringGenerator = stringGenerator;
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
     * 设置缓存管理
     *
     * @param cacheManager 缓存管理
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * 设置存储票据的缓存名称
     *
     * @param ticketCacheName 名称
     */
    public void setTicketCacheName(String ticketCacheName) {
        this.ticketCacheName = ticketCacheName;
    }

    /**
     * 设置票据验证管理
     *
     * @param ticketValidationManager 票据验证管理
     */
    public void setTicketValidationManager(TicketValidationManager ticketValidationManager) {
        this.ticketValidationManager = ticketValidationManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.ticketValidationManager == null) {

            UrlTicketValidationManager urlTicketValidationManager = new UrlTicketValidationManager();
            urlTicketValidationManager.setOrganizationManager(organizationManager);
            urlTicketValidationManager.setTicketManager(this);

            ticketValidationManager = urlTicketValidationManager;
        }

        if (this.numericGenerator == null) {
            this.numericGenerator = new SimpleNumericGenerator();
        }

        if (this.stringGenerator == null) {
            this.stringGenerator = new SimpleRandomStringGenerator();
        }
    }
}
