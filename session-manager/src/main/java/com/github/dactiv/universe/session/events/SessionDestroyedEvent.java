package com.github.dactiv.universe.session.events;

import com.github.dactiv.universe.session.Session;

/**
 * 销毁 session 事件源
 *
 * @author maurice
 */
public class SessionDestroyedEvent extends AbstractSessionEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 销毁 session 事件源
     *
     * @param source    事件源
     * @param sessionId session id
     */
    public SessionDestroyedEvent(Object source, String sessionId) {
        super(source, sessionId);
    }

    /**
     * 销毁 session 事件源
     *
     * @param source  事件源
     * @param session session
     */
    public SessionDestroyedEvent(Object source, Session session) {
        super(source, session);
    }
}
