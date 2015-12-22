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

import com.github.dactiv.universe.sso.server.organization.entity.Status;

import java.io.Serializable;

/**
 * 机构访问策略
 *
 * @author maurice
 */
public interface AccessPolicy extends Serializable {

    /**
     * 获取当前状态
     *
     * @return 启用返回 {@link Status#ENABLED}, 禁用返回 {@link Status#DISABLE}
     * @see Status
     */
    Status getStatus();

    /**
     * 是否允许访问 sso
     *
     * @return true 表示是，否则 false
     */
    boolean isAccessAllowedForSso();
}
