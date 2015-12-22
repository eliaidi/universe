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
