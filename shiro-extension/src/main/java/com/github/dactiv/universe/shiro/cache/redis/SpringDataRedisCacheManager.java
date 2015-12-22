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