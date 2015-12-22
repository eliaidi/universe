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

package com.github.dactiv.universe.sso.server.validation.policy.support;

import com.github.dactiv.universe.sso.server.ticket.entity.Ticket;
import com.github.dactiv.universe.sso.server.validation.policy.ExpirationPolicy;

import java.util.concurrent.TimeUnit;

/**
 * 认证票据超时策略
 *
 * @author maurice
 */
public class AuthenticationTicketExpirationPolicy implements ExpirationPolicy {

    /**
     * 默认票据的最大存活时间
     */
    public final static long DEFAULT_MAX_TIME_TO_LIVE_IN_MILLI_SECONDS = 28800;
    /**
     * 默认清除票据的时间
     */
    public final static long DEFAULT_TIME_TO_KILL_IN_MILLI_SECONDS = 7200;

    // 票据的最大存活时间
    private long maxTimeToLiveInMilliSeconds = DEFAULT_MAX_TIME_TO_LIVE_IN_MILLI_SECONDS;
    // 清除票据的时间
    private long timeToKillInMilliSeconds = DEFAULT_TIME_TO_KILL_IN_MILLI_SECONDS;

    /**
     * 认证票据超时策略
     */
    public AuthenticationTicketExpirationPolicy() {
        this(DEFAULT_TIME_TO_KILL_IN_MILLI_SECONDS, DEFAULT_MAX_TIME_TO_LIVE_IN_MILLI_SECONDS);
    }

    /**
     * 认证票据超时策略
     *
     * @param maxTimeToLiveInMilliSeconds 票据的最大存活时间
     * @param timeToKillInMilliSeconds    清除票据的时间
     */
    public AuthenticationTicketExpirationPolicy(long timeToKillInMilliSeconds, long maxTimeToLiveInMilliSeconds) {
        this(timeToKillInMilliSeconds, maxTimeToLiveInMilliSeconds, TimeUnit.SECONDS);
    }

    /**
     * 认证票据超时策略
     *
     * @param maxTimeToLiveInMilliSeconds 票据的最大存活时间
     * @param timeToKillInMilliSeconds    清除票据的时间
     * @param timeUnit                    时间单位
     */
    public AuthenticationTicketExpirationPolicy(long maxTimeToLiveInMilliSeconds, long timeToKillInMilliSeconds, TimeUnit timeUnit) {
        this.maxTimeToLiveInMilliSeconds = timeUnit.toMillis(maxTimeToLiveInMilliSeconds);
        this.timeToKillInMilliSeconds = timeUnit.toMillis(timeToKillInMilliSeconds);
    }

    /**
     * 是否超时
     *
     * @param ticket 票据信息
     * @return true 表示是，否则表示 false
     */
    @Override
    public boolean isExpired(Ticket ticket) {
        final long currentSystemTimeInMillis = System.currentTimeMillis();

        if ((currentSystemTimeInMillis - ticket.getCreationTime().getTime() >= maxTimeToLiveInMilliSeconds)) {
            return Boolean.TRUE;
        }

        if ((currentSystemTimeInMillis - ticket.getLastTimeUsed().getTime() >= timeToKillInMilliSeconds)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}
