package com.github.dactiv.universe.session.events;

import com.github.dactiv.universe.session.Session;
import org.springframework.context.ApplicationEvent;

/**
 * 抽象 session 事件源
 *
 * @author maurice
 */
public class AbstractSessionEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    // session id
	private final String sessionId;
    // session
    private final Session session;

    /**
     * 抽象 session 事件源
     *
     * @param source 事件源
     * @param sessionId session id
     */
    protected AbstractSessionEvent(Object source, String sessionId) {
        super(source);
        this.sessionId = sessionId;
        this.session = null;
    }

    /**
     * 抽象 session 事件源
     * @param source 事件源
     * @param session session
     */
    AbstractSessionEvent(Object source, Session session) {
        super(source);
        this.session = session;
        this.sessionId = session.getId();
    }

    /**
     * 获取 session
     *
     * @param <S> session
     *
     * @return session
     */
    @SuppressWarnings("unchecked")
    public <S extends Session> S getSession() {
        return (S) this.session;
    }

    /**
     * 获取 session id
     *
     * @return session id
     */
    public String getSessionId() {
        return this.sessionId;
    }
}
