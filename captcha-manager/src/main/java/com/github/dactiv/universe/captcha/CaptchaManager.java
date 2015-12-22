package com.github.dactiv.universe.captcha;

import com.github.dactiv.universe.captcha.entity.Captcha;
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
     * @return 验证码实体
     */
    Captcha create();

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
