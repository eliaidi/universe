package com.github.dactiv.universe.map.validation.mapping;

import com.github.dactiv.universe.map.validation.Constraint;
import com.github.dactiv.universe.map.validation.MappingKey;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的key 映射信息接口实现
 *
 * @author maurice
 */
public class SimpleMappingKey implements MappingKey {

    private String name;
    private String alias;

    private List<Constraint> constraints = new ArrayList<Constraint>();

    public SimpleMappingKey(String name, String alias, List<Constraint> constraints) {
        this.name = name;
        this.alias = alias;
        this.constraints = constraints;
    }

    @Override
    public List<Constraint> getConstraints() {
        return constraints;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getKeyName() {
        String result = getAlias();
        if (result == null || result.equals("")) {
            result = getName();
        }
        return result;
    }
}
