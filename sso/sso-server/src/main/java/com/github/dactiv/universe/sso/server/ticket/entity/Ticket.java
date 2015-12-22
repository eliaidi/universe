package com.github.dactiv.universe.sso.server.ticket.entity;


import com.github.dactiv.universe.sso.server.entity.IdEntity;

import java.util.Date;

/**
 * SSO 认证需要的票据接口
 *
 * @author maurice
 */
public interface Ticket extends IdEntity {

    /**
     * 判断是否超时
     *
     * @return true 表示超时，否则 false
     */
    boolean isExpired();

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Date getCreationTime();

    /**
     * 获取该票据使用的次数
     *
     * @return 使用次数
     */
    int getCountOfUses();

    /**
     * 最后一次票据使用时间
     *
     * @return 时间对象
     */
    Date getLastTimeUsed();

    /**
     * 上一次使用票据的时间
     *
     * @return 时间对象
     */
    Date getPreviousTimeUsed();
}
