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
package com.github.dactiv.universe.captcha.entity.support;


/**
 * jpeg 图片验证码令牌
 *
 * @author maurice
 */
public class JpegImgCaptchaToken extends CaptchaTokenSupport {

    /**
     * 默认字体
     */
    public static final String DEFAULT_FONT_FAMILY = "Arial Black";
    /**
     * 默认字体大小
     */
    public static final Integer DEFAULT_FONT_SIZE = 22;
    /**
     * 默认验证码图片宽度
     */
    public static final Integer DEFAULT_WIDTH = 96;
    /**
     * 默认验证码图片高度
     */
    public static final Integer DEFAULT_HEIGHT = 44;

    /**
     * 默认验证码长度
     */
    public static final Integer DEFAULT_LENGTH = 4;

    // 图片宽度
    private Integer width = DEFAULT_WIDTH;
    // 图片高度
    private Integer height = DEFAULT_HEIGHT;
    // 字体大小
    private Integer fontSize = DEFAULT_FONT_SIZE;
    // 验证码长度
    private Integer length = DEFAULT_LENGTH;
    // 验证码字体
    private String fontFamily = DEFAULT_FONT_FAMILY;

    /**
     * jpeg 图片验证码令牌
     */
    public JpegImgCaptchaToken() {
    }

    /**
     * jpeg 图片验证码令牌
     *
     * @param width 图片宽度
     * @param height 图片高度
     * @param fontSize 字体大小
     * @param length 验证码长度
     * @param fontFamily 验证码字体
     */
    public JpegImgCaptchaToken(Integer width, Integer height, Integer fontSize, Integer length, String fontFamily) {
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.length = length;
        this.fontFamily = fontFamily;
    }

    /**
     * 获取图片宽度
     *
     * @return 宽度
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 设置图片宽度
     *
     * @param width 宽度
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 获取图片高度
     *
     * @return 高度
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 设置图片高度
     *
     * @param height 高度
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 获取字体大小
     *
     * @return 字体大小
     */
    public Integer getFontSize() {
        return fontSize;
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
     * 获取验证码长度
     *
     * @return 验证码长度
     */
    public Integer getLength() {
        return length;
    }

    /**
     * 设置验证码长度
     *
     * @param length 验证码长度
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * 获取验证码字体
     *
     * @return 验证码字体
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * 设置验证码字体
     *
     * @param fontFamily 验证码字体
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }
}
