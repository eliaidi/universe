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

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * shiro redis 缓存实体
 *
 * @author maurice
 */
@SuppressWarnings("unchecked")
public class SpringDataRedisCache implements Cache<Object, Object> {

    // spring redis cache 实体
    private RedisCache cache;
    // spring data redis template
    private final RedisTemplate template;
    // cache 名称
    private final String name;

    /**
     * shiro redis 缓存实体
     *
     * @param cache spring data redis cache 类
     */
    public SpringDataRedisCache(RedisCache cache) {
        this.cache = cache;
        this.template = (RedisTemplate) cache.getNativeCache();
        this.name = cache.getName();
    }

    /**
     * 获取 key 前缀
     *
     * @return 前缀名
     */
    private String getPrefix() {
        return name + ":";
    }


    @Override
    public Object get(Object key) throws CacheException {

        if (key == null) {
            return null;
        }

        try {
            org.springframework.cache.Cache.ValueWrapper valueWrapper = cache.get(key);

            if (valueWrapper == null) {
                return null;
            }

            return valueWrapper.get();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        if (key == null) {
            return null;
        }
        try {
            Object previous = get(key);
            cache.put(key, value);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Object remove(Object key) throws CacheException {

        if (key == null) {
            return null;
        }

        try {
            Object value = get(key);
            cache.evict(key);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        try {
            cache.clear();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        try {
            return keys().size();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Set<Object> keys() {
        try {
            Set<Object> keys = template.keys(name  + "*");
            HashSet<Object> hashSet = new LinkedHashSet<Object>();
            if (!CollectionUtils.isEmpty(keys)) {
                for (Object key : keys) {
                    hashSet.add(key.toString().replaceAll(getPrefix(), ""));
                }
                return Collections.unmodifiableSet(hashSet);
            } else {
                return Collections.emptySet();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }

    }

    @Override
    public Collection<Object> values() {
        try {

            Set<Object> keys = keys();

            if (!CollectionUtils.isEmpty(keys)) {
                List<Object> values = new ArrayList<Object>(keys.size());

                for (Object key : keys) {

                    key = key.toString().replaceAll(getPrefix(), "");
                    values.add(get(key));
                }

                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }

        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public String toString() {
        return "redis cache " + name + " (" + size() + " entries )";
    }
}
