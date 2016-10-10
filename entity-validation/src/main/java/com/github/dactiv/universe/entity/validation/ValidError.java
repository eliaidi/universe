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

package com.github.dactiv.universe.entity.validation;

/**
 *
 * 验证错误接口，用于描述哪个字段验证不通过以及为什么。
 *
 * @author maurice
 */
public interface ValidError {

    /**
     * 获取验证错误的 map key 名称
     *
     * @return  如果 xml 里配置了 alias 属性, 将是 alias 值, 否则为 map key 名称
     */
    String getName();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
