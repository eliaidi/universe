package com.github.dactiv.universe.sso.server.ticket.generator.support;


import com.github.dactiv.universe.sso.server.ticket.generator.RandomStringGenerator;

import java.security.SecureRandom;

/**
 * 简单的随机字符串生成实现类
 *
 * @author maurice
 */
public class SimpleRandomStringGenerator implements RandomStringGenerator {

    // 随机的字符生成规则
    private static final char[] PRINTABLE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345679".toCharArray();

    // 默认生成随机字符的最大值
    private static final int DEFAULT_MAX_RANDOM_LENGTH = PRINTABLE_CHARACTERS.length;

    // 随机生成类
    private final SecureRandom randomizer = new SecureRandom();

    // 生成随机字符的最大值
    private final int maximumRandomLength;

    /**
     * 简单的随机字符串生成实现类
     */
    public SimpleRandomStringGenerator() {
        this.maximumRandomLength = DEFAULT_MAX_RANDOM_LENGTH;
    }

    /**
     * 简单的随机字符串生成实现类
     *
     * @param maximumRandomLength 随机数的最大长度
     */
    public SimpleRandomStringGenerator(int maximumRandomLength) {
        this.maximumRandomLength = maximumRandomLength;
    }

    /**
     * 获取需要生成字符的最大长度
     *
     * @return 最大长度值
     */
    @Override
    public int getMinimumLength() {
        return maximumRandomLength;
    }

    /**
     * 获取需要生成字符的最小长度
     *
     * @return 最小长度值
     */
    @Override
    public int getMaximumLength() {
        return maximumRandomLength;
    }

    /**
     * 创建一个新的随机字符串
     *
     * @return 新的随机字符串
     */
    @Override
    public String getNewString() {
        final byte[] random = getNewStringAsBytes();

        return convertBytesToString(random);
    }

    /**
     * 将随机 byte 数组转换成字符串
     *
     * @param random byte 数组
     * @return 字符串
     */
    private String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
            output[i] = PRINTABLE_CHARACTERS[index];
        }

        return new String(output);
    }

    /**
     * 获取当前随机字符串的 byte 数组
     *
     * @return 字符串的 byte 数组
     */
    @Override
    public byte[] getNewStringAsBytes() {
        final byte[] random = new byte[this.maximumRandomLength];

        this.randomizer.nextBytes(random);

        return random;
    }
}
