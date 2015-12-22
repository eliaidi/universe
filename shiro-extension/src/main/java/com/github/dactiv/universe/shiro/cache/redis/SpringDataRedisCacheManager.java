package com.github.dactiv.universe.shiro.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * shiro redis 缓存管理
 *
 * @author maurice
 */
public class SpringDataRedisCacheManager implements CacheManager {

    // spring data redis manager
    private RedisCacheManager cacheManager;

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        RedisCache cache = (RedisCache) cacheManager.getCache(name);
        return (Cache<K, V>) new SpringDataRedisCache(cache);
    }

    /**
     * 设置 spring data redis cache manager
     *
     * @param cacheManager redis cache manager
     */
    public void setCacheManager(org.springframework.data.redis.cache.RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * 获取 spring data redis cache manager
     *
     * @return redis cache manager
     */
    public org.springframework.data.redis.cache.RedisCacheManager getCacheManager() {
        return cacheManager;
    }

}