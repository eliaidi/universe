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
