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

package com.github.dactiv.universe.sso.server.validation;

import java.util.Map;

/**
 * 票据验证管理
 *
 * @author maurice
 */
public interface TicketValidationManager {

    /**
     * 验证票据
     *
     * @param requestPairSource 请求信息
     * @return 断言结果
     */
    Map<String, Object> valid(Object requestPairSource);

}
