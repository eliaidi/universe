/*
 * Copyright 2016 dactiv
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
package com.github.dactiv.universe.sso.server.ticket.entity.support;

/**
 * 记录 ip 和 session id 的票据实现
 *
 * @author maurice
 */
public class IpSessionTicket extends SimpleTicket{

    // 当前注册的 ip 地址
    private String ipAddress;

    // 当前的 session id
    private String sessionId;

    /**
     * 记录 ip 和 session id 的票据实现
     *
     * @param id id 值
     * @param ipAddress ip 值
     * @param sessionId session id 值
     */
    public IpSessionTicket(Object id, String ipAddress, String sessionId) {
        super(id);
        this.ipAddress = ipAddress;
        this.sessionId = sessionId;
    }

    /**
     * 获取 ip 地址
     *
     * @return ip 地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 获取 session id
     *
     * @return session id
     */
    public String getSessionId() {
        return sessionId;
    }
}
