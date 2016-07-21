package com.github.dactiv.universe.session.respository.redis;


import com.github.dactiv.universe.session.ExpiringSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis session 超时政策，用于管理 session 的超时操作
 *
 * @author maurice
 */
public class RedisSessionExpirationPolicy {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisSessionExpirationPolicy.class);
    // redis 操作类
    private final RedisOperations<Object, Object> redis;

    // redis sesssion 存储库
    private final RedisSessionRepository redisSession;

    /**
     * redis session 供应者
     *
     * @param redisOperations  redis 操作类
     * @param redisSession redis session 供应者
     */
    RedisSessionExpirationPolicy(RedisOperations<Object, Object> redisOperations, RedisSessionRepository redisSession) {
        this.redis = redisOperations;
        this.redisSession = redisSession;
    }

    /**
     * 当删除 session 时，触发该方法
     *
     * @param session 被删除的 session
     */
    public void onDelete(ExpiringSession session) {
        long toExpire = roundUpToNextMinute(expiresInMillis(session));
        String expireKey = getExpirationKey(toExpire);
        this.redis.boundSetOps(expireKey).remove(session.getId());
    }

    /**
     * 当超时时间更新时，触发该方法
     *
     * @param originalExpirationTimeInMilli 原始超时时间的分钟时间戳
     * @param session session
     */
    public void onExpirationUpdated(Long originalExpirationTimeInMilli, ExpiringSession session) {

        String keyToExpire = "expires:" + session.getId();
        long toExpire = roundUpToNextMinute(expiresInMillis(session));

        if (originalExpirationTimeInMilli != null) {
            long originalRoundedUp = roundUpToNextMinute(originalExpirationTimeInMilli);
            if (toExpire != originalRoundedUp) {
                String expireKey = getExpirationKey(originalRoundedUp);
                this.redis.boundSetOps(expireKey).remove(keyToExpire);
            }
        }

        String expireKey = getExpirationKey(toExpire);
        BoundSetOperations<Object, Object> expireOperations = this.redis.boundSetOps(expireKey);
        expireOperations.add(keyToExpire);

        long sessionExpireInSeconds = session.getMaxInactiveIntervalInSeconds();
        long fiveMinutesAfterExpires = sessionExpireInSeconds + TimeUnit.MINUTES.toSeconds(5);
        String sessionKey = getSessionKey(keyToExpire);

        expireOperations.expire(fiveMinutesAfterExpires, TimeUnit.SECONDS);
        if (sessionExpireInSeconds == 0) {
            this.redis.delete(sessionKey);
        } else {
            this.redis.boundValueOps(sessionKey).append("");
            this.redis.boundValueOps(sessionKey).expire(sessionExpireInSeconds,TimeUnit.SECONDS);
        }
        this.redis.boundHashOps(getSessionKey(session.getId())).expire(fiveMinutesAfterExpires, TimeUnit.SECONDS);
    }

    /**
     * 获取超时时间的 key 值
     *
     * @param expires 时间戳
     *
     * @return redis 超时时间的 key 值
     */
    String getExpirationKey(long expires) {
        return this.redisSession.getExpirationsKey(expires);
    }

    /**
     * 通过 session id 获取 session 存在 redis 的 key 值
     *
     * @param sessionId session id
     *
     * @return session redis key
     */
    String getSessionKey(String sessionId) {
        return this.redisSession.getSessionKey(sessionId);
    }

    /**
     * 清除超时的 session
     */
    public void cleanExpiredSessions() {
        long now = System.currentTimeMillis();
        // 获取当前时间的分钟之间戳
        long prevMin = roundDownMinute(now);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(new Date(prevMin) + " clean session");
        }

        String expirationKey = getExpirationKey(prevMin);
        Set<Object> sessionsToExpire = this.redis.boundSetOps(expirationKey).members();
        this.redis.delete(expirationKey);
        for (Object session : sessionsToExpire) {
            String sessionKey = getSessionKey((String) session);

            this.redis.hasKey(sessionKey);
        }
    }

    /**
     * 获取 session 的超时时间戳
     *
     * @param session session
     *
     * @return 时间戳
     */
    private static long expiresInMillis(ExpiringSession session) {
        int maxInactiveInSeconds = session.getMaxInactiveIntervalInSeconds();
        long lastAccessedTimeInMillis = session.getLastAccessedTime();
        return lastAccessedTimeInMillis + TimeUnit.SECONDS.toMillis(maxInactiveInSeconds);
    }

    /**
     * 通过时间(ms)求出下一分钟的时间戳
     *
     * @param timeInMs 时间戳
     *
     * @return 时间戳
     */
    private static long roundUpToNextMinute(long timeInMs) {

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timeInMs);
        date.add(Calendar.MINUTE, 1);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MILLISECOND);
        return date.getTimeInMillis();
    }

    /**
     * 通过时间(ms)求出去处秒和毫秒值的分钟数
     *
     * @param timeInMs 时间戳
     *
     * @return 时间戳
     */
    private static long roundDownMinute(long timeInMs) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timeInMs);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MILLISECOND);
        return date.getTimeInMillis();
    }
}
