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

package com.github.dactiv.universe.map.validation.valid;

import com.github.dactiv.universe.map.validation.Constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 日期格式验证器
 *
 * @author maurice
 */
public class DateValidator extends AllowsNullValueValidator{

    public static final String NAME = "date";
    private static final String EL_ATTR_NAME = "el";

    private List<String> dateRegList = new ArrayList<String>();

    /**
     * 日期格式验证器
     */
    public DateValidator() {
        dateRegList.add("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    }

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        String cel = constraint.getElement().attributeValue(EL_ATTR_NAME);

        if (cel != null && !"".equals(cel) && Pattern.compile(cel).matcher(value.toString()).matches()) {
            return Boolean.TRUE;
        }

        for (String drl : dateRegList) {
            Pattern p = Pattern.compile(drl);
            if (p.matcher(value.toString()).matches()) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    /**
     * 设置时间正则表达式验证集合
     *
     * @param dateRegList 正则表达式验证集合
     */
    public void setDateRegList(List<String> dateRegList) {
        this.dateRegList = dateRegList;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
