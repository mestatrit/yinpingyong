package com.mt.common.dynamicDataDef;

import com.mt.common.Formatter;
import com.mt.util.DateUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 一个表示字段的类
 *
 * @author hanhui
 */
public class Field implements Cloneable {

    /**
     * 字段的名字
     */
    private String name;
    /**
     * 字段的值
     */
    private Object value;

    /**
     * 由名称构建Field,值是null
     *
     * @param name
     */
    public Field(String name) {
        this(name, null);
    }

    /**
     * 由名称和值构建Field
     *
     * @param name
     * @param value
     */
    public Field(String name, Object value) {

        setName(name);
        setValue(value);
    }

    /**
     * 设置Field的名称
     *
     * @param name
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * 获得Field的名称
     *
     * @return
     */
    public final String getName() {
        return name;
    }

    /**
     * 设置Field的值
     *
     * @param value
     */
    public final void setValue(Object value) {

        this.value = value;
    }

    /**
     * 得到Field的值
     *
     * @return
     */
    public final Object getValue() {
        return value;
    }

    /**
     * 获得字符串值
     *
     * @return
     */
    public String getStringValue() {
        return value == null ? "" : value.toString();
    }

    /**
     * 将一个Date值转换为字符串形式（格式为yyyyMMdd）再设置Field的值
     *
     * @param value
     */
    public void setDateStringValue(Date value) {
        setValue(DateUtil.getDateString(value, "yyyyMMdd"));
    }

    /**
     * 将一个Date值转换为字符串形式（格式可以自定义）再设置Field的值
     *
     * @param value
     * @param format
     */
    public void setDateStringValue(Date value, String format) {
        setValue(DateUtil.getDateString(value, format));
    }

    /**
     * 将一个字符串日期按format转换再设置Field的值
     * 如果字符串是空将直接设Field的值为空
     *
     * @param value
     * @param format
     */
    public void setStringDateValue(String value, String format) {
        if (value.equals("")) {
            setValue(value);
            return;
        }
        Date date = Formatter.getDateN(value);
        if (date == null) {
            throw new FieldValueException(this, "传进来的字符串日期无法解析成Date");
        }
        setDateStringValue(date, format);
    }

    /**
     * 将一个整数值转换为字符串形式再设置Field的值
     *
     * @param value
     */
    public void setIntStringValue(int value) {
        setValue(Integer.toString(value));
    }

