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
package com.github.dactiv.universe.sso.server.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Java 版 Discuz! 加解密 iv 类
 *
 * @author maurice
 */
public class AuthCoder {

    /**
     * 默认的随机密钥长度
     */
    public final static int DEFAULT_RANDOM_KEY_LENGTH = 4;
    /**
     * 默认的字符串编码
     */
    public final static String DEFAULT_VALUE_ENCODING = "UTF-8";
    /**
     * 默认参数分隔符
     */
    public final static String DEFAULT_SPLIT_STRING = "&";
    /**
     * 默认左右值分隔符
     */
    public final static String DEFAULT_LEFT_RIGHT_SPLIT_STRING = "=";
    /**
     * 默认加密后的解密超时时间
     */
    public final static int DEFAULT_EXPIRY = 0;

    /**
     * 解密
     *
     * @param value 加密后的字符串
     * @param key   密钥
     * @return 原字符串信息
     * @throws UnsupportedEncodingException
     */
    public static String decode(String value, String key) throws UnsupportedEncodingException {
        return decode(value, key, DEFAULT_RANDOM_KEY_LENGTH);
    }

    /**
     *
     * @param value           加密后的字符串
     * @param key             密钥
     * @param randomKeyLength 随机密钥长度 取值 0-32，加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，
     *                        加密结果也会每次不同，增大破解难度（实际上就是iv）。取值越大，密文变动规律越大，
     *                        密文变化 = 16 的 randomKeyLength 次方，当此值为 0 时，则不产生随机密钥
     * @return 原字符串信息
     * @throws UnsupportedEncodingException
     */
    public static String decode(String value, String key, int randomKeyLength) throws UnsupportedEncodingException {
        return decode(value, key, randomKeyLength, DEFAULT_VALUE_ENCODING);
    }

    /**
     * 解密
     * @param value    加密后的字符串
     * @param key      密钥
     * @param encoding 字符编码
     * @return 原字符串信息
     * @throws UnsupportedEncodingException
     */
    public static String decode(String value, String key, String encoding) throws UnsupportedEncodingException {
        return decode(value, key, DEFAULT_RANDOM_KEY_LENGTH, encoding);
    }

    /**
     * 解密
     *
     * @param value           加密后的字符串
     * @param key             密钥
     * @param randomKeyLength 随机密钥长度 取值 0-32，加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，
     *                        加密结果也会每次不同，增大破解难度（实际上就是iv）。取值越大，密文变动规律越大，
     *                        密文变化 = 16 的 randomKeyLength 次方，当此值为 0 时，则不产生随机密钥
     * @param encoding        字符编码
     * @return 原字符串信息
     */
    public static String decode(String value, String key, int randomKeyLength, String encoding) throws UnsupportedEncodingException {

        key = DigestUtils.md5Hex(key.getBytes());

        String keya = DigestUtils.md5Hex(StringUtils.substring(key, 0, 16).getBytes());
        String keyb = DigestUtils.md5Hex(StringUtils.substring(key, 16, 16 + 16).getBytes());
        String keyc = randomKeyLength > 0 ? StringUtils.substring(value, 0, randomKeyLength) : "";

        String cryptKey = keya + DigestUtils.md5Hex((keya + keyc).getBytes());
        int cryptKeyLen = cryptKey.length();

        value = new String(Base64.decodeBase64((StringUtils.substring(value, randomKeyLength))), encoding);
        int stringLen = value.length();

        List<Integer> rndKey = new ArrayList<>();

        StringBuilder result = new StringBuilder();

        Integer[] box = new Integer[256];
        for(int i=0; i<box.length; i++) {
            box[i] = i;
        }

        for(int i=0; i<= 255; i++) {
            rndKey.add((int)cryptKey.charAt(i%cryptKeyLen));
        }

        for(int j = 0, i = 0; i<256; i++) {
            j = (j + box[i] + rndKey.get(i)) % 256;
            int tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }

        for(int k=0,j=0,i=0; i<stringLen; i++) {
            k = (k + 1) % 256;
            j = (j + box[k]) % 256;
            int tmp = box[k];
            box[k] = box[j];
            box[j] = tmp;
            int a = (int)value.charAt(i);
            int b = box[(box[k] + box[j]) % 256];
            char r = (char)(a ^ b);
            result.append(r);
        }

        if((NumberUtils.toInt(StringUtils.substring(result.toString(), 0, 10), -1) == 0 ||
                NumberUtils.toInt(StringUtils.substring(result.toString(), 0, 10), 0) - System.currentTimeMillis()/1000 > 0)
                && StringUtils.substring(result.toString(), 10, 10 + 16).equals(StringUtils.substring(DigestUtils.md5Hex((StringUtils.substring(result.toString(), 26) + keyb).getBytes()), 0, 16))){
            return StringUtils.substring(result.toString(), 26);
        } else {
            return "";
        }
    }

