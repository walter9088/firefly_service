package com.firefly.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间工具类
 */
public class DateUtil {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 格式化日期时间
     *
     * @param date    日期
     * @param pattern 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化日期时间（默认格式）
     *
     * @param date 日期
     * @return 格式化后的字符串
     */
    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    /**
     * 解析日期时间字符串
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static Date parse(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }

    /**
     * 解析日期时间字符串（默认格式）
     *
     * @param dateStr 日期字符串
     * @return 日期对象
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DEFAULT_PATTERN);
    }

    /**
     * 获取指定时区的当前时间
     *
     * @param timeZone 时区
     * @return 当前时间
     */
    public static Date now(TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
        sdf.setTimeZone(timeZone);
        String dateStr = sdf.format(new Date());
        return parse(dateStr);
    }

    /**
     * 获取两个日期之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 天数差
     */
    public static long daysBetween(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }
} 