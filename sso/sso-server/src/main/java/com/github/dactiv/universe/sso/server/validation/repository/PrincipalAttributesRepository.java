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

package com.github.dactiv.universe.sso.server.validation.repository;

import java.io.Serializable;
import java.util.Map;

/**
 * 当事人（用户）属性库
 *
 * @author maurice
 */
public interface PrincipalAttributesRepository extends Serializable {

    /**
     * 获取当时人（用户）所有属性
     *
     * @param principal 当时人（用户）
     * @return 所有属性
     */
    Map<String, Object> getAttributes(Object principal);

}
