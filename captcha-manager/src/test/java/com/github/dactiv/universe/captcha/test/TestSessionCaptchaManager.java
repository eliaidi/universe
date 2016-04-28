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

package com.github.dactiv.universe.captcha.test;

import com.github.dactiv.universe.captcha.entity.Captcha;
import com.github.dactiv.universe.captcha.entity.ValidResult;
import com.github.dactiv.universe.captcha.entity.support.JpegImgCaptchaToken;
import com.github.dactiv.universe.captcha.entity.support.ValidResultSupport;
import com.github.dactiv.universe.captcha.support.SessionCaptchaManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;

/**
 * 单元测试验证码管理
 *
 * @author maurice
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestSessionCaptchaManager {

    private SessionCaptchaManager captchaManager = new SessionCaptchaManager();
    private MockHttpSession mockHttpSession = new MockHttpSession(new MockServletContext(new DefaultResourceLoader()));

    @Before
    public void install() {
        captchaManager.setCurrentSession(mockHttpSession);
    }

    @Test
    public void test() {

        Captcha captcha = captchaManager.create(new JpegImgCaptchaToken());

        Object value = mockHttpSession.getAttribute(SessionCaptchaManager.DEFAULT_CAPTCHA_ATTRIBUTE_NAME);
        Assert.assertNotNull(value);

        ValidResult validResult = captchaManager.valid(captcha.getId(), captcha.getCode());
        Assert.assertTrue(validResult instanceof ValidResultSupport);

        value = mockHttpSession.getAttribute(SessionCaptchaManager.DEFAULT_CAPTCHA_ATTRIBUTE_NAME);
        Assert.assertNull(value);

        validResult = captchaManager.valid(captcha.getId(), "ssss");
        Assert.assertFalse(validResult.getIsValid());
    }


}