    /**
     * 将一个Double值转换为字符串形式再设置Field的值
     *
     * @param value
     */
    public void setDoubleStringValue(double value) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        setValue(df.format(value));
    }

    /**
     * 将一个Double值转换为字符串形式再设置Field的值,会在单位上做一个万到元的转换
     *
     * @param value
     */
    public void setDoubleStringValue_WY(double value) {
        double v = value * 10000;
        setDoubleStringValue(v, 2);
    }

    /**
     * 将一个Double值转换为字符串形式再设置Field的值,会在单位上做一个百分比到小数的转换
     *
     * @param value
     */
    public void setDoubleStringValue_PF(double value) {
        double v = value / 100;
        setDoubleStringValue(v, 4);
    }

    /**
     * 将一个Double值转换为字符串形式再设置Field的值，可以自定义小数位数
     *
     * @param value
     * @param fd
     */
    public void setDoubleStringValue(double value, int fd) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(fd);
        df.setMinimumFractionDigits(fd);
        df.setGroupingUsed(false);
        setValue(df.format(value));
    }

    /**
     * 将一个Double值转换为字符串形式再设置Field的值,会在单位上做一个万到元的转换
     * 并且可以控制转换到元后的小数位数
     *
     * @param value
     * @param fd
     */
    public void setDoubleStringValue_WY(double value, int fd) {
        double v = value * 10000;
        setDoubleStringValue(v, fd);
    }

    /**
     * 将一个Double值转换为字符串形式再设置Field的值,会在单位上做一个百分比到小数的转换
     * 并且可以控制转换后的值的小数位数
     *
     * @param value
     * @param fd
     */
    public void setDoubleStringValue_PF(double value, int fd) {
        double v = value / 100;
        setDoubleStringValue(v, fd);
    }

    /**
     * 将一个BigDecimal值转换为字符串形式再设置Field的值
     *
     * @param bd
     */
    public void setBigDecimalStringValue(BigDecimal bd) {
        setValue(bd.toString());
    }

    /**
     * 将一个BigDecimal值转换为字符串形式再设置Field的值
     * 可以控制小数位数
     *
     * @param bd
     * @param fd
     */
    public void setBigDecimalStringValue(BigDecimal bd, int fd) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(fd);
        df.setMinimumFractionDigits(fd);
        setValue(df.format(bd));
    }

    /**
     * 将一个BigDecimal值转换为字符串形式再设置Field的值
     * 会在单位上做一个万到元的转换
     *
     * @param bd
     */
    public void setBigDecimalStringValue_WY(BigDecimal bd) {
        BigDecimal v = bd.multiply(new BigDecimal("10000"));
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        setValue(df.format(v));
    }

    /**
     * 将一个BigDecimal值转换为字符串形式再设置Field的值
     * 会在单位上做一个万到元的转换,可以指定小数位数
     *
     * @param bd
     * @param fd
     */
    public void setBigDecimalStringValue_WY(BigDecimal bd, int fd) {
        BigDecimal v = bd.multiply(new BigDecimal("10000"));
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(fd);
        df.setMinimumFractionDigits(fd);
        setValue(df.format(v));
    }

    /**
     * 将一个BigDecimal值转换为字符串形式再设置Field的值
     * 会在单位上做一个百分比到小数的转换
     *
     * @param bd
     */
    public void setBigDecimalStringValue_PF(BigDecimal bd) {
        BigDecimal v = bd.divide(new BigDecimal("100"));
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(4);
        df.setMinimumFractionDigits(0);
        setValue(df.format(v));
    }

    /**
     * 将一个BigDecimal值转换为字符串形式再设置Field的值
     * 会在单位上做一个百分比到小数的转换,可以指定小数位数
     *
     * @param bd
     * @param fd
     */
    public void setBigDecimalStringValue_PF(BigDecimal bd, int fd) {
        BigDecimal v = bd.divide(new BigDecimal("100"));
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(fd);
        df.setMinimumFractionDigits(fd);
        setValue(df.format(v));
    }

    /**
     * 将一个字符串数值设置到Field中，如果字符串中有","会去除掉
     *
     * @param number
     */
    public void setStringNumberValue(String number) {
        setValue(number.replaceAll(",", ""));
    }

    /**
     * 将一个字符串数值设置到Field中，如果字符串中有","会去除掉
     * 可以控制小数位数
     *
     * @param number
     * @param fd
     */
    public void setStringNumberValue(String number, int fd) {
        try {
            setBigDecimalStringValue(new BigDecimal(number.replaceAll(",", "")), fd);
        } catch (Exception ex) {
            throw new FieldValueException(this, "传进来的值无法解析成数值", ex);
        }
    }

    /**
     * 将一个字符串数值做一个万到元的转换再设置Field的值
     * 如果字符串是空将直接设Field的值为空,如果字符串中有逗号会过滤掉
     *
     * @param number
     */
    public void setStringNumberValue_WY(String number) {
        if (!number.equals("")) {
            try {
                setBigDecimalStringValue_WY(new BigDecimal(number.replaceAll(",", "")));
            } catch (Exception ex) {
                throw new FieldValueException(this, "传进来的值无法解析成数值", ex);
            }
        } else {
            setValue(number);
        }
    }

    /**
     * 将一个字符串数值做一个万到元的转换再设置Field的值
     * 如果字符串是空将直接设Field的值为空,如果字符串中有逗号会过滤掉
     * 可以控制小数位数
     *
     * @param number
     * @param fd
     */
    public void setStringNumberValue_WY(String number, int fd) {
        if (!number.equals("")) {
            try {
                setBigDecimalStringValue_WY(new BigDecimal(number.replaceAll(",", "")), fd);
            } catch (Exception ex) {
                throw new FieldValueException(this, "传进来的值无法解析成数值", ex);
            }
        } else {
            setValue(number);
        }
    }

    /**
     * 将一个字符串数值做一个百分比到小数的转换再设置Field的值
     * 如果字符串是空将直接设Field的值为空,如果字符串中有逗号会过滤掉
     *
     * @param number
     */
    public void setStringNumberValue_PF(String number) {
        if (!number.equals("")) {
            try {
                setBigDecimalStringValue_PF(new BigDecimal(number.replaceAll(",", "")));
            } catch (Exception ex) {
                throw new FieldValueException(this, "传进来的值无法解析成数值", ex);
            }
        } else {
            setValue(number);
        }
    }

    /**
     * 将一个字符串数值做一个百分比到小数的转换再设置Field的值
     * 如果字符串是空将直接设Field的值为空,如果字符串中有逗号会过滤掉
     * 可以控制小数位数
     *
     * @param number
     * @param fd
     */
    public void setStringNumberValue_PF(String number, int fd) {
        if (!number.equals("")) {
            try {
                setBigDecimalStringValue_PF(new BigDecimal(number.replaceAll(",", "")), fd);
            } catch (Exception ex) {
                throw new FieldValueException(this, "传进来的值无法解析成数值", ex);
            }
        } else {
            setValue(number);
        }
    }

    /**
     * 获得Field值的日期字符串形式（格式为yyyy-MM-dd）
     * 如果Field值本更不是日期会抛出异常
     *
     * @return
     */
    public String getDateStringValue() {
        return getDateStringValue("yyyy-MM-dd");
    }

    /**
     * 获得Field值的日期字符串形式（格式可以自定义）
     * 如果Field值本更不是日期会抛出异常
     *
     * @param format
     * @return
     */
    public String getDateStringValue(String format) {
        Date date = getDateValue();
        return DateUtil.getDateString(date, format);
    }

    /**
     * 获得Field值的日期类型的值
     * 如果Field值本更不是日期会抛出异常
     *
     * @return
     */
    public Date getDateValue() {
        if (value == null || value.equals("")) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        } else {
            Date date = Formatter.getDateN(value.toString());
            if (date == null) {
                date = Formatter.getDateFromIITime(value.toString());
            }
            if (date == null) {
                date = Formatter.getDateFromITime(value.toString());
            }
            if (date == null) {
                date = Formatter.getDateFromTime(value.toString());
            }
            if (date == null) {
                throw new FieldValueException(this, "Field中的值无法解析成Date");
            }
            return date;
        }

    }

    /**
     * 获得Field值的Number字符串形式
     * 如果Field值本更不是数值会抛出异常，但是如果Field值是空字符串会返回空字符串
     *
     * @return
     */
    public String getNumberStringValue() {
        if (getValue().equals("")) {
            return "";
        }
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(true);
        BigDecimal v = getBigDecimalValue();
        return df.format(v);
    }

    /**
     * 获得Field值的Number字符串形式
     * 会在单位上作一次元到万的转换
     * 如果Field值本更不是数值会抛出异常，但是如果Field值是空字符串会返回空字符串
     *
     * @return
     */
    public String getNumberStringValue_YW() {
        if (getValue().equals("")) {
            return "";
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(6);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(true);
        BigDecimal v = getBigDecimalValue_YW();
        return df.format(v);
    }

    /**
     * 获得Field值的Number字符串形式
     * 会在单位上作一次小数到百分比的转换
     * 如果Field值本更不是数值会抛出异常，但是如果Field值是空字符串会返回空字符串
     *
     * @return
     */
    public String getNumberStringValue_FP() {
        if (getValue().equals("")) {
            return "";
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(true);
        BigDecimal v = getBigDecimalValue_FP();
        return df.format(v);
    }

    /**
     * 获得Field值的Number字符串形式（小数位数自定义）
     * 如果Field值本更不是数值会抛出异常，但是如果Field值是空字符串会返回空字符串
     *
     * @param fd
     * @return
     */
    public String getNumberStringValue(int fd) {
        if (getValue().equals("")) {
            return "";
        }
        BigDecimal v = getBigDecimalValue();
        return Formatter.getCommonNumberFormat(v, fd);
    }

    /**
     * 获得Field值的Number字符串形式
     * 会在单位上作一次元到万的转换,可以对转换后的值设定小数位数
     * 如果Field值本更不是数值会抛出异常，但是如果Field值是空字符串会返回空字符串
     *
     * @param fd
     * @return
     */
    public String getNumberStringValue_YW(int fd) {
        if (getValue().equals("")) {
            return "";
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(fd);
        df.setMinimumFractionDigits(fd);
        df.setGroupingUsed(true);
        BigDecimal v = getBigDecimalValue_YW();
        return df.format(v);
    }

    /**
     * 获得Field值的Number字符串形式
     * 会在单位上作一次小数到百分比的转换,可以对转换后的值设定小数位数
     * 如果Field值本更不是数值会抛出异常，但是如果Field值是空字符串会返回空字符串
     *
     * @param fd
     * @return
     */
    public String getNumberStringValue_FP(int fd) {
        if (getValue().equals("")) {
            return "";
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(fd);
        df.setMinimumFractionDigits(fd);
        df.setGroupingUsed(true);
        BigDecimal v = getBigDecimalValue_FP();
        return df.format(v);
    }

    /**
     * 获得Field值的Double类型值
     * 如果Field值本更不是数值会抛出异常
     *
     * @return
     */
    public double getDoubleValue() {
        if (value == null || value.equals("")) {
            return Double.NaN;
        }
        if (value instanceof Double) {
            return (Double) value;
        } else {
            try {
                return Double.parseDouble(value.toString().replaceAll(",", ""));
            } catch (Exception ex) {
                throw new FieldValueException(this, "Field中的值无法解析成Double", ex);
            }
        }
    }

    /**
     * 获得Field值的Double类型值,会在单位上作一次元到万的转换
     *
     * @return
     */
    public double getDoubleValue_YW() {
        return getDoubleValue() / 10000;
    }

    /**
     * 获得Field值的Double类型值,会在单位上作一次小数到百分比的转换
     *
     * @return
     */
    public double getDoubleValue_FP() {
        return getDoubleValue() * 100;
    }

    /**
     * 获得Field值的BigDecimal类型值
     * 如果Field值本更不是数值会抛出异常
     *
     * @return
     */
    public BigDecimal getBigDecimalValue() {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            try {
                return new BigDecimal(value.toString());
            } catch (Exception ex) {
                throw new FieldValueException(this, "Field中的值无法解析成Decimal", ex);
            }
        }
    }

    /**
     * 获得Field值的BigDecimal类型值,会在单位上作一次元到万的转换
     *
     * @return
     */
    public BigDecimal getBigDecimalValue_YW() {
        return getBigDecimalValue().divide(new BigDecimal("10000"));
    }

    /**
     * 获得Field值的BigDecimal类型值,会在单位上作一次小数到百分比的转换
     *
     * @return
     */
    public BigDecimal getBigDecimalValue_FP() {
        return getBigDecimalValue().multiply(new BigDecimal("100"));
    }

    /**
     * 获得Field值的整数类型值
     * 如果Field值本更不是整数数值会抛出异常
     *
     * @return
     */
    public int getIntValue() {
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            try {
                return Integer.parseInt(value.toString().replaceAll(",", ""));
            } catch (Exception ex) {
                throw new FieldValueException(this, "Field中的值无法解析成整数", ex);
            }
        }
    }

    /**
     * 获得Field值的逻辑类型值
     *
     * @return
     */
    public boolean getBooleanValue() {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return value.toString().equals("Y") || value.toString().equals("1");
        }
    }

    /**
     * Field相等的定义
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Field) {
            if (this.getName() == null || ((Field) obj).getName() == null) {
                //字段名都不存在无法比较
                return false;
            } else if (this.getValue() == null || ((Field) obj).getValue() == null) {
                return this.getName().equals(((Field) obj).getName()) && this.getValue() == ((Field) obj).getValue();
            } else {
                return this.getName().equals(((Field) obj).getName())
                        && this.getValue().equals(((Field) obj).getValue());
            }
        }
        return false;
    }

    /**
     * 哈希码的定义
     */
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (getName() == null ? 0 : getName().hashCode());
        hash = 31 * hash + (getValue() == null ? 0 : getValue().hashCode());
        return hash;
    }

    /**
     * 复制自己
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    /**
     * 重新定义toString
     */
    public String toString() {
        return name + ": " + (value != null ? value.toString() : "");
    }
}
