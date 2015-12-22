package com.github.dactiv.universe.sso.server.validation;

import java.util.Map;

/**
 * 票据验证管理
 *
 * @author maurice
 */
public interface TicketValidationManager {

    /**
     * 验证票据
     *
     * @param requestPairSource 请求信息
     * @return 断言结果
     */
    Map<String, Object> valid(Object requestPairSource);

}
