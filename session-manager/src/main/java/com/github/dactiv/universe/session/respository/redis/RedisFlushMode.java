package com.github.dactiv.universe.session.respository.redis;

/**
 *
 * 指定在哪种操作下才写入 redis
 *
 * @author maurice
 */
public enum  RedisFlushMode {

    /**
     * 当调用 session 时候写入，这种在 http 调用完成后会存在写入操作
     */
    ON_SAVE,

    /**
     * 尽早的去写入 redis，如:在{@link com.github.dactiv.universe.session.SessionRepository#createSession()}时，
     * 就写入 redis，其他的类似 setAttribute 也同时写如 redis
     */
    IMMEDIATE
}
