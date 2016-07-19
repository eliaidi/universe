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

import java.util.Set;

/**
 * 渠道专用 session
 *
 * @author maurice
 */
public interface Session {

    /**
     * 获取 id
     *
     * @return session id 值
     */
    String getId();

    /**
     * 获取属性
     *
     * @param attributeName 属性名称
     *
     * @return 属性值
     */
    <T> T getAttribute(String attributeName);

    /**
     * 获取所有属性名称
     *
     * @return 所有属性名称
     */
    Set<String> getAttributeNames();

    /**
     * 设置属性
     *
     * @param attributeName 属性名称
     * @param attributeValue 属性值
     */
    void setAttribute(String attributeName, Object attributeValue);

    /**
     * 删除属性
     *
     * @param attributeName 属性名称
     */
    void removeAttribute(String attributeName);
}
