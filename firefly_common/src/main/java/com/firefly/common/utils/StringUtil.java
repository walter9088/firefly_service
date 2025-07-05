package com.firefly.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public class StringUtil {
    
    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 判断字符串是否为空白
     *
     * @param str 字符串
     * @return 是否为空白
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 字符串
     * @return 是否不为空白
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 去除字符串两端的空白字符
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }

    /**
     * 去除字符串中的所有空白字符
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    public static String trimAll(String str) {
        return StringUtils.deleteWhitespace(str);
    }

    /**
     * 将字符串转换为驼峰命名
     *
     * @param str 字符串
     * @return 驼峰命名字符串
     */
    public static String toCamelCase(String str) {
        return StringUtils.uncapitalize(StringUtils.capitalize(str));
    }

    /**
     * 将驼峰命名字符串转换为下划线命名
     *
     * @param str 字符串
     * @return 下划线命名字符串
     */
    public static String toSnakeCase(String str) {
        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(str), "_").toLowerCase();
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始位置
     * @param end   结束位置
     * @return 截取后的字符串
     */
    public static String substring(String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }

    /**
     * 重复字符串
     *
     * @param str    字符串
     * @param repeat 重复次数
     * @return 重复后的字符串
     */
    public static String repeat(String str, int repeat) {
        return StringUtils.repeat(str, repeat);
    }
} 