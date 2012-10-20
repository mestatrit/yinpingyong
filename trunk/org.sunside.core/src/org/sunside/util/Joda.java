package org.sunside.util;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author:Ryan
 * @date:2012-10-21
 */
public class Joda {

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		
		System.out.println(dt.dayOfWeek().getAsText(Locale.CHINA));
		
		System.out.println(dt.dayOfWeek().getAsText(Locale.ENGLISH));
		
		System.out.println(dt.plusHours(10).dayOfWeek().getAsText(Locale.ENGLISH));
		
		System.out.println(dt.withZone(DateTimeZone.UTC));
		
		//Joda转成Jdk日期
		Date jdkDate = dt.toDate();
		
		//Jdk日期转成Joda
		dt = new DateTime(jdkDate);
	}

}
