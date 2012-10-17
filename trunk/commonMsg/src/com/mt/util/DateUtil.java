package com.mt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getDateString(Date date, DateFormat format) {
		return format.format(date);
	}
	
	public static String getDateString(Date date, String formatString) {
        return new SimpleDateFormat(formatString).format(date);
    }
}
