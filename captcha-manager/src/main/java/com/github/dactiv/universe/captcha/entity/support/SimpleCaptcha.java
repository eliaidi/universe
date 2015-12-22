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

package com.github.dactiv.universe.captcha.entity.support;

import com.github.dactiv.universe.captcha.entity.Captcha;

import java.util.Date;

/**
 * 简单的验证码实体实现
 *
 * @author maurice
 */
public class SimpleCaptcha implements Captcha {

    private String id;

    private String code;

    private Date creationTime;

    private byte[] stream;

    /**
     * 简单的验证码实体实现
     */
    public SimpleCaptcha() {
        super();
    }

    /**
     * 简单的验证码实体实现
     *
     * @param id           主键 id
     * @param code         验证码
     * @param creationTime 创建时间
     * @param stream       流
     */
    public SimpleCaptcha(String id, String code, Date creationTime, byte[] stream) {
        this.id = id;
        this.code = code;
        this.creationTime = creationTime;
        this.stream = stream;
    }

    /**
     * 获取主键 id
     *
     * @return 主键 id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取验证码代码
     *
     * @return 验证码代码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取验证码图片的流
     *
     * @return 验证码图片流
     */
    @Override
    public byte[] getStream() {
        return stream;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    @Override
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 设置主键 id
     *
     * @param id 主键 id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 设置验证码
     *
     * @param code 验证码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 设置验证码流
     *
     * @param stream 流
     */
    public void setStream(byte[] stream) {
        this.stream = stream;
    }
}
