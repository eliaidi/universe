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
