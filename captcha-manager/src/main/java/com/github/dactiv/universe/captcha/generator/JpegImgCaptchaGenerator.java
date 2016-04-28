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

package com.github.dactiv.universe.captcha.generator;

import com.github.dactiv.universe.captcha.CaptchaGenerator;
import com.github.dactiv.universe.captcha.entity.CaptchaToken;
import com.github.dactiv.universe.captcha.entity.support.JpegImgCaptchaToken;
import com.github.dactiv.universe.captcha.exception.CaptchaException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * jpeg图片的验证码生成器
 *
 * @author maurice
 *
 */
public class JpegImgCaptchaGenerator implements CaptchaGenerator {

    /**
     * 生成随机颜色
     *
     * @param fc 从随机的颜色位置 (0-255)
     * @param bc 到随机的颜色位置（0-255）
     * @return {@link Color}
     */
    public Color getRandColor(int fc, int bc) {
        Random random = new Random();

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    /**
     * 生成随机字符的方法
     */
    public String getRandomChar() {
        int rand = (int) Math.round(Math.random() * 2);
        long itmp;
        char ctmp;
        // 根据rand的值来决定是生成一个大写字母，小写字母和数字
        switch (rand) {
            // 生成大写字母的情形
            case 1:
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            // 生成小写字母的情形
            case 2:
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            // 生成数字的情形
            default:
                itmp = Math.round(Math.random() * 9);
                return String.valueOf(itmp);
        }

    }

    /**
     * 生成验证码
     *
     * @param token 验证码令牌
     * @return 验证码
     */
    @Override
    public String generate(CaptchaToken token) throws CaptchaException {
        JpegImgCaptchaToken jpegImgCaptchaToken = (JpegImgCaptchaToken) token;
        // 生成一张新图片
        BufferedImage image = new BufferedImage(jpegImgCaptchaToken.getWidth(), jpegImgCaptchaToken.getHeight(), BufferedImage.TYPE_INT_RGB);
        Font font = new Font(jpegImgCaptchaToken.getFontFamily(), Font.PLAIN, jpegImgCaptchaToken.getFontSize());
        // 绘制图片
        Random random = new Random();
        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, jpegImgCaptchaToken.getWidth() - 1, jpegImgCaptchaToken.getHeight() - 1);
        g.setColor(new Color(102, 102, 103));
        g.drawRect(0, 0, jpegImgCaptchaToken.getWidth() - 1, jpegImgCaptchaToken.getHeight() - 1);
        g.setFont(font);
        // 随机生成线条，让图片看起来更加杂乱
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(jpegImgCaptchaToken.getWidth() - 1);
            int y = random.nextInt(jpegImgCaptchaToken.getHeight() - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            g.drawLine(x, y, x + x1, y + y1);
        }
        // 该变量用于保存系统生成的随机字符串
        String sRand = "";
        for (int i = 0; i < jpegImgCaptchaToken.getLength(); i++) {
            // 取得一个随机字符
            String tmp = getRandomChar();
            sRand += tmp;
            // 将系统随机字符添加到图形验证码图片上
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(tmp, jpegImgCaptchaToken.getFontSize() * i + 5, jpegImgCaptchaToken.getFontSize() + 5);
        }
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", jpegImgCaptchaToken.getOutputStream());
            jpegImgCaptchaToken.getOutputStream().close();
        } catch (IOException e) {
            throw new CaptchaException(e);
        }
        return sRand;
    }
}
