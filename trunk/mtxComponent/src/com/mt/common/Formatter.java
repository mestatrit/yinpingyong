package com.mt.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 一个支持很多格式化的工具方法
 *
 * @author hanhui
 */

public class Formatter {

    private static Logger logger = LoggerFactory.getLogger(Formatter.class);

    /**
     * 单价（以元为单位）的最多小数位
     */
    public static final int unitPriceYuanMaxFractionDigits = 12;

    /**
     * 单价（以元为单位）的最少小数位
     */
    public static final int unitPriceYuanMinFractionDigits = 4;

    /**
     * 单价（以元为单位）的最多整数位
     */
    public static final int unitPriceYuanMaxIntegerDigits = 3;

    /**
     * 单价（以元为单位）的最少整数位
     */
    public static final int unitPriceYuanMinIntegerDigits = 1;


    /**
     * 总金额（以万元为单位）的最多小数位
     */
    public static final int totalPriceWanYuanMaxFractionDigits = 6;

    /**
     * 总金额（以万元为单位）的最少小数位
     */
    public static final int totalPriceWanYuanMinFractionDigits = 6;

    /**
     * 总金额（以万元为单位）的最多整数位
     */
    public static final int totalPriceWanYuanMaxIntegerDigits = 10;

    /**
     * 总金额（以万元为单位）的最少整数位
     */
    public static final int totalPriceWanYuanMinIntegerDigits = 1;


    /**
     * 总金额（以元为单位）的最多小数位
     */
    public static final int totalPriceYuanMaxFractionDigits = totalPriceWanYuanMaxFractionDigits > 4 ? totalPriceWanYuanMaxFractionDigits - 4 : 0;

    /**
     * 总金额（以元为单位）的最少小数位
     */
    public static final int totalPriceYuanMinFractionDigits = 2;

    /**
     * 总金额（以元为单位）的最多整数位
     */
    public static final int totalPriceYuanMaxIntegerDigits = totalPriceWanYuanMaxIntegerDigits + 4;

    /**
     * 总金额（以元为单位）的最少整数位
     */
    public static final int totalPriceYuanMinIntegerDigits = 1;


    /**
     * 利率（以百分比为单位）的最多小数位
     */
    public static final int ratePercentMaxFractionDigits = 8;

    /**
     * 利率（以百分比为单位）的最少小数位
     */
    public static final int ratePercentMinFractionDigits = 4;

    /**
     * 利率（以百分比为单位）的最多整数位
     */
    public static final int ratePercentMaxIntegerDigits = 2;

    /**
     * 利率（以百分比为单位）的最少整数位
     */
    public static final int ratePercentMinIntegerDigits = 1;


    /**
     * 利率（以1为单位）的最多小数位
     */
    public static final int rateMaxFractionDigits = ratePercentMaxFractionDigits + 2;

    /**
     * 利率（以1为单位）的最少小数位
     */
    public static final int rateMinFractionDigits = 0;

    /**
     * 利率（以1为单位）的最多整数位
     */
    public static final int rateMaxIntegerDigits = ratePercentMaxIntegerDigits > 2 ? ratePercentMaxIntegerDigits - 2 : 1;

    /**
     * 利率（以1为单位）的最少整数位
     */
    public static final int rateMinIntegerDigits = 1;


    /**
     * 天期（以天为单位）的最多小数位
     */
    public static final int interDaysMaxFractionDigits = 0;

    /**
     * 天期（以天为单位）的最少小数位
     */
    public static final int interDaysMinFractionDigits = 0;

    /**
     * 天期（以天为单位）的最多整数位
     */
    public static final int interDaysMaxIntegerDigits = 4;

    /**
     * 天期（以天为单位）的最多整数位
     */
    public static final int interIBODaysMaxIntegerDigits = 6;

    /**
     * 天期（以天为单位）的最少整数位
     */
    public static final int interDaysMinIntegerDigits = 1;


    public static String XMLHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";


    /**
     * 设置一个组件警告颜色
     *
     * @param com
     */
    static public void setWarning(JComponent com) {
        com.setForeground(Color.red);
    }

