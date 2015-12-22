package com.github.dactiv.universe.sso.server.validation.policy;

import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;

import java.io.Serializable;

/**
 * 超时政策接口
 *
 * @author maurice
 */
public interface ExpirationPolicy extends Serializable {

    /**
     * 是否超时
     *
     * @param ticket 票据信息
     * @return true 表示是，否则表示 false
     */
    boolean isExpired(Ticket ticket);

}
