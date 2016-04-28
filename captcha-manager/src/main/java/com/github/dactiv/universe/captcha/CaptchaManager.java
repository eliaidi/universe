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

package com.github.dactiv.universe.captcha;

import com.github.dactiv.universe.captcha.entity.Captcha;
import com.github.dactiv.universe.captcha.entity.CaptchaToken;
import com.github.dactiv.universe.captcha.entity.ValidResult;

/**
 * 验证码业务管理
 *
 * @author maurice
 */
public interface CaptchaManager {

    /**
     * 保存验证码
     *
     * @param captcha 验证码
     */
    void save(Captcha captcha);

    /**
     * 删除验证码
     *
     * @param id 主键 id
     */
    void delete(String id);

    /**
     * 创建验证码
     *
     * @param token 验证码令牌
     *
     * @return 验证码实体
     */
    Captcha create(CaptchaToken token);

    /**
     * 验证验证码
     *
     * @param id   操作仓库
     * @param code 验证码
     * @return 验证信息
     */
    ValidResult valid(String id, String code);

    /**
     * 获取验证码
     *
     * @param id 验证码 id
     *
     * @return 验证码实体
     */
    Captcha get(String id);

}
