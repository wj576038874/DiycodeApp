package com.wenjie.diycode.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间间隔的工具类
 *
 * @author 00
 */
public class TimeUtil {
    //一分钟
    private static final int seconds_of_1minute = 60;
    //30分钟
    private static final int seconds_of_30minutes = 30 * 60;
    //1小时
    private static final int seconds_of_1hour = 60 * 60;
    //一天
    private static final int seconds_of_1day = 24 * 60 * 60;
    //15天 =  一天* 15
    private static final int seconds_of_15days = seconds_of_1day * 15;
    //30天 = 一天*30 = 一个月
    private static final int seconds_of_30days = seconds_of_1day * 30;
    //半年   6个月  = 一个月*6
    private static final int seconds_of_6months = seconds_of_30days * 6;
    //1年
    private static final int seconds_of_1year = seconds_of_30days * 12;

    /**
     * 判断时间 间隔
     *
     * @param createTime 接收消息的时间
     * @return 时间间隔
     */
    public static String getTimeElapse(long createTime) {
        long nowTime = new Date().getTime();
        // createTime是接收消息的时间毫秒数
        long oldTime = createTime;
        // elapsedTime是接收消息和现在的间隔时间毫秒数  再除于1000  以秒计算
        long elapsedTime = (nowTime - oldTime) / 1000;

        //间隔时间小于1分钟
        if (elapsedTime < seconds_of_1minute) {
            return "刚刚";
        }
        //间隔时间小于30分钟
        if (elapsedTime < seconds_of_1hour) {
            return elapsedTime / seconds_of_1minute + "分钟前";
        }
//		if (elapsedTime < seconds_of_1hour) {
//			return "半小时前";
//		}
        //间隔时间小于24小时（1天）
        if (elapsedTime < seconds_of_1day) {
            //elapsedTime / seconds_of_1hour + "小时"+(elapsedTime % seconds_of_1hour)/seconds_of_1minute+"分钟前";
            return elapsedTime / seconds_of_1hour + "小时前";
        }
//		if (elapsedTime < seconds_of_15days) {
//			return elapsedTime / seconds_of_1day + "天前";
//		}
        //间隔时间小于30天
        if (elapsedTime < seconds_of_30days) {
            //elapsedTime / seconds_of_1day + "天"+(elapsedTime % seconds_of_1day)/seconds_of_1hour+"小时前";
            return elapsedTime / seconds_of_1day + "天前";
        }
//		if (elapsedTime < seconds_of_6months) {
//			return elapsedTime / seconds_of_30days + "月前";
//		}

        //间隔时间小于1年
        if (elapsedTime < seconds_of_1year) {
            return elapsedTime / seconds_of_30days + "月前";
        }
        //间隔时间大于1年
        if (elapsedTime >= seconds_of_1year) {
            return elapsedTime / seconds_of_1year + "年前";
        }
        return "";
    }

    /**
     * 2017-08-02T17:58:00.719+08:00
     *
     * @param time
     * @return
     */
    public static long string2time(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.CANADA);
        try {
            Date date = simpleDateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取返回yyyy-MM-dd格式的日期
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 获取返回yyyy-MM-dd-HH:mm:ss格式的日期
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentYMDHMS() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 根据毫秒值返回 yyyy-MM-dd-HH:mm:ss格式的日期
     *
     * @param time 毫秒值
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String time2CurrentYMDHMS(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sb = format.format(gc.getTime());
        return sb;
    }


    /**
     * 根据毫秒值返回 yyyy-MM-dd-HH:mm:ss格式的日期
     *
     * @param time 毫秒值
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String time2CurrentYMDHMS(String time) {

        if (time.length() < 8) {

            return time.substring(0, 4);
        }

        long parseLong = Long.parseLong(time);

        Date dat = new Date(parseLong);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sb = format.format(gc.getTime());
        return sb;
    }

    /**
     * 将字符串数据转化为毫秒数
     */
    @SuppressLint("SimpleDateFormat")
    public static long time2Millisecond(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            long millionSeconds = sdf.parse(time).getTime() / 1000;
            return millionSeconds;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将字符串数据转化为毫秒数
     */
    @SuppressLint("SimpleDateFormat")
    public static long time2Millisecond2(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据毫秒值返回 yyyy-MM-dd格式的日期
     *
     * @param time 毫秒值
     * @return
     */
    public static String time2CurrentYMD(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String sb = format.format(gc.getTime());
        return sb;
    }


    /**
     * 根据毫秒值返回 yyyy-MM-dd格式的日期
     *
     * @param time 毫秒值
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String time2CurrentYMD(String time) {
        if (time.length() < 8) {
            return time.substring(0, 4);
        }
        long parseLong = Long.parseLong(time);
        Date dat = new Date(parseLong);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(gc.getTime());
    }


}
