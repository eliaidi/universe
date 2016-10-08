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
package com.github.dactiv.universe.session.respository.map;

import com.github.dactiv.universe.session.ExpiringSession;
import com.github.dactiv.universe.session.SessionRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * map 形式的 session 存储库
 *
 * @author maurice
 */
public class MapSessionRepository implements SessionRepository<ExpiringSession> {
    // 默认最大超时时间
    private Integer defaultMaxInactiveInterval;
    // session 集合
    private final Map<String, ExpiringSession> sessions;

    /**
     * map 形式的 session 存储库
     */
    public MapSessionRepository() {
        this(new ConcurrentHashMap<String, ExpiringSession>());
    }

    /**
     * map 形式的 session 存储库
     *
     * @param sessions session
     */
    public MapSessionRepository(Map<String, ExpiringSession> sessions) {
        if (sessions == null) {
            throw new IllegalArgumentException("sessions can't be null");
        }
        this.sessions = sessions;
    }

    /**
     * 默认最大超时时间
     *
     * @param defaultMaxInactiveInterval 时间
     */
    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = Integer.valueOf(defaultMaxInactiveInterval);
    }

    /**
     * 保存 session
     *
     * @param session session 接口
     */
    public void save(ExpiringSession session) {
        this.sessions.put(session.getId(), new MapSession(session));
    }

    /**
     * 获取 session
     *
     * @param id session id
     *
     * @return
     */
    public ExpiringSession getSession(String id) {
        ExpiringSession saved = this.sessions.get(id);
        if (saved == null) {
            return null;
        }
        if (saved.isExpired()) {
            delete(saved.getId());
            return null;
        }
        return new MapSession(saved);
    }

    /**
     * 删除 session
     *
     * @param id session id
     */
    public void delete(String id) {
        this.sessions.remove(id);
    }

    /**
     * 创建 session
     *
     * @return 新创建的 session
     */
    public ExpiringSession createSession() {
        ExpiringSession result = new MapSession();
        if (this.defaultMaxInactiveInterval != null) {
            result.setMaxInactiveIntervalInSeconds(this.defaultMaxInactiveInterval);
        }
        return result;
    }


}
