package com.github.dactiv.universe.captcha.test;

import com.github.dactiv.universe.captcha.entity.Captcha;
import com.github.dactiv.universe.captcha.entity.ValidResult;
import com.github.dactiv.universe.captcha.entity.support.FailureValidResult;
import com.github.dactiv.universe.captcha.entity.support.SuccessValidResult;
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
        Captcha captcha = captchaManager.create();

        Object value = mockHttpSession.getAttribute(SessionCaptchaManager.DEFAULT_CAPTCHA_ATTRIBUTE_NAME);
        Assert.assertNotNull(value);

        ValidResult validResult = captchaManager.valid(captcha.getId(), captcha.getCode());
        Assert.assertTrue(validResult instanceof SuccessValidResult);

        value = mockHttpSession.getAttribute(SessionCaptchaManager.DEFAULT_CAPTCHA_ATTRIBUTE_NAME);
        Assert.assertNull(value);

        validResult = captchaManager.valid(captcha.getId(), "ssss");
        Assert.assertTrue(validResult instanceof FailureValidResult);

        value = mockHttpSession.getAttribute(SessionCaptchaManager.DEFAULT_CAPTCHA_ATTRIBUTE_NAME);
        Assert.assertNotNull(value);

        FailureValidResult failureValidResult = (FailureValidResult) validResult;
        captcha = failureValidResult.getNextCaptcha();

        validResult = captchaManager.valid(captcha.getId(), captcha.getCode());
        Assert.assertTrue(validResult instanceof SuccessValidResult);

        value = mockHttpSession.getAttribute(SessionCaptchaManager.DEFAULT_CAPTCHA_ATTRIBUTE_NAME);
        Assert.assertNull(value);
    }


}
