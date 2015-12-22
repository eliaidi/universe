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

package com.github.dactiv.universe.sso.client.authentication.support;

import com.github.dactiv.universe.sso.client.authentication.AttributePrincipal;

import java.util.Map;

/**
 * 简单的当属性的当事人（用户）信息实现
 *
 * @author maurice
 */
public class SimpleAttributePrincipal implements AttributePrincipal {

    // 当事人（用户）名称
    private String username;
    // 附加属性
    private Map<String, Object> attributes;

    /**
     * 简单的当属性的当事人（用户）信息实现
     */
    public SimpleAttributePrincipal() {
    }

    /**
     * 简单的当属性的当事人（用户）信息实现
     *
     * @param username 当事人（用户）名称
     */
    public SimpleAttributePrincipal(String username) {
        this(username, null);
    }

    /**
     * 简单的当属性的当事人（用户）信息实现
     *
     * @param username   当事人（用户）名称
     * @param attributes 附加属性
     */
    public SimpleAttributePrincipal(String username, Map<String, Object> attributes) {
        this.username = username;
        this.attributes = attributes;
    }

    /**
     * 获取附加属性
     *
     * @return 附加属性
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 获取当事人（用户）名称
     *
     * @return 当事人（用户）名称
     */
    @Override
    public String getName() {
        return username;
    }
}
