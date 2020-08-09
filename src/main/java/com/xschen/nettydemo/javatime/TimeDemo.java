package com.xschen.nettydemo.javatime;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author xschen
 *
 */


public class TimeDemo {

    // 获取当前此刻的时间
    public void rightNowTest() {
        LocalDateTime rightNow = LocalDateTime.now();
        System.out.println("当前时间：" + rightNow);
        System.out.println("当前年份：" + rightNow.getYear());
        System.out.println("当前月份：" + rightNow.getMonthValue());
        System.out.println("当前日期：" + rightNow.getDayOfMonth());
        System.out.println("当前时：" + rightNow.getHour());
        System.out.println("当前分：" + rightNow.getMinute());
        System.out.println("当前秒：" + rightNow.getSecond());
    }

    /**
     * 当前时间：2020-07-21T14:52:53.429
     * 当前年份：2020
     * 当前月份：7
     * 当前日期：21
     * 当前时：14
     * 当前分：52
     * 当前秒：53
     */


    // 构造2020年6月22日5时40分30秒
    public void createTimeTest() {
        LocalDateTime time = LocalDateTime.of(2020, Month.JUNE, 22, 5, 40, 30);
        System.out.println(time);
    }

    // 修改日期
    public void modifyTimeTest() {
        LocalDateTime rightNow = LocalDateTime.now();

        System.out.println(rightNow.minusYears(2)); // 减少2年
        System.out.println(rightNow.plusMonths(3)); // 增加3个月
        System.out.println(rightNow.withYear(2008)); // 直接修改到2008年

    }

    // 格式化时间
    public void formatTimeTest() {
        LocalDateTime rightNow = LocalDateTime.now();
        String result1 = rightNow.format(DateTimeFormatter.ISO_DATE);
        String result2 = rightNow.format(DateTimeFormatter.BASIC_ISO_DATE);
        String result3 = rightNow.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    /**
     * 2020-07-21
     * 20200721
     * 2020/07/21
     */


    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    // 时间戳转换为时间的字符串格式
    public static String convertTimeToString(Long time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    // 字符串格式的时间转换为时间戳
    public static Long convertTimeToLong(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDateTime parse = LocalDateTime.parse(stringDate, formatter);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }




    public static void main(String[] args) {
        TimeDemo timeDemo = new TimeDemo();
//        timeDemo.rightNowTest();
//        timeDemo.createTimeTest();
//        timeDemo.modifyTimeTest();

        timeDemo.formatTimeTest();
    }

}
