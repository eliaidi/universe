package com.github.dactiv.universe.session.events;

import com.github.dactiv.universe.session.Session;

/**
 * 超时 session 事件源
 * @author maurice
 */
public class SessionExpiredEvent extends SessionDestroyedEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 超时 session 事件源
     *
     * @param source    事件源
     * @param sessionId session id
     */
    public SessionExpiredEvent(Object source, String sessionId) {
        super(source, sessionId);
    }

    /**
     * 超时 session 事件源
     *
     * @param source  事件源
     * @param session session
     */
    public SessionExpiredEvent(Object source, Session session) {
        super(source, session);
    }

}
