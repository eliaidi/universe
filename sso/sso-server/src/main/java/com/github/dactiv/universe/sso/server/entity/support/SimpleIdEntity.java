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

package com.github.dactiv.universe.sso.server.entity.support;

import com.github.dactiv.universe.sso.server.entity.IdEntity;

/**
 * 简单的 id 实体类实现
 *
 * @author maurice
 */
public class SimpleIdEntity implements IdEntity {

    // 唯一识别 id
    private Object id;

    /**
     * 简单的 id 实体类实现
     */
    public SimpleIdEntity() {
    }

    /**
     * 简单的 id 实体类实现
     *
     * @param id id 值
     */
    public SimpleIdEntity(Object id) {
        this.id = id;
    }

    /**
     * 获取唯一识别 id
     *
     * @return id
     */
    @Override
    public Object getId() {
        return id;
    }

    /**
     * 设置唯一识别 id
     *
     * @param id 唯一识别 id
     */
    @Override
    public void setId(Object id) {
        this.id = id;
    }
}
