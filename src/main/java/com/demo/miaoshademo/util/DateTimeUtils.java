package com.demo.miaoshademo.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by geely
 */
public class DateTimeUtils {

    //joda-time

    //str->Date
    //Date->str
//    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateFormat STANDARD_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static Date strToDate(String dateTimeStr, String formatStr) throws ParseException {
        DateFormat sf = new SimpleDateFormat(formatStr);
        Date ateTimeFormatter = sf.parse(dateTimeStr);
        return ateTimeFormatter;
    }

    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        DateFormat sf = new SimpleDateFormat(formatStr);
        String dateTime = sf.format(date);
        return dateTime;
    }

    public static Date strToDate(String dateTimeStr) throws ParseException {
        Date ateTimeFormatter = STANDARD_FORMAT.parse(dateTimeStr);
        return ateTimeFormatter;
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        String dateTime = STANDARD_FORMAT.format(date);
        return dateTime;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(DateTimeUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeUtils.strToDate("2010-01-01 11:11:11", "yyyy-MM-dd HH:mm:ss"));
    }


}