    /**
     * 去掉一个组件的警告颜色
     *
     * @param com
     */
    static public void cancelWarning(JComponent com) {
        com.setForeground(UIManager.getColor("TextField.foreground"));
    }

    /**
     * 获得字符在某种字体下的宽度
     *
     * @param text
     * @param font
     * @return
     */
    static public int getStringWidth(String text, Font font) {
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        return fm.stringWidth(text);
    }


    /**
     * 是否是中文字符
     *
     * @param c
     * @return
     */
    static public boolean isChinese(char c) {
        if (c > 127) {
            return true;
        }
        return false;
    }

    /**
     * 清楚一组字符串的空格
     *
     * @param data
     * @return
     */
    static public String[] clearSpace(String[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].trim();
        }
        return data;
    }

    /**
     * 从字符串解析到double值，如果解析失败会返回0
     *
     * @param text
     * @return
     */
    static public double getDouble(String text) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        try {
            return df.parse(text).doubleValue();
        } catch (Exception e) {
            return 0;
        }

    }

    static public int getInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 从字符串解析到double值，如果解析失败则抛出异常而不是返回零
     *
     * @param text
     * @return
     * @throws ParseException
     */
    static public double getEDouble(String text) throws ParseException {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        return df.parse(text).doubleValue();
    }

    private static DecimalFormat unitPriceYuanFormatter = new DecimalFormat();
    private static DecimalFormat totalPriceYuanFormatter = new DecimalFormat();
    private static DecimalFormat totalPriceWanYuanFormatter = new DecimalFormat();
    private static DecimalFormat rateFormatter = new DecimalFormat();
    private static DecimalFormat ratePercentFormatter = new DecimalFormat();
    private static DecimalFormat interDaysFormatter = new DecimalFormat();
    private static DecimalFormat interIBODaysFormatter = new DecimalFormat();


    static {
        unitPriceYuanFormatter.setMaximumFractionDigits(unitPriceYuanMaxFractionDigits);
        unitPriceYuanFormatter.setMinimumFractionDigits(unitPriceYuanMinFractionDigits);
        unitPriceYuanFormatter.setMaximumIntegerDigits(unitPriceYuanMaxIntegerDigits);
        unitPriceYuanFormatter.setMinimumIntegerDigits(unitPriceYuanMinIntegerDigits);

        totalPriceYuanFormatter.setMaximumFractionDigits(totalPriceYuanMaxFractionDigits);
        totalPriceYuanFormatter.setMinimumFractionDigits(totalPriceYuanMinFractionDigits);
        totalPriceYuanFormatter.setMaximumIntegerDigits(totalPriceYuanMaxIntegerDigits);
        totalPriceYuanFormatter.setMinimumIntegerDigits(totalPriceYuanMinIntegerDigits);

        totalPriceWanYuanFormatter.setMaximumFractionDigits(totalPriceWanYuanMaxFractionDigits);
        totalPriceWanYuanFormatter.setMinimumFractionDigits(totalPriceWanYuanMinFractionDigits);
        totalPriceWanYuanFormatter.setMaximumIntegerDigits(totalPriceWanYuanMaxIntegerDigits);
        totalPriceWanYuanFormatter.setMinimumIntegerDigits(totalPriceWanYuanMinIntegerDigits);

        ratePercentFormatter.setMaximumFractionDigits(ratePercentMaxFractionDigits);
        ratePercentFormatter.setMinimumFractionDigits(ratePercentMinFractionDigits);
        ratePercentFormatter.setMaximumIntegerDigits(ratePercentMaxIntegerDigits);
        ratePercentFormatter.setMinimumIntegerDigits(ratePercentMinIntegerDigits);

        rateFormatter.setMaximumFractionDigits(rateMaxFractionDigits);
        rateFormatter.setMinimumFractionDigits(rateMinFractionDigits);
        rateFormatter.setMaximumIntegerDigits(rateMaxIntegerDigits);
        rateFormatter.setMinimumIntegerDigits(rateMinIntegerDigits);

        interDaysFormatter.setMaximumIntegerDigits(interDaysMaxIntegerDigits);
        interDaysFormatter.setMinimumIntegerDigits(interDaysMinIntegerDigits);
        interDaysFormatter.setMaximumFractionDigits(interDaysMaxFractionDigits);
        interDaysFormatter.setMinimumFractionDigits(interDaysMinFractionDigits);
        interIBODaysFormatter.setMaximumIntegerDigits(interIBODaysMaxIntegerDigits);
        interIBODaysFormatter.setMinimumIntegerDigits(interDaysMinIntegerDigits);
        interIBODaysFormatter.setMaximumFractionDigits(interDaysMaxFractionDigits);
        interIBODaysFormatter.setMinimumFractionDigits(interDaysMinFractionDigits);
    }

    /**
     * 单价（如净价、全价）以元为单位的格式化。
     */
    static public String formatUnitPriceYuan(Double d) {
        return unitPriceYuanFormatter.format(d);
    }

    /**
     * 总价（如交割总金额、面额）以万元为单位的格式化。
     */
    static public String formatTotalPriceWanYuan(Double d) {
        return totalPriceWanYuanFormatter.format(d);
    }

    /**
     * 总价总价（如交割总金额、面额）以元为单位的格式化。
     */
    static public String formatTotalPriceYuan(Double d) {
        return totalPriceYuanFormatter.format(d);
    }

    /**
     * 利率（如回购利率，拆借利率）以百分比为单位的格式化。
     */
    static public String formatRatePercent(Double d) {
        return ratePercentFormatter.format(d);
    }

    /**
     * 利率（如回购利率，拆借利率）以1为单位的格式化。
     */
    static public String formatRate(Double d) {
        return rateFormatter.format(d);
    }

    /**
     * 天期（如回购天期）以天为单位的格式化。
     */
    static public String formatInterDays(int d) {
        return interDaysFormatter.format(d);
    }

    /**
     * 天期（同业拆借）以天为单位的格式化。
     */
    static public String formatIBOInterDays(int d) {
        return interIBODaysFormatter.format(d);
    }

    /**
     * 获得一个数值的字符串形式
     *
     * @param d
     * @return
     */
    static public String getCommonNumberFormat(Number d) {
        try {
            int temp = (int) d.doubleValue();
            if (temp == d.doubleValue()) {
                return getCommonNumberFormat(d, 0);
            }
            return getCommonNumberFormat(d, 2);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获得一个数值的字符串形式
     * groupuserd = false
     *
     * @param d
     * @return
     */
    static public String getCommonNumberFormatNoGroup(Number d) {
        try {
            int temp = (int) d.doubleValue();
            if (temp == d.doubleValue()) {
                return getCommonNumberFormatNoGroup(d, 0);
            }
            return getCommonNumberFormatNoGroup(d, 2);
        } catch (Exception e) {
            return "";
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

    /**
     * 获得一个数值的字符串形式 , 零不顯示
     *
     * @param d
     * @return
     */
    static public String getCommonNumberFormatWithoutZero(Number d) {
        try {
            int temp = (int) d.doubleValue();
            if (temp == d.doubleValue())
                return getCommonNumberFormatWithoutZero(d, 0);
            return getCommonNumberFormatWithoutZero(d, 2);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 获得一个数值的字符串形式,可以指定小数位数 , 零不顯示
     *
     * @param d
     * @param fd
     * @return
     */
    static public String getCommonNumberFormatWithoutZero(Number d, int fd) {
        try {
            df.setMinimumIntegerDigits(1);
            df.setMinimumFractionDigits(0);
            df.setMaximumFractionDigits(fd);
            return df.format(BigDecimal.valueOf(d.doubleValue()).setScale(fd, RoundingMode.HALF_UP));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获得一个数值的字符串形式,可以指定小数位数
     * groupuserd = false
     *
     * @param d
     * @param fd
     * @return
     */
    static public String getCommonNumberFormatNoGroup(Number d, int fd) {
        try {
            return BigDecimal.valueOf(d.doubleValue()).setScale(fd, RoundingMode.HALF_UP).toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 从字符串解析到Number值
     *
     * @param n
     * @return
     */
    static public Number getNumber(String n) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        try {
            return df.parse(n);
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 对日期增加天数
     *
     * @param date
     * @param day
     * @return
     */
    static public Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 获得两个日期间隔的天数
     *
     * @param s yyyy-MM-dd
     * @param e yyyy-MM-dd
     * @return
     */
    static public long getDay(String s, String e) {
        Date ds = getDateFromDisplay(s);
        Date de = getDateFromDisplay(e);
        long count = de.getTime() - ds.getTime();
        return count / (1000 * 60 * 60 * 24);
    }

    /**
     * 获得某个日期的年份
     *
     * @param date
     * @return
     */
    static public String getYear(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return sf.format(date).substring(0, 4);
    }

    /**
     * 从yyyyMMdd:HH:mm:ss格式得到yyyy-MM-dd HH:mm:ss
     *
     * @param s yyyyMMdd:HH:mm:ss
     * @return yyyy-MM-dd HH:mm:ss
     */
    static public String getDisplayTime(String s) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd:HH:mm:ss");
        Date date = null;
        try {
            date = sf.parse(s);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            ;
            return s;
        }
        sf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sf.format(date);
    }

    /**
     * 从日期得到yyyy/MM/dd HH:mm:ss格式
     *
     * @param date
     * @return yyyy/MM/dd HH:mm:ss
     */
    static public String getDisplayTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);

    }

    /**
     * 将日期从OriginalFormat转换成PresentFormat
     *
     * @param time
     * @param OriginalFormat
     * @param PresentFormat
     * @return Formatted Date String in PresentFormat.
     */
    static public String getFormatDate(String time, String OriginalFormat, String PresentFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(OriginalFormat);
        Date date = null;
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            ;
            return time;
        }
        sf.applyPattern(PresentFormat);
        return sf.format(date);
    }

    /**
     * 获得yyyy/MM/dd HH:mm:ss的时间格式
     *
     * @param time
     * @return HH:mm:ss
     */
    static public String getTime(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            ;
            return time;
        }
        sf.applyPattern("HH:mm:ss");
        return sf.format(date);

    }

    /**
     * 获得某个日期的时间格式
     *
     * @param date
     * @return HH:mm:ss
     */
    static public String getTime(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        return sf.format(date);
    }

    /**
     * 得到日期"MM/dd HH:mm"格式
     *
     * @param date
     * @return MM/dd mm:ss
     */
    static public String getCDisplayDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
        return sf.format(date);
    }


    /**
     * 从"yyyy/MM/dd HH:mm:ss"得到"yyyy-MM-dd"格式
     *
     * @param time
     * @return yyyy-MM-dd
     */
    static public String getDisplayDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            ;
            return time;
        }
        sf.applyPattern("yyyy-MM-dd");
        return sf.format(date);
    }


    /**
     * 从yyyy-MM-dd得到yyyy/MM/dd格式
     *
     * @param s yyyy-MM-dd
     * @return yyyy/MM/dd
     */
    static public String getDateChooserFormat(String s) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sf.parse(s);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            ;
            return s;
        }
        sf.applyPattern("yyyy/MM/dd");
        return sf.format(date);
    }

    /**
     * 得到日期yyyy/MM/dd格式
     *
     * @param date
     * @return yyyy/MM/dd
     */
    static public String getDateChooserFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);

    }

    /**
     * 从yyyyMMdd得到yyyy-MM-dd格式
     *
     * @param s YYYYMMDD
     * @return YYYY_MM_DD
     */
    static public String getDisplayDateFormat(String s) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sf.parse(s);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            ;
            return s;
        }
        sf.applyPattern("yyyy-MM-dd");
        return sf.format(date);
    }

    /**
     * 得到日期yyyy-MM-dd格式
     *
     * @param date
     * @return yyyy-MM-dd
     */
    static public String getDisplayDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 获得日期的yyyyMMdd 格式
     *
     * @param date
     * @return
     */
    static public String getInternalDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    /**
     * 日期比较
     *
     * @param d1 yyyy-MM-dd yyyyMMdd "yyyy-MM-dd HH:mm:ss"
     * @param d2 yyyy-MM-dd yyyyMMdd "yyyy-MM-dd HH:mm:ss"
     * @return 负数, 0, 正数
     *         负数表示d1<d2; 0表示d1==d2; 正数表示d1>d2
     */
    static public int dateCompare(String d1, String d2) {
        Date date1 = getDate(d1);
        Date date2 = getDate(d2);
        if (date1 == null || date2 == null) {
            return 0;
        }
        return date1.compareTo(date2);
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
                if (i == 2 && (sep != c)) return null;
                sep = c;
            }
        }
        if (i == 0) {
            if (d.length() == 8)
                return getDate(makeDateString(d.substring(0, 4), d.substring(4, 6), d.substring(6, 8)));
            else if (d.length() == 6)
                return getDate(makeDateString("20" + d.substring(0, 2), d.substring(2, 4), d.substring(4, 6)));
            else return null;
        } else if (i == 2) {
            String y = d.substring(0, d.indexOf(sep));
            if (y.length() == 2) y = "20" + y;
            else if (y.length() != 4) return null;
            String tmp = d.substring(d.indexOf(sep) + 1);
            String m = tmp.substring(0, tmp.indexOf(sep));
            if (m.length() == 1) m = "0" + m;
            if (m.length() > 2) return null;
            String day = tmp.substring(tmp.indexOf(sep) + 1);
            if (day.length() == 1) day = "0" + day;
            if (day.length() > 2) return null;
            return getDate(makeDateString(y, m, day));
        } else return null;

    }

    private static String makeDateString(String yyyy, String mm,
                                         String dd) {
        return yyyy + "-" + mm + "-" + dd;
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
     * 从HH:mm格式得到Date对象
     *
     * @param d 630,20000
     * @return date
     */
    static public Date getDateFromHMTime(String d) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        sf.setLenient(false);
        try {
            return sf.parse(getHMTimeFomat(d));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 从数字格式化到HH:mm格式
     *
     * @param d 630,2000
     * @return 06:30 ,20:00
     */
    static public String getHMTimeFomat(String d) {
        String temp = d.trim();
        if (temp.length() == 3) {
            return "0" + temp.substring(0, 1) + ":" + temp.substring(1);
        } else if (temp.length() == 4) {
            return temp.substring(0, 2) + ":" + temp.substring(2);
        } else if (temp.length() == 2) {
            return "00:" + temp;
        } else if (temp.length() == 1) {
            return "00:0" + temp;
        }
        return "";
    }


    /**
     * 简便的方法取得所需numberFormat
     *
     * @param maxFrac
     * @param minFrac
     * @param groupUsed
     * @return
     */
    public static NumberFormat getNumberFormat(
            int maxFrac,
            int minFrac,
            boolean groupUsed) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(maxFrac);
        nf.setMinimumFractionDigits(minFrac);
        nf.setGroupingUsed(groupUsed);

        return nf;
    }

    /**
     * 获得NumberFormat对象，最大小数位和最大整数位相同
     *
     * @param max_minFrac
     * @param groupUsed
     * @return
     */
    public static NumberFormat getNumberFormat(
            int max_minFrac,
            boolean groupUsed) {
        return getNumberFormat(max_minFrac, max_minFrac, groupUsed);
    }

    /**
     * 获得NumberFormat对象
     * 支持字符串
     *
     * @param maxFrac
     * @param minFrac
     * @param groupUsed
     * @return
     */
    public static NumberFormat getNumberFormat(
            String maxFrac,
            String minFrac,
            boolean groupUsed) {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.setMaximumFractionDigits(Integer.parseInt(maxFrac));
            nf.setMinimumFractionDigits(Integer.parseInt(minFrac));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
        }

        nf.setGroupingUsed(groupUsed);

        return nf;
    }

    /**
     * 有时候maxFrac=minFrac
     *
     * @param max_minFrac
     * @param groupUsed
     * @return
     */
    public static NumberFormat getNumberFormat(
            String max_minFrac,
            boolean groupUsed
    ) {
        return getNumberFormat(max_minFrac, max_minFrac, groupUsed);
    }

    private static int amount_prec = 2;
    private static int amount_w_prec = 0;
    private static int yield_prec = 4;

    /**
     * default amount format maxFrac=minFrac=2
     * xxx,xxx,xxx.xx
     *
     * @return
     */
    public static NumberFormat getCommonAmountFormat() {
        return getNumberFormat(amount_prec, amount_prec, true);
    }


    /**
     * 用于万元
     * default amount w  format maxFrac=minFrac=0
     * xxx,xxx,xxx
     *
     * @return
     */
    public static NumberFormat getCommon_w_AmountFormat() {
        return getNumberFormat(amount_w_prec, amount_w_prec, true);
    }

    /**
     * 取得默认对收益率or回购利率的format
     * xxx,xxx,xxx.xxxx
     *
     * @return
     */
    public static NumberFormat getCommonYieldFormat() {
        return getNumberFormat(yield_prec, yield_prec, false);
    }

    /**
     * 取得format后的string
     *
     * @param source
     * @param format
     * @return
     */
    public static String getFormattedString(String source, NumberFormat format) {
        try {
            return format.format(Double.parseDouble(source));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
        }
        return source;
    }

    public static String getSendStr(double src) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(8);
        nf.setMinimumFractionDigits(0);
        nf.setGroupingUsed(false);

        return nf.format(src);
    }

    public static String getSendStr(double src, int fd) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(fd);
        nf.setMinimumFractionDigits(0);
        nf.setGroupingUsed(false);

        return nf.format(src);
    }

    /**
     * 将一批数据从元转换到万
     *
     * @param ys
     * @return
     */
    public static String[] convertYW(String[] ys) {
        String[] ws = new String[ys.length];
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        df.setMinimumFractionDigits(6);
        df.setMaximumFractionDigits(6);
        for (int i = 0; i < ys.length; i++) {
            try {
                ws[i] = df.format(Double.parseDouble(ys[i].replaceAll(",", "")) / 10000);
            } catch (Exception ex) {
                ws[i] = ex.getMessage();
            }
        }
        return ws;
    }

    /**
     * 将一批数据从小数转换到百分比
     *
     * @param fs
     * @return
     */
    public static String[] convertFP(String[] fs) {
        String[] ps = new String[fs.length];
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        df.setMinimumFractionDigits(4);
        df.setMaximumFractionDigits(4);
        for (int i = 0; i < fs.length; i++) {
            try {
                ps[i] = df.format(Double.parseDouble(fs[i]) * 100);
            } catch (Exception ex) {
                ps[i] = ex.getMessage();
            }
        }
        return ps;
    }

    /**
     * 将一批逗号分割的数据从元转换到万，结果还是以逗号分割
     *
     * @param ys
     * @return
     */
    public static String convertYW(String ys) {
        return convertData(ys, "YW");
    }

    /**
     * 将一批逗号分割的数据从小数转换到百分比，结果还是以逗号分割
     *
     * @param fs
     * @return
     */
    public static String convertFP(String fs) {
        return convertData(fs, "FP");
    }

    private static String convertData(String data, String type) {
        if (data.equals("")) {
            return "";
        }
        String[] rs = null;
        if (data.contains(",")) {
            if (type.equals("YW")) {
                rs = convertYW(data.split(","));
            } else {
                rs = convertFP(data.split(","));
            }
        } else {
            if (type.equals("YW")) {
                rs = convertYW(new String[]{data});
            } else {
                rs = convertFP(data.split(","));
            }
        }
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < rs.length; i++) {
            if (data.contains(",")) {
                rs[i] = rs[i].replaceAll(",", "");
            }
            if (i == 0) {
                strB.append(rs[i]);
            } else {
                strB.append(',').append(rs[i]);
            }
        }
        return strB.toString();
    }

    public static double getCeil(double number, int n) {
        double num = number;

        double factor = StrictMath.pow(10, n);
        num *= factor;
        num = StrictMath.ceil(num);
        num /= factor;

        return num;
    }


    public static double getFloor(double number, int n) {
        double num = number;

        double factor = StrictMath.pow(10, n);
        num *= factor;
        num = StrictMath.floor(num);
        num /= factor;

        return num;
    }


    public static double getRound(double number, int n) {
        double num = number;

        double factor = StrictMath.pow(10, n);
        num *= factor;
        num = StrictMath.round(num);
        num /= factor;

        return num;
    }

    public static double rounding(double num, int frac) {
        if (Double.isNaN(num) || Double.toString(num) == null) {
            return num;
        }
        return BigDecimal.valueOf(num).setScale(frac, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
