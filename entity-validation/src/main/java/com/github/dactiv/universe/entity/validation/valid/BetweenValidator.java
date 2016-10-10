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

package com.github.dactiv.universe.entity.validation.valid;

import com.github.dactiv.universe.entity.validation.Constraint;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 范围值验证器
 *
 * @author maurice
 */
public class BetweenValidator extends AllowsNullValueValidator{

    public static final String NAME = "between";

    private static final String MAX_ATTR_NAME = "max";
    private static final String MIN_ATTR_NAME = "min";
    private static final String FORMAT_ATTR_NAME = "format";
    private static final String REGULAR_EXPRESSION_ATTR_NAME = "reg";
    private static final String MIN_NUMBER_DEFAULT_VALUE = "0.0";
    private static final String MIN_DATE_DEFAULT_VALUE = "1970-01-01 00:00:00";

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat();

    private List<String> dateRegularExpressionList = new ArrayList<String>();
    private List<String> dateFormatList = new ArrayList<String>();
    /**
     * 范围值验证器
     */
    public BetweenValidator() {
        dateRegularExpressionList.add("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        dateFormatList.add("yyyy-MM-dd");
        dateFormatList.add("yyyy-MM-dd HH:mm:ss");
        dateFormatList.add("yyyy-MM");
        dateFormatList.add("HH:mm:ss");
    }

    @Override
    public boolean valid(Object value, Map<String, Object> source, Constraint constraint) {
        String maxValue = constraint.getElement().attributeValue(MAX_ATTR_NAME);

        if (Date.class.isAssignableFrom(value.getClass()) || isDateValue(value.toString(), constraint)) {
            String minValue = constraint.getElement().attributeValue(MIN_ATTR_NAME, MIN_DATE_DEFAULT_VALUE);
            return validDateValue(value, constraint, maxValue, minValue);
        }

        String minValue = constraint.getElement().attributeValue(MIN_ATTR_NAME, MIN_NUMBER_DEFAULT_VALUE);
        return validNumberValue(value, constraint, maxValue, minValue);
    }

    /**
     * 获取日期对象
     *
     * @param value 字符串时间值
     * @param format 格式化信息
     *
     * @return 日期对象
     */
    public Date getDate(String value, String format) {
        if (format != null && !"".equals(format)) {
            FORMAT.applyPattern(format);
            try {
                return FORMAT.parse(value);
            } catch (ParseException e) {

            }
        }

        for (String f : dateFormatList) {
            FORMAT.applyPattern(f);
            try {
                return FORMAT.parse(value);
            } catch (ParseException e) {
            }
        }

        return null;
    }

    /**
     * 验证时间型的范围值是否正确
     *
     * @param value 时间型范围值
     * @param constraint 验证约束
     * @param maxValue 最大值
     * @param minValue 最小值
     *
     * @return ture 表示正确, 否则 false
     */
    private boolean validDateValue(Object value, Constraint constraint, String maxValue, String minValue) {

        String cformat = constraint.getElement().attributeValue(FORMAT_ATTR_NAME);

        Date maxDate = getDate(maxValue, cformat);
        Date minDate = getDate(minValue, cformat);

        Date v = Date.class.isAssignableFrom(value.getClass()) ? (Date) value : getDate(value.toString(), cformat);

        setMessage(constraint, FORMAT.format(minDate), FORMAT.format(maxDate));

        return v.getTime() >= minDate.getTime() && v.getTime() <= maxDate.getTime();
    }

    /**
     * 验证数值型的范围值是否正确
     *
     * @param value 数值型范围值
     * @param constraint 验证约束
     * @param maxValue 最大值
     * @param minValue 最小值
     *
     * @return ture 表示正确, 否则 false
     */
    private boolean validNumberValue(Object value, Constraint constraint, String maxValue, String minValue) {

        constraint.setDefaultMessage(MessageFormat.format(constraint.getDefaultMessage(), minValue, maxValue));

        String message = constraint.getMessage();

        if (message != null && message.indexOf("{") > 0 && message.indexOf("}") > 0) {
            constraint.setMessage(MessageFormat.format(message, minValue, maxValue));
        }

        try {
            Double max = Double.parseDouble(maxValue);
            Double min = Double.parseDouble(minValue);

            Double v = new Double(value.toString());

            return v >= min && v <= max;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    /**
     * 校验是否日期值
     *
     * @param value 值
     * @param constraint 约束条件
     *
     * @return true 表示是, 否则 false.
     */
    private boolean isDateValue(String value, Constraint constraint) {

        String cel = constraint.getElement().attributeValue(REGULAR_EXPRESSION_ATTR_NAME);

        if (cel != null && !"".equals(cel) && Pattern.compile(cel).matcher(value).matches()) {
            return Boolean.TRUE;
        }

        for (String el : dateRegularExpressionList) {
            Pattern p = Pattern.compile(el);
            if (p.matcher(value).matches()) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }


    @Override
    public String getName() {
        return NAME;
    }

    /**
     * 设置时间正则表达式验证集合
     *
     * @param dateRegularExpressionList 正则表达式验证集合
     */
    public void setDateRegularExpressionList(List<String> dateRegularExpressionList) {
        this.dateRegularExpressionList.addAll(dateRegularExpressionList);
    }

    /**
     * 设置时间格式化集合
     *
     * @param dateFormatList 时间格式化集合
     */
    public void setDateFormatList(List<String> dateFormatList) {
        this.dateFormatList = dateFormatList;
    }
}
