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

package com.github.dactiv.universe.captcha.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码接口
 *
 * @author maurice
 */
public interface Captcha extends Serializable {

    /**
     * 获取主键 id
     *
     * @return 主键 id
     */
    String getId();

    /**
     * 获取验证码代码
     *
     * @return 验证码代码
     */
    String getCode();

    /**
     * 获取验证码图片的流
     *
     * @return 验证码图片流
     */
    byte[] getStream();

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Date getCreationTime();

}
