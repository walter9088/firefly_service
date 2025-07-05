package com.firefly.utils.string;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.regex.Pattern;

public class FireflyStringUtils {
    private FireflyStringUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 判断字符串是否为空白
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否不为空白
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 去除字符串两端空白
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }

    /**
     * 生成UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成指定长度的随机字符串
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 判断是否为邮箱
     */
    public static boolean isEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }

    /**
     * 判断是否为手机号
     */
    public static boolean isPhone(String phone) {
        if (isBlank(phone)) {
            return false;
        }
        String regex = "^1[3-9]\\d{9}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 判断是否为身份证号
     */
    public static boolean isIdCard(String idCard) {
        if (isBlank(idCard)) {
            return false;
        }
        String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 字符串转驼峰命名
     */
    public static String toCamelCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        str = str.toLowerCase();
        StringBuilder sb = new StringBuilder(str.length());
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名转下划线
     */
    public static String toUnderlineCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean nextUpperCase = true;
            if (i < (str.length() - 1)) {
                nextUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }
            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append('_');
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
} 