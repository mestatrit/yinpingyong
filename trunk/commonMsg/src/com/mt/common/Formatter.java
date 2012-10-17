package com.mt.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

	/**
	 * 一个比较通用的日期解析函数
	 * 
	 * @param d
	 * @return
	 */
	static public Date getDateN(String d) {
		int i = 0;
		char sep = '-';
		for (char c : d.toCharArray()) {
			if (c > '9' || c < '0') {
				i++;
				if (i == 2 && (sep != c))
					return null;
				sep = c;
			}
		}
		if (i == 0) {
			if (d.length() == 8)
				return getDate(makeDateString(d.substring(0, 4), d.substring(4,6), d.substring(6, 8)));
			else if (d.length() == 6)
				return getDate(makeDateString("20" + d.substring(0, 2), d.substring(2, 4), d.substring(4, 6)));
			else
				return null;
		} else if (i == 2) {
			String y = d.substring(0, d.indexOf(sep));
			if (y.length() == 2)
				y = "20" + y;
			else if (y.length() != 4)
				return null;
			String tmp = d.substring(d.indexOf(sep) + 1);
			String m = tmp.substring(0, tmp.indexOf(sep));
			if (m.length() == 1)
				m = "0" + m;
			if (m.length() > 2)
				return null;
			String day = tmp.substring(tmp.indexOf(sep) + 1);
			if (day.length() == 1)
				day = "0" + day;
			if (day.length() > 2)
				return null;
			return getDate(makeDateString(y, m, day));
		} else
			return null;

	}
	
	/**
     * 能支持yyyy-MM-dd yyyyMMdd "yyyy-MM-dd HH:mm:ss"三种格式的解析
     * "yyyy-MM-dd"的格式用"yyyyMMdd"也可以解析，所以要放在前面
     *
     * @param d yyyy-MM-dd yyyyMMdd "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    static public Date getDate(String d) {
        Date date = getDateFromTime(d);
        if (date != null) return date;

        date = getDateFromDisplay(d);
        if (date != null) return date;

        return getDateFromInternal(d);
    }

    /**
     * 从yyyy-MM-dd HH:mm:ss格式得到Date对象
     *
     * @param d "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    static public Date getDateFromTime(String d) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sf.setLenient(false);
        try {
            return sf.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 从yyyy-MM-dd格式得到Date
     *
     * @param d "yyyy-MM-dd"
     * @return
     */
    static public Date getDateFromDisplay(String d) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        sf.setLenient(false);
        try {
            return sf.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }
	
    /**
     * 从yyyyMMdd得到Date对象
     *
     * @param d yyyyMMdd
     * @return
     */
    static public Date getDateFromInternal(String d) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        sf.setLenient(false);
        try {
            return sf.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }
    
	private static String makeDateString(String yyyy, String mm, String dd) {
		return yyyy + "-" + mm + "-" + dd;
	}
	
	/**
     * 从yyyyMMdd HH:mm:ss格式得到Date对象
     *
     * @param d
     * @return
     */
    static public Date getDateFromIITime(String d) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        sf.setLenient(false);
        try {
            return sf.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 从yyyy/MM/dd HH:mm:ss格式得到Date对象
     *
     * @param d
     * @return
     */
    static public Date getDateFromITime(String d) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sf.setLenient(false);
        try {
            return sf.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }

    static private DecimalFormat df = new DecimalFormat();
    
    /**
     * 获得一个数值的字符串形式,可以指定小数位数
     *
     * @param d
     * @param fd
     * @return
     */
    static public String getCommonNumberFormat(Number d, int fd) {
        try {
            df.setMinimumIntegerDigits(1);
            df.setMaximumFractionDigits(fd);
            df.setMinimumFractionDigits(fd);
            return df.format(BigDecimal.valueOf(d.doubleValue()).setScale(fd, RoundingMode.HALF_UP));
        } catch (Exception e) {
            return "";
        }
    }
}
