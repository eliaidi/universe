package com.github.dactiv.universe.sso.server.entity.support;

import com.github.dactiv.universe.sso.server.entity.IdEntity;

/**
 * 简单的 id 实体类实现
 *
 * @author maurice
 */
public class SimpleIdEntity implements IdEntity {

    // 唯一识别 id
    private Object id;

    /**
     * 简单的 id 实体类实现
     */
    public SimpleIdEntity() {
    }

    /**
     * 简单的 id 实体类实现
     *
     * @param id id 值
     */
    public SimpleIdEntity(Object id) {
        this.id = id;
    }

    /**
     * 获取唯一识别 id
     *
     * @return id
     */
    @Override
    public Object getId() {
        return id;
    }

    /**
     * 设置唯一识别 id
     *
     * @param id 唯一识别 id
     */
    @Override
    public void setId(Object id) {
        this.id = id;
    }
}
