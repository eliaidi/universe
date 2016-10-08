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
