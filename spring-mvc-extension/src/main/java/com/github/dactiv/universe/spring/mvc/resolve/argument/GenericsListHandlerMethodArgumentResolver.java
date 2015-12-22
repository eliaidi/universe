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

package com.github.dactiv.universe.spring.mvc.resolve.argument;

import com.github.dactiv.universe.spring.mvc.annotation.GenericsList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.GenericCollectionTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * 泛型集合参数解析器，用于在表单提交动态集合参数(param[0].name, param[1].name,param[2].name)时使用
 *
 * @author maurice
 */
public class GenericsListHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    // spring el
    private ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 设置 spring el 配置
     *
     * @param expressionParser spring el 配置
     */
    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    /**
     * 重写父类方法，该 argument resolver 仅仅支持带有 {@link GenericsList} 注解, 并且参数类型是 List 的
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GenericsList.class) && List.class.isAssignableFrom(parameter.getParameterType());
    }

    /**
     * 重写父类方法，解析 request 的参数信息
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        GenericsList genericsList = parameter.getParameterAnnotation(GenericsList.class);
        String name = StringUtils.isEmpty(genericsList.name()) ? parameter.getParameterName() :  genericsList.name();
        // 获取参数为 name 做前缀的参数集合
        List<Map<String, Object>> param = getParameter(name, webRequest);

        // 获取该参数的泛型 class
        Class<?> type = GenericCollectionTypeResolver.getCollectionParameterType(parameter);
        // 如果参数为空
        if (param.isEmpty()) {
            // 并且 GenericsList 注解的 required 等于 true， 表示该值必须要传，但又没转，直接抛出异常
            if (genericsList.required()) {
                throw new MissingServletRequestParameterException(name, parameter.getGenericParameterType().toString());
            }
            // 如果 GenericsList 注解的 required 等于 false，那就返回 null 回去
            return null;
        }
        // 如果有参数，并且该参数的泛型是 Map 那就直接返回 param 即可
        if (Map.class.isAssignableFrom(type)) {
            return param;
        } else { // 否则通过 spring el 将该 class 反射到实体中
            List<Object> result = new ArrayList<>();

            for (Map<String, Object> map : param) {
                Object o = type.newInstance();

                StandardEvaluationContext simpleContext = new StandardEvaluationContext(o);

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    expressionParser.parseExpression(entry.getKey()).setValue(simpleContext, entry.getValue());
                }

                result.add(o);
            }

            return result;
        }

    }

    /**
     * 获取参数信息
     *
     * @param name 参数名称
     * @param webRequest request 对象
     *
     * @return 参数信息集合
     */
    private List<Map<String, Object>> getParameter(String name, NativeWebRequest webRequest) {

        List<Map<String, Object>> result = new ArrayList<>();

        // 排序树 map
        Map<Integer, Map<String, Object>> temp = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer target, Integer source) {
                return target.compareTo(source);
            }
        });

        putParameterValue(temp, name, webRequest.getParameterMap());

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(servletRequest, MultipartHttpServletRequest.class);

        if (multipartRequest != null) {
            putParameterValue(temp, name, multipartRequest.getFileMap());
        }

        // 上面做完后，在将所有 key 加入到 result 中。保持值的顺序
        for (Integer key : temp.keySet()) {
            result.add(temp.get(key));
        }

        return result;
    }

    /**
     * 设置参数到 result 参数里
     *
     * @param result result map
     * @param name 参数名称
     * @param parameter 参数集合
     */
    private void putParameterValue(Map<Integer, Map<String, Object>> result, String name, Map<String, ?> parameter) {

        // 获取所有参数信息
        for (Map.Entry<String, ?> entry : parameter.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.startsWith(key, name)) {
                // 获取当前参数的索引值
                int index = Integer.parseInt(StringUtils.substringBetween(key, "[", "]"));
                Map<String, Object> data = new LinkedHashMap<>();
                // 如果存在该索引值，先 get 出来
                if (result.containsKey(index)) {
                    data =  result.get(index);
                } else {
                    result.put(index, data);
                }

                Object value = entry.getValue();

                if (value instanceof String[]) {
                    String[] tempValue = (String[]) value;
                    value = tempValue.length > 1 ? tempValue : tempValue[0];
                }

                data.put(StringUtils.substringAfterLast(key, "."), value);
            }
        }

    }
}
