package com.github.dactiv.universe.captcha.generator;

import com.github.dactiv.universe.captcha.CaptchaGenerator;
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
     * 默认字体
     */
    public static final String DEFAULT_FONT_FAMILY = "Arial Black";
    /**
     * 默认字体大小
     */
    public static final Integer DEFAULT_FONT_SIZE = 16;
    /**
     * 默认验证码图片宽度
     */
    public static final Integer DEFAULT_IMAGE_WIDTH = 60;
    /**
     * 默认验证码图片高度
     */
    public static final Integer DEFAULT_IMAGE_HEIGHT = 18;
    /**
     * 验证码字符长度
     */
    public static final Integer DEFAULT_CODE_LENGTH = 4;
    /** 验证码字体 */
    private String fontFamily = DEFAULT_FONT_FAMILY;
    /** 字体大小 */
    private Integer fontSize = DEFAULT_FONT_SIZE;
    /** 图片宽度 */
    private Integer imageWidth = DEFAULT_IMAGE_WIDTH;
    /** 图片高度 */
    private Integer imageHeight = DEFAULT_IMAGE_HEIGHT;
    /** 验证码字符长度 */
    private Integer codeLength = DEFAULT_CODE_LENGTH;

    /**
     * 生成验证码
     *
     * @param stream 图像流
     * @return 验证码
     */
    @Override
    public String generate(OutputStream stream) throws CaptchaException {
        // 生成一张新图片
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Font font = new Font(fontFamily, Font.PLAIN, fontSize);
        // 绘制图片
        Random random = new Random();
        Graphics g = image.getGraphics();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, imageHeight - 1, imageHeight - 1);
        g.setColor(new Color(102, 102, 103));
        g.drawRect(0, 0, imageHeight - 1, imageHeight - 1);
        g.setFont(font);
        // 随机生成线条，让图片看起来更加杂乱
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(imageHeight - 1);
            int y = random.nextInt(imageHeight - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            g.drawLine(x, y, x + x1, y + y1);
        }
        // 该变量用于保存系统生成的随机字符串
        String sRand = "";
        for (int i = 0; i < codeLength; i++) {
            // 取得一个随机字符
            String tmp = getRandomChar();
            sRand += tmp;
            // 将系统随机字符添加到图形验证码图片上
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(tmp, fontSize * i + 5, fontSize + 5);
        }
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", stream);
            stream.close();
        } catch (IOException e) {
            throw new CaptchaException(e);
        }
        return sRand;
    }

    /**
     * 生成随机颜色
     *
     * @param fc 从随机的颜色位置 (0-255)
     * @param bc 到随机的颜色位置（0-255）
     * @return {@link Color}
     */
    public static Color getRandColor(int fc, int bc) {
        Random random = new Random();

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    /**
     * 生成随机字符的方法
     */
    public static String getRandomChar() {
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
     * 设置字体
     *
     * @param fontFamily 字体名称
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     */
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * 设置图片宽度
     *
     * @param imageWidth 宽度
     */
    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * 设置图片高度
     *
     * @param imageHeight 高度
     */
    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * 设置验证码长度
     *
     * @param codeLength 长度
     */
    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }
}
