package com.dili.formation8.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by asiam on 2017/4/28 0028.
 */
public class DateUtils {

    public static long getServerTime() {
        return System.currentTimeMillis();
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(String format) {
        return format(new Date(), format);
    }

    public static String format(Date date, String format) {
        if(date == null) {
            return null;
        } else {
            try {
                SimpleDateFormat e = new SimpleDateFormat(format);
                return e.format(date);
            } catch (Exception var3) {
                throw new RuntimeException("日期格式化转换失败", var3);
            }
        }
    }

    public static String dateFormat(long time) {
        return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(String dateStr, String oldFromat, String newFormat) {
        try {
            if(StringUtils.isBlank(dateStr)) {
                return null;
            } else {
                SimpleDateFormat e = new SimpleDateFormat(oldFromat);
                Date date = e.parse(dateStr);
                return format(date, newFormat);
            }
        } catch (Exception var5) {
            throw new RuntimeException("日期格式化转换失败", var5);
        }
    }

    public static Calendar format(String dateStr, String dateStrFormat) {
        try {
            if(StringUtils.isBlank(dateStr)) {
                return null;
            } else {
                SimpleDateFormat e = new SimpleDateFormat(dateStrFormat);
                Date date = e.parse(dateStr);
                Calendar ca = GregorianCalendar.getInstance();
                ca.setTime(date);
                return ca;
            }
        } catch (Exception var5) {
            throw new RuntimeException("日期格式化转换失败", var5);
        }
    }

    public static Date dateStr2Date(String dateStr, String dateStrFormat) {
        try {
            if(StringUtils.isBlank(dateStr)) {
                return null;
            } else {
                SimpleDateFormat e = new SimpleDateFormat(dateStrFormat);
                return e.parse(dateStr);
            }
        } catch (Exception var3) {
            throw new RuntimeException("日期格式化转换失败", var3);
        }
    }

    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    public static Date reduceHours(Date date, int amount) {
        if(null == date) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if(amount < 0) {
                cal.add(11, amount);
            } else {
                cal.add(11, -amount);
            }

            return cal.getTime();
        }
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date addMilliSeconds(Date date, int amount) {
        return add(date, 14, amount);
    }

    public static Date addSeconds(int amount) {
        return add(new Date(), 13, amount);
    }

    private static Date add(Date date, int field, int amount) {
        if(null == date) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(field, amount);
            return cal.getTime();
        }
    }

    public static String formatDate2DateTimeStart(String dateStr) {
        Calendar calendar = format(dateStr, "yyyy-MM-dd");
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return format(calendar.getTime());
    }

    public static String formatDate2DateTimeEnd(String dateStr) {
        Calendar calendar = format(dateStr, "yyyy-MM-dd");
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return format(calendar.getTime());
    }

    public static Date formatDate2DateTimeStart(Date date) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            return calendar.getTime();
        }
    }

    public static Date formatDate2DateTimeEnd(Date date) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            return calendar.getTime();
        }
    }

    public static Date formatDateStr2Date(String date) {
        return dateStr2Date(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date addMinutes(Date nextDate, int amount) {
        return add(new Date(), 12, amount);
    }

    public static boolean equals(Date time1, Date time2) {
        return (time1 != null || time2 == null) && (time1 == null || time2 != null)?(time1 == null && time2 == null?true:StringUtils.equals(format(time1), format(time2))):false;
    }
}
