package com.mt.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @Author: Ryan
 * 
 * 2012-7-17
 */
public class DateUtil {
	
	static private final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	/**
     * format the date to be only yyyyMMdd
     *
     * @param date
     * @return
     */
    public static Date getFormattedDate(Date date) {
        GregorianCalendar gday = new GregorianCalendar();
        gday.setTime(date);
        gday.set(Calendar.HOUR, 0);
        gday.set(Calendar.HOUR_OF_DAY, 0);
        gday.set(Calendar.MINUTE, 0);
        gday.set(Calendar.SECOND, 0);
        gday.set(Calendar.MILLISECOND, 0);

        date = gday.getTime();
        return date;
    }
    
    /**
     * convent method to get days after or before
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getDateAfter(Date date, int days) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DATE, days);
        return calendar.getTime();
    }
    
    public static String getDateString(Date date, String formatString) {
        return new SimpleDateFormat(formatString).format(date);
    }
    
    public static Date getDate(String dStr, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return getDate(dStr, format);
    }
    
    public static Date getDate(String dStr, DateFormat format) {
        format.setLenient(false);
        Date date = null;
        try {
            date = format.parse(dStr);
        } catch (ParseException ex) {
            logger.error("解析日期格式出错", ex);
        }
        return date;
    }
}
