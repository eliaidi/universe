package com.github.dactiv.universe.session.respository.redis;

import com.github.dactiv.universe.session.ExpiringSession;
import com.github.dactiv.universe.session.SessionRepository;
import com.github.dactiv.universe.session.events.SessionCreatedEvent;
import com.github.dactiv.universe.session.events.SessionDeletedEvent;
import com.github.dactiv.universe.session.events.SessionExpiredEvent;
import com.github.dactiv.universe.session.respository.map.MapSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis session 存储库
 *
 * @author maurice
 */
public class RedisSessionRepository implements SessionRepository<RedisSessionRepository.RedisSession>, MessageListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisSessionRepository.class);
    /**
     * 默认存在 redis 的 session key 名称
     */
    public static final String DEFAULT_SESSION_REDIS_PREFIX = "redis:session:";
    /**
     * 创建时间的 session 属性
     */
    public static final String CREATION_TIME_ATTR = "creationTime";
    /**
     * 最大无效区间
     */
    public static final String MAX_INACTIVE_ATTR = "maxInactiveInterval";
    /**
     * 最后访问时间的 session 属性
     */
    public static final String LAST_ACCESSED_ATTR = "lastAccessedTime";
    /**
     * session 属性前缀
     */
    public static final String SESSION_ATTR_PREFIX = "sessionAttr:";
    // redis 的 session key 名称
    private String keyPrefix = DEFAULT_SESSION_REDIS_PREFIX;
    // redis 操作类
    private final RedisOperations<Object, Object> sessionRedisOperations;
    // redis session 超时供应者
    private RedisSessionExpirationPolicy expirationPolicy;
    // 默认最大无效区间
    private Integer defaultMaxInactiveInterval;
    // redis 序列化操作
    private RedisSerializer<Object> defaultSerializer = new JdkSerializationRedisSerializer();
    // 指定在哪种操作下才写入 redis
    private RedisFlushMode redisFlushMode = RedisFlushMode.ON_SAVE;
    // redis 事件发布者
    private ApplicationEventPublisher eventPublisher = new ApplicationEventPublisher() {
        public void publishEvent(ApplicationEvent event) {
        }

        public void publishEvent(Object event) {
        }
    };

    /**
     * redis session 存储库
     *
     * @param redisConnectionFactory redis 链接工厂
     */
    public RedisSessionRepository(RedisConnectionFactory redisConnectionFactory) {
        this(createDefaultTemplate(redisConnectionFactory));
    }

    /**
     * redis session 存储库
     *
     * @param sessionRedisOperations redis 操作类
     */
    public RedisSessionRepository(RedisOperations<Object, Object> sessionRedisOperations) {
        Assert.notNull(sessionRedisOperations, "sessionRedisOperations 不能为 null");
        this.sessionRedisOperations = sessionRedisOperations;
        this.expirationPolicy = new RedisSessionExpirationPolicy(sessionRedisOperations,this);
    }

    /**
     * 设置 redis 事件发布者
     * @param applicationEventPublisher reids 事件发布者
     */
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        Assert.notNull(applicationEventPublisher,"applicationEventPublisher 不能为 null");
        this.eventPublisher = applicationEventPublisher;
    }

    /**
     * 设置默认最大无效区间
     *
     * @param defaultMaxInactiveInterval 无效区间
     */
    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    /**
     * 设置 redis 序列化操作
     *
     * @param defaultSerializer redis 序列化操作
     */
    public void setDefaultSerializer(RedisSerializer<Object> defaultSerializer) {
        Assert.notNull(defaultSerializer, "defaultSerializer 不能为 null");
        this.defaultSerializer = defaultSerializer;
    }

    /**
     * 设置指定在哪种操作下才写入 redis
     *
     * @param redisFlushMode 枚举类型
     */
    public void setRedisFlushMode(RedisFlushMode redisFlushMode) {
        Assert.notNull(redisFlushMode, "redisFlushMode 不能为 null");
        this.redisFlushMode = redisFlushMode;
    }

    /**
     * 设置 redis session 超时供应者
     *
     * @param expirationPolicy 超时供应者
     */
    public void setExpirationPolicy(RedisSessionExpirationPolicy expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
    }

    /**
     * 创建 redis 操作模板
     *
     * @param connectionFactory redis 链接工厂
     *
     * @return redis 操作模板
     */
    private static RedisTemplate<Object, Object> createDefaultTemplate(RedisConnectionFactory connectionFactory) {
        Assert.notNull(connectionFactory, "connectionFactory 不能为 null");
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建 session
     *
     * @return session
     */
    @Override
    public RedisSession createSession() {
        RedisSession redisSession = new RedisSession();
        if (this.defaultMaxInactiveInterval != null) {
            redisSession.setMaxInactiveIntervalInSeconds(this.defaultMaxInactiveInterval);
        }
        return redisSession;
    }

    /**
     * 保存 session
     *
     * @param session session 接口
     */
    @Override
    public void save(RedisSession session) {
        session.saveDelta();
        if (session.isNew()) {
            String sessionCreatedKey = getSessionCreatedChannel(session.getId());
            this.sessionRedisOperations.convertAndSend(sessionCreatedKey, session.delta);
            session.setNew(false);
        }
    }

    /**
     * 通过 session id 获取在 redis 创建 session 的 key 名称
     *
     * @param sessionId session id
     *
     * @return 在 redis 创建 session 的 key 名称
     */
    private String getSessionCreatedChannel(String sessionId) {
        return getSessionCreatedChannelPrefix() + sessionId;
    }

    /**
     * 获取 redis 创建 session 的 key 名称
     *
     * @return redis 创建 session 的 key 名称
     */
    public String getSessionCreatedChannelPrefix() {
        return this.keyPrefix + "event:created:";
    }

    /**
     * 定时调度，在一分钟内，让超时供应者去清除超时的 session
     */
    @Scheduled(cron = "0 * * * * *")
    public void cleanupExpiredSessions() {
        this.expirationPolicy.cleanExpiredSessions();
    }

    /**
     * 获取 session
     *
     * @param id session id
     *
     * @return session
     */
    @Override
    public RedisSession getSession(String id) {
        return getSession(id, false);
    }

    /**
     * 通过 id 获取 session
     *
     * @param id session id
     * @param allowExpired 是否支持获取超时的 session，true 为是，否则 false
     *
     * @return
     */
    private RedisSession getSession(String id, boolean allowExpired) {
        Map<Object, Object> entries = getSessionBoundHashOperations(id).entries();
        if (entries.isEmpty()) {
            return null;
        }
        MapSession loaded = loadSession(id, entries);
        if (!allowExpired && loaded.isExpired()) {
            return null;
        }
        RedisSession result = new RedisSession(loaded);
        result.originalLastAccessTime = loaded.getLastAccessedTime();
        return result;
    }

    /**
     * 加载 session
     *
     * @param id session id
     * @param entries session 里的原始属性值
     *
     * @return session
     */
    private MapSession loadSession(String id, Map<Object, Object> entries) {
        MapSession loaded = new MapSession(id);
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            String key = (String) entry.getKey();
            if (CREATION_TIME_ATTR.equals(key)) {
                loaded.setCreationTime((Long) entry.getValue());
            } else if (MAX_INACTIVE_ATTR.equals(key)) {
                loaded.setMaxInactiveIntervalInSeconds((Integer) entry.getValue());
            } else if (LAST_ACCESSED_ATTR.equals(key)) {
                loaded.setLastAccessedTime((Long) entry.getValue());
            } else if (key.startsWith(SESSION_ATTR_PREFIX)) {
                loaded.setAttribute(key.substring(SESSION_ATTR_PREFIX.length()), entry.getValue());
            }
        }
        return loaded;
    }

    /**
     * 删除 session
     *
     * @param id session id
     */
    @Override
    public void delete(String id) {
        RedisSession session = getSession(id, true);
        if (session == null) {
            return;
        }

        this.expirationPolicy.onDelete(session);

        String expireKey = getExpiredKey(session.getId());
        this.sessionRedisOperations.delete(expireKey);

        session.setMaxInactiveIntervalInSeconds(0);
        save(session);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message, byte[] pattern) {
        byte[] messageChannel = message.getChannel();
        byte[] messageBody = message.getBody();
        if (messageChannel == null || messageBody == null) {
            return;
        }

        String channel = new String(messageChannel);

        if (channel.startsWith(getSessionCreatedChannelPrefix())) {
            Map<Object, Object> loaded = (Map<Object, Object>) this.defaultSerializer.deserialize(message.getBody());
            handleCreated(loaded, channel);
            return;
        }

        String body = new String(messageBody);
        if (!body.startsWith(getExpiredKeyPrefix())) {
            return;
        }

        boolean isDeleted = channel.endsWith(":del");
        if (isDeleted || channel.endsWith(":expired")) {
            int beginIndex = body.lastIndexOf(":") + 1;
            int endIndex = body.length();
            String sessionId = body.substring(beginIndex, endIndex);

            RedisSession session = getSession(sessionId, true);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("为ID为 " + sessionId + "的 session 推送销毁操作");
            }

            if (isDeleted) {
                handleDeleted(sessionId, session);
            } else {
                handleExpired(sessionId, session);
            }

        }
    }

    /**
     * onMessage 响应删除 session 的的句柄，推送删除消息
     *
     * @param sessionId session id
     * @param session session 对象
     */
    private void handleDeleted(String sessionId, RedisSession session) {
        if (session == null) {
            publishEvent(new SessionDeletedEvent(this, sessionId));
        } else {
            publishEvent(new SessionDeletedEvent(this, session));
        }
    }

    /**
     * onMessage 响应创建 session 的的句柄，推送创建消息
     *
     * @param loaded redis session 属性
     * @param channel redis 创建 session 的 key 名称
     */
    public void handleCreated(Map<Object, Object> loaded, String channel) {
        String id = channel.substring(channel.lastIndexOf(":") + 1);
        ExpiringSession session = loadSession(id, loaded);
        publishEvent(new SessionCreatedEvent(this, session));
    }

    /**
     * onMessage 响应超时 session 的的句柄，推送创建消息
     *
     * @param session session id
     * @param session session 对象
     */
    private void handleExpired(String sessionId, RedisSession session) {
        if (session == null) {
            publishEvent(new SessionExpiredEvent(this, sessionId));
        } else {
            publishEvent(new SessionExpiredEvent(this, session));
        }
    }

    /**
     * 推送消息
     *
     * @param event 事件源
     */
    private void publishEvent(ApplicationEvent event) {
        try {
            this.eventPublisher.publishEvent(event);
        } catch (Throwable ex) {
            LOGGER.error("推送 " + event + "事件错误.", ex);
        }
    }

    /**
     * 通过 session id 获取 redis 的 key 名称
     *
     * @param sessionId session id
     *
     * @return key 名称
     */
    String getSessionKey(String sessionId) {
        return this.keyPrefix + sessionId;
    }

    /**
     * 通过属性名称获取存储在 redis 的基础属性的 key 名称
     *
     * @param attributeName key 名称
     *
     * @return key 名称
     */
    public static String getSessionAttrNameKey(String attributeName) {
        return SESSION_ATTR_PREFIX + attributeName;
    }

    /**
     * 通过 session id 获取存储在 redis 的超时 key 名称
     *
     * @param sessionId session id
     *
     * @return key 名称
     */
    private String getExpiredKey(String sessionId) {
        return getExpiredKeyPrefix() + sessionId;
    }

    /**
     * 获取 redis 存储超时 session key 的前缀名称
     * @return
     */
    private String getExpiredKeyPrefix() {
        return this.keyPrefix + "expires:";
    }

    /**
     * 通过时间戳获取管理超时 session 的 key 名称
     *
     * @param expiration 时间戳
     *
     * @return key 名称
     */
    String getExpirationsKey(long expiration) {
        return this.keyPrefix + "expirations:" + expiration;
    }

    /**
     * 通过 session id 获取该 session 下的 redis hash 操作
     *
     * @param sessionId sesion id
     *
     * @return redis 相关 hash 的类
     */
    private BoundHashOperations<Object, Object, Object> getSessionBoundHashOperations(String sessionId) {
        String key = getSessionKey(sessionId);
        return this.sessionRedisOperations.boundHashOps(key);
    }

    /**
     *  redis 的 session 实现
     */
    final class RedisSession implements ExpiringSession {
        // 缓存值
        private final MapSession cached;
        // 原始最后访问时间
        private Long originalLastAccessTime;
        // 一个变量增量，用于存在一些数据，但要和缓存分离
        private Map<String, Object> delta = new HashMap<String, Object>();
        // 是否为新的 session，true 为是，否则 false
        private boolean isNew;

        /**
         * redis 的 session 实现
         */
        RedisSession() {
            this(new MapSession());
            this.delta.put(CREATION_TIME_ATTR, getCreationTime());
            this.delta.put(MAX_INACTIVE_ATTR, getMaxInactiveIntervalInSeconds());
            this.delta.put(LAST_ACCESSED_ATTR, getLastAccessedTime());
            this.isNew = true;
            flushImmediateIfNecessary();
        }

        /**
         * 缓存值
         *
         * @param cached redis 的 session 实现
         */
        RedisSession(MapSession cached) {
            Assert.notNull("MapSession 不能 null");
            this.cached = cached;
        }

        /**
         * 保存 session 信息
         */
        private void flushImmediateIfNecessary() {
            if (RedisSessionRepository.this.redisFlushMode == RedisFlushMode.IMMEDIATE) {
                saveDelta();
            }
        }

        /**
         * 是否为新的 session
         *
         * @return true 为是，否则 false
         */
        public boolean isNew() {
            return this.isNew;
        }

        /**
         * 是否为新的 session
         *
         * @param isNew true 为是，否则 false
         */
        public void setNew(boolean isNew) {
            this.isNew = isNew;
        }

        /**
         * 获取创建时间
         *
         * @return 创建时间戳
         */
        @Override
        public long getCreationTime() {
            return this.cached.getCreationTime();
        }
        /**
         * 设置最后访问时间
         *
         * @param lastAccessedTime 最后访问创建时间戳
         */
        @Override
        public void setLastAccessedTime(long lastAccessedTime) {
            this.cached.setLastAccessedTime(lastAccessedTime);
            this.delta.put(LAST_ACCESSED_ATTR, getLastAccessedTime());
            flushImmediateIfNecessary();
        }

        /**
         * 保存变量增量
         */
        private void saveDelta() {
            if (this.delta.isEmpty()) {
                return;
            }
            String sessionId = getId();
            getSessionBoundHashOperations(sessionId).putAll(this.delta);

            this.delta = new HashMap<String, Object>(this.delta.size());

            Long originalExpiration = this.originalLastAccessTime == null ? null
                    : this.originalLastAccessTime + TimeUnit.SECONDS
                    .toMillis(getMaxInactiveIntervalInSeconds());

            RedisSessionRepository.this.expirationPolicy.onExpirationUpdated(originalExpiration, this);
        }

        /**
         * 获取最后访问时间
         *
         * @return 最后访问创建时间戳
         */
        @Override
        public long getLastAccessedTime() {
            return this.cached.getLastAccessedTime();
        }

        /**
         * 设置被请求之间最大的不活跃的时间间隔(以秒为单位)
         *
         * @param interval 如果为负数，就永远不超时
         */
        @Override
        public void setMaxInactiveIntervalInSeconds(int interval) {
            this.cached.setMaxInactiveIntervalInSeconds(interval);
            this.delta.put(MAX_INACTIVE_ATTR, getMaxInactiveIntervalInSeconds());
            flushImmediateIfNecessary();
        }

        /**
         * 获取被请求之间最大的不活跃的时间间隔(以秒为单位)，如果为负数，就永远不超时
         *
         * @return 时间
         */
        @Override
        public int getMaxInactiveIntervalInSeconds() {
            return this.cached.getMaxInactiveIntervalInSeconds();
        }

        /**
         * 判断是否超时
         *
         * @return true 表示是，否则 false
         */
        @Override
        public boolean isExpired() {
            return this.cached.isExpired();
        }

        /**
         * 获取 id
         *
         * @return session id 值
         */
        @Override
        public String getId() {
            return this.cached.getId();
        }

        /**
         * 获取属性
         *
         * @param attributeName 属性名称
         *
         * @return 属性值
         */
        @Override
        public <T> T getAttribute(String attributeName) {
            return this.cached.getAttribute(attributeName);
        }

        /**
         * 获取所有属性名称
         *
         * @return 所有属性名称
         */
        @Override
        public Set<String> getAttributeNames() {
            return this.cached.getAttributeNames();
        }

        /**
         * 设置属性
         *
         * @param attributeName 属性名称
         * @param attributeValue 属性值
         */
        @Override
        public void setAttribute(String attributeName, Object attributeValue) {
            this.cached.setAttribute(attributeName, attributeValue);
            this.delta.put(getSessionAttrNameKey(attributeName), attributeValue);
            flushImmediateIfNecessary();
        }

        /**
         * 删除属性
         *
         * @param attributeName 属性名称
         */
        @Override
        public void removeAttribute(String attributeName) {
            this.cached.removeAttribute(attributeName);
            this.delta.put(getSessionAttrNameKey(attributeName), null);
            flushImmediateIfNecessary();
        }
    }
}
