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
    public static final int DEFAULT_FONT_SIZE = 22;
    /**
     * 默认验证码图片宽度
     */
    public static final int DEFAULT_WIDTH = 96;
    /**
     * 默认验证码图片高度
     */
    public static final int DEFAULT_HEIGHT = 44;

    /**
     * 默认验证码长度
     */
    public static final int DEFAULT_LENGTH = 4;

    /**
     * 默认图片循环时间秒
     */
    public static final int DEFAULT_LOOP_SECOND = 10;

    // 图片循环时间秒
    private int loopSecond = DEFAULT_LOOP_SECOND;
    // 图片宽度
    private int width = DEFAULT_WIDTH;
    // 图片高度
    private int height = DEFAULT_HEIGHT;
    // 字体大小
    private int fontSize = DEFAULT_FONT_SIZE;
    // 验证码长度
    private int length = DEFAULT_LENGTH;
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
     * @param loopSecond 图片循环时间秒
     * @param width 图片宽度
     * @param height 图片高度
     * @param fontSize 字体大小
     * @param length 验证码长度
     * @param fontFamily 验证码字体
     */
    public JpegImgCaptchaToken(int loopSecond, int width, int height, int fontSize, int length, String fontFamily) {
        this.loopSecond = loopSecond;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.length = length;
        this.fontFamily = fontFamily;
    }

    /**
     * 获取图片循环时间秒
     *
     * @return 图片循环时间秒
     */
    public int getLoopSecond() {
        return loopSecond;
    }

    /**
     * 设置图片循环时间秒
     *
     * @param loopSecond 图片循环时间秒
     */
    public void setLoopSecond(int loopSecond) {
        this.loopSecond = loopSecond;
    }

    /**
     * 获取图片宽度
     *
     * @return 宽度
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置图片宽度
     *
     * @param width 宽度
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 获取图片高度
     *
     * @return 高度
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置图片高度
     *
     * @param height 高度
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 获取字体大小
     *
     * @return 字体大小
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * 获取验证码长度
     *
     * @return 验证码长度
     */
    public int getLength() {
        return length;
    }

    /**
     * 设置验证码长度
     *
     * @param length 验证码长度
     */
    public void setLength(int length) {
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
