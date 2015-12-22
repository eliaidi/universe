package com.github.dactiv.universe.shiro.test;

import com.github.dactiv.universe.shiro.cache.redis.SpringDataRedisCache;
import com.github.dactiv.universe.shiro.cache.redis.SpringDataRedisCacheManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * 单元测试 Redis Cache
 *
 * @author maurice
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-core.xml")
public class SpringDataRedisCacheTest {

    @Autowired
    @Qualifier("shiroCacheManager")
    private SpringDataRedisCacheManager springDataRedisCacheManager;

    private SpringDataRedisCache springDataRedisCache;
    private SpringDataRedisCache otherCache;

    @Before
    public void install() {
        otherCache = (SpringDataRedisCache) springDataRedisCacheManager.getCache("otherTestCache");
        springDataRedisCache = (SpringDataRedisCache) springDataRedisCacheManager.getCache("redisTestCache");
        springDataRedisCache.clear();
        otherCache.clear();
    }

    @Test
    public void testAll() throws Exception {
        Object o = springDataRedisCache.get("someKey");
        assertNull(o);

        o = springDataRedisCache.put("someKey","someValue");
        assertNull(o);
        assertEquals(springDataRedisCache.size(), 1);
        assertEquals(springDataRedisCache.values().size(), 1);

        o = springDataRedisCache.get("someKey");
        assertEquals(o, "someValue");

        o = springDataRedisCache.remove("someKey");
        assertEquals(o,"someValue");
        assertEquals(springDataRedisCache.size(), 0);

        springDataRedisCache.put("someKey1","someValue");
        springDataRedisCache.put("someKey2","someValue");
        springDataRedisCache.put("someKey3","someValue");

        assertEquals(springDataRedisCache.keys().size(), 3);

        otherCache.put("otherKey","otherValue");

        springDataRedisCache.clear();

        assertEquals(springDataRedisCache.size(), 0);
        assertEquals(otherCache.size(), 1);

        otherCache.clear();
        assertEquals(otherCache.size(), 0);
    }

}
