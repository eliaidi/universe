package com.github.dactiv.universe.sso.server.entity;

import java.io.Serializable;

/**
 * 主键 id 实体接口
 *
 * @author maurice
 */
public interface IdEntity extends Serializable {

    /**
     * 获取唯一识别 id
     *
     * @return id
     */
    Object getId();

    /**
     * 设置唯一识别 id
     *
     * @param id 唯一识别 id
     */
    void setId(Object id);

}
