package com.github.dactiv.universe.sso.server.ticket;

import com.github.dactiv.universe.sso.server.ticket.entity.AuthenticationTicket;
import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;
import com.github.dactiv.universe.sso.server.validation.TicketValidationManager;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 票据管理
 *
 * @author maurice
 */
public interface TicketManager extends TicketValidationManager {

    /**
     * 生成一个新的票据
     *
     * @param prefix 票据前缀
     * @return 票据字符串
     */
    String create(String prefix);

    /**
     * 创建认证票据
     *
     * @param organization 机构识别
     * @param principal    当事人(登录成功后的用户)
     * @param token        认证令牌
     * @return 认证票据
     */
    AuthenticationTicket createAuthenticationTicket(String organization, Object principal, AuthenticationToken token);

    /**
     * 保存票据
     *
     * @param ticket 票据信息
     */
    void save(Ticket ticket);

    /**
     * 通过票据 id 获取票据
     *
     * @param id          票据 id
     * @param ticketClass 票据 class 类型
     * @return 票据
     */
    <T extends Ticket> T get(String id, Class<? extends Ticket> ticketClass);

    /**
     * 删除票据
     *
     * @param id 票据 id
     */
    void delete(Object id);
}
