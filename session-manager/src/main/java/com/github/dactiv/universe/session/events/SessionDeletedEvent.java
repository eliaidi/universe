package com.github.dactiv.universe.session.events;

import com.github.dactiv.universe.session.Session;

/**
 * 删除 session 事件源
 *
 * @author maurice
 */
public class SessionDeletedEvent extends SessionDestroyedEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 删除 session 事件源
     *
     * @param source    事件源
     * @param sessionId session id
     */
    public SessionDeletedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }

    /**
     * 删除 session 事件源
     *
     * @param source  事件源
     * @param session session
     */
    public SessionDeletedEvent(Object source, Session session) {
        super(source, session);
    }
}
