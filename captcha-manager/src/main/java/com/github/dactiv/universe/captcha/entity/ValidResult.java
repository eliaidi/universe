package com.github.dactiv.universe.captcha.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maurice
 */
public interface ValidResult extends Serializable{

    /**
     * 获取验证时间
     *
     * @return 验证时间
     */
    Date getValidTime();

    /**
     * 获取验证信息
     *
     * @return 信息
     */
    String getMessage();

}
