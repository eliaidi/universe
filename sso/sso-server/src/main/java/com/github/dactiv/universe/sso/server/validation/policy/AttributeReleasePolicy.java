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

package com.github.dactiv.universe.sso.server.validation.policy;

import java.io.Serializable;
import java.util.Map;

/**
 * 发布当事人（用户）信息接口
 *
 * @author maurice
 */
public interface AttributeReleasePolicy extends Serializable {

    /**
     * 是否发用户登录密码
     *
     * @return true 表示是，否则 false
     */
    boolean isReleaseCredentialPassword();

    /**
     * 是否发布票据信息
     *
     * @return true 表示是，否则 false
     */
    boolean isReleaseTicket();

    /**
     * 获取发布属性
     *
     * @param principal 当事人（用户）
     * @return 属性 map
     */
    Map<String, Object> getAttributes(Object principal);
}
