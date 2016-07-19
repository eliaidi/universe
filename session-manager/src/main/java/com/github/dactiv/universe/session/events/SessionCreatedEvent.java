package com.github.dactiv.universe.session.events;


import com.github.dactiv.universe.session.Session;

/**
 * 创建 session 事件源
 *
 * @author maurice
 */
public class SessionCreatedEvent extends SessionDestroyedEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 创建 session 事件源
     *
     * @param source    事件源
     * @param sessionId session id
     */
    public SessionCreatedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }

    /**
     * 创建 session 事件源
     *
     * @param source  事件源
     * @param session session
     */
    public SessionCreatedEvent(Object source, Session session) {
        super(source, session);
    }
}
