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
package com.github.dactiv.universe.session;

/**
 *
 * Session 存储库接口，用于做 Session 的 CURD 操作
 *
 * @author maurice
 */
public interface SessionRepository<S extends Session> {

    /**
     * 创建 session
     *
     * @return session
     */
    S createSession();

    /**
     * 保存 session
     *
     * @param session session 接口
     */
    void save(S session);

    /**
     * 获取 session
     *
     * @param id session id
     *
     * @return session
     */
    S getSession(String id);

    /**
     * 删除 session
     *
     * @param id session id
     */
    void delete(String id);
}