    /**
     * 加密
     *
     * @param value 加密后的字符串
     * @param key 密钥
     * @return 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String encode(String value, String key) throws UnsupportedEncodingException {
        return encode(value, key, DEFAULT_EXPIRY);
    }

    /**
     * 加密
     *
     * @param value  加密后的字符串
     * @param key    密钥
     * @param expiry 超时时间
     * @return 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String encode(String value, String key, int expiry) throws UnsupportedEncodingException {
        return encode(value, key, expiry, DEFAULT_RANDOM_KEY_LENGTH, DEFAULT_VALUE_ENCODING);
    }

    /**
     * 加密
     *
     * @param value           加密后的字符串
     * @param key             密钥
     * @param expiry          超时时间
     * @param randomKeyLength 随机密钥长度 取值 0-32，加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，
     *                        加密结果也会每次不同，增大破解难度（实际上就是iv）。取值越大，密文变动规律越大，
     *                        密文变化 = 16 的 randomKeyLength 次方，当此值为 0 时，则不产生随机密钥
     * @return 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String encode(String value, String key, int expiry, int randomKeyLength) throws UnsupportedEncodingException {
        return encode(value, key, expiry, randomKeyLength,DEFAULT_VALUE_ENCODING);
    }

    /**
     * 加密
     * @param value    加密后的字符串
     * @param key      密钥
     * @param expiry   超时时间
     * @param encoding 字符编码
     *
     * @return 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String encode(String value, String key, int expiry, String encoding) throws UnsupportedEncodingException {
        return encode(value, key, expiry, DEFAULT_RANDOM_KEY_LENGTH, encoding);
    }

    /**
     * 加密
     *
     * @param value           加密后的字符串
     * @param key             密钥
     * @param expiry          超时时间
     * @param randomKeyLength 随机密钥长度 取值 0-32，加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，
     *                        加密结果也会每次不同，增大破解难度（实际上就是iv）。取值越大，密文变动规律越大，
     *                        密文变化 = 16 的 randomKeyLength 次方，当此值为 0 时，则不产生随机密钥
     * @param encoding        字符编码
     * @return 加密后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String encode(String value, String key, int expiry, int randomKeyLength, String encoding) throws UnsupportedEncodingException {

        key = DigestUtils.md5Hex(key.getBytes());
        String keya = DigestUtils.md5Hex(StringUtils.substring(key, 0, 16).getBytes());
        String keyb = DigestUtils.md5Hex(StringUtils.substring(key, 16, 16 + 16).getBytes());
        String keyc = randomKeyLength > 0 ? StringUtils.substring(DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()/1000).getBytes()), - randomKeyLength) : "";

        String cryptKey = keya + DigestUtils.md5Hex((keya + keyc).getBytes());
        int cryptKeyLen = cryptKey.length();

        value = String.format("%010d", expiry > 0 ? expiry + System.currentTimeMillis()/1000 : 0) + StringUtils.substring(DigestUtils.md5Hex((value + keyb).getBytes()), 0 ,16) + value;
        int stringLen = value.length();

        List<Integer> rndKey = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        Integer[] box = new Integer[256];
        for(int i=0; i<box.length; i++) {
            box[i] = i;
        }

        for(int i=0; i<= 255; i++) {
            rndKey.add((int)cryptKey.charAt(i%cryptKeyLen));
        }

        for(int j = 0, i = 0; i<256; i++) {
            j = (j + box[i] + rndKey.get(i)) % 256;
            int tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }

        for(int k=0,j=0,i=0; i<stringLen; i++) {
            k = (k + 1) % 256;
            j = (j + box[k]) % 256;
            int tmp = box[k];
            box[k] = box[j];
            box[j] = tmp;
            int a = (int) value.charAt(i);
            int b = box[(box[k] + box[j]) % 256];
            char r = (char)(a ^ b);
            result.append(r);
        }

        return keyc + Base64.encodeBase64String(result.toString().getBytes(encoding)).replaceAll("=", "");
    }


    /**
     * 解码，并返回 map
     *
     * @param value 值
     * @param key 密钥
     * @param split 中间分隔符
     * @param lrSplit 左右值分隔符
     *
     * @return map
     *
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> decodeToMap(String value, String key, String split, String lrSplit) throws UnsupportedEncodingException {

        Map<String, String> result = new LinkedHashMap<>();
        String target = decode(value, key);
        String [] splitString = StringUtils.split(target, split);

        for (String ss : splitString) {
            String right = StringUtils.substringAfter(ss, lrSplit);
            String left = StringUtils.substringBefore(ss, lrSplit);
            result.put(left, right);
        }

        return result;
    }

    /**
     * 解码，并返回 map
     * @param value 值
     * @param key 密钥
     *
     * @return map
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> decodeToMap(String value, String key) throws UnsupportedEncodingException {
        return decodeToMap(value, key, DEFAULT_SPLIT_STRING, DEFAULT_LEFT_RIGHT_SPLIT_STRING);
    }

}
