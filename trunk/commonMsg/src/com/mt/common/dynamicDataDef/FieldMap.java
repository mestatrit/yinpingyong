package com.mt.common.dynamicDataDef;

import java.math.BigDecimal;
import java.util.*;

/**
 * 一个定义字段集合的类
 *
 * @author hanhui
 */
public class FieldMap {

    /**
     * FieldMap的名字
     */
    private String name;
    /**
     * Field哈希表
     */
    private Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
    /**
     * 自定义toString
     */
    private FieldMapCustomToString fmToStr;

    public FieldMap(String name) {
        this.name = name;
    }

    /**
     * 设置FieldMap的名字
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得FieldMap的名字
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 通过Field的名字得到Field
     *
     * @param fieldName
     * @return
     */
    public Field getField(String fieldName) {
        return fieldMap.get(fieldName);
    }

    /**
     * 将字段名转换成大写后再去获取Field
     *
     * @param fieldName
     * @return
     */
    public Field getField_Upper(String fieldName) {
        return getField(fieldName.toUpperCase());
    }

    /**
     * 将字段名转换成小写后再去获取Field
     *
     * @param fieldName
     * @return
     */
    public Field getField_Lower(String fieldName) {
        return getField(fieldName.toLowerCase());
    }

    /**
     * 增加一个Field
     *
     * @param f
     */
    public FieldMap addField(Field f) {
        fieldMap.put(f.getName(), f);
        return this;
    }

    /**
     * 一种快捷加入Field的方法
     *
     * @param name
     * @param value
     */
    public FieldMap putField(String name, Object value) {
        return addField(new Field(name, value));
    }

    /**
     * 增加一批Field
     *
     * @param fields
     * @return
     */
    public FieldMap addFieldList(List<Field> fields) {
        for (Field f : fields) {
            addField(f);
        }
        return this;
    }

    /**
     * 移除Field
     *
     * @param f
     * @return
     */
    public FieldMap removeField(Field f) {
        fieldMap.remove(f.getName());
        return this;
    }

    /**
     * 通过name移除Field
     *
     * @param fieldName
     */
    public FieldMap removeField(String fieldName) {
        fieldMap.remove(fieldName);
        return this;
    }

    /**
     * 通过某个index移除某个field
     *
     * @param index
     */
    @SuppressWarnings("unchecked")
	public FieldMap removeField(int index) {
        String key = (String) ((Map.Entry) fieldMap.entrySet().toArray()[index]).getKey();
        return removeField(key);
    }

    /**
     * 删除一批Field
     *
     * @param fields
     */
    public FieldMap removeFieldList(List<Field> fields) {
        for (Field f : fields) {
            removeField(f);
        }
        return this;
    }

    /**
     * 复制一份FieldMap
     *
     * @return
     */
    @SuppressWarnings("unchecked")
	public FieldMap copyFieldMap() {
        FieldMap fm = new FieldMap(this.getName());
        Iterator i = fieldMap.entrySet().iterator();
        for (; i.hasNext(); ) {
            Field f = (Field) ((Map.Entry) i.next()).getValue();
            fm.addField((Field) f.clone());
        }
        return fm;
    }

    /**
     * 更新fm中的field至原fieldmap
     *
     * @param fm
     */
    public void updateFieldMap(FieldMap fm) {
        Iterator<String> ior = fm.fieldMap.keySet().iterator();
        while (ior.hasNext()) {
            String fieldName = ior.next();
            putField(fieldName, fm.getFieldValue(fieldName));
        }
    }


    /**
     * 获得Field的数量
     *
     * @return
     */
    public int getFieldCount() {
        return fieldMap.size();
    }

    /**
     * 判断是否设置了某些名字的Field
     *
     * @param names
     * @return
     */
    public boolean isSetField(String... names) {
        for (String n : names) {
            if (getField(n) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获得FieldMap的List形式
     *
     * @return
     */
    public List<Field> toFieldList() {
        return Arrays.asList(toFieldArray());
    }

    /**
     * 获得FieldMap的数组形式
     *
     * @return
     */
    public Field[] toFieldArray() {
        if (getFieldCount() == 0) {
            return new Field[0];
        } else {
            Field[] fieldArray = new Field[getFieldCount()];
            fieldMap.values().toArray(fieldArray);
            return fieldArray;
        }
    }

    /**
     * 通过Field的名字得到Field的值
     *
     * @param fieldName
     * @return
     */
    public Object getFieldValue(String fieldName) {
        Field f = getField(fieldName);
        if (f == null) {
            return null;
        }
        return f.getValue();
    }

    /**
     * 通过Field的名字得到Field的字符串值
     *
     * @param fieldName
     * @return
     */
    public String getStringValue(String fieldName) {
        Field f = getField(fieldName);
        if (f != null) {
            Object o = f.getValue();
            if (o != null) {
                return o.toString();
            }
        }
        return "";
    }

    /**
     * 通过Field的名字得到Field的日期值(yyyy-MM-dd)
     *
     * @param fieldName
     * @return
     */
    public String getDateStringValue(String fieldName) {
        Field f = getField(fieldName);
        if (f != null) {
            return f.getDateStringValue();
        } else {
            return "";
        }
    }

    /**
     * 通过Field的名字得到Field的日期值,可以指定格式
     *
     * @param fieldName
     * @param format
     * @return
     */
    public String getDateStringValue(String fieldName, String format) {
        Field f = getField(fieldName);
        if (f != null) {
            return f.getDateStringValue(format);
        } else {
            return "";
        }
    }

    /**
     * 通过Field的名字得到Field的日期值,Date类型
     *
     * @param fieldName
     * @return
     */
    public Date getDateValue(String fieldName) {
        Field f = getField(fieldName);
        if(f != null){
            return f.getDateValue();
        }else{
            return null;
        }
    }

    /**
     * 通过Field的名字得到Field的Double字符串值，可以指定小数位数
     *
     * @param fieldName
     * @param fd
     * @return
     */
    public String getNumberStringValue(String fieldName, int fd) {
        Field f = getField(fieldName);
        if (f != null) {
            return f.getNumberStringValue(fd);
        } else {
            return "";
        }
    }

    /**
     * 通过Field的名字得到Field的Double字符串值
     *
     * @param fieldName
     * @return
     */
    public String getNumberStringValue(String fieldName) {
        Field f = getField(fieldName);
        if (f != null) {
            return f.getNumberStringValue();
        } else {
            return "";
        }
    }

    /**
     * 通过Field的名字得到Field的Double字符串值，可以指定小数位数(作元到万转换)
     * @param fieldName
     * @param fd
     * @return
     */
    public String getNumberStringValue_YW(String fieldName, int fd) {
        Field f = getField(fieldName);
        if (f != null) {
            return f.getNumberStringValue_YW(fd);
        } else {
            return "";
        }
    }

    /**
     * 通过Field的名字得到Field的Double字符串值(作元到万转换)
     * @param fieldName
     * @return
     */
    public String getNumberStringValue_YW(String fieldName) {
        Field f = getField(fieldName);
        if (f != null) {
            return f.getNumberStringValue_YW();
        } else {
            return "";
        }
    }

    /**
     * 通过Field的名字得到Field的Double类型值
     *
     * @param fieldName
     * @return
     */
    public double getDoubleValue(String fieldName) {
        return getField(fieldName).getDoubleValue();
    }

    /**
     * 通过Field的名字得到Field的Int类型值
     *
     * @param fieldName
     * @return
     */
    public int getIntValue(String fieldName) {
        return getField(fieldName).getIntValue();
    }

    /**
     * 通过Field的名字得到Field的Boolean类型值
     *
     * @param fieldName
     * @return
     */
    public boolean getBooleanValue(String fieldName) {
        return getField(fieldName).getBooleanValue();
    }

    /**
     * 一种快捷加入Field的方法,将double转换为字符串值设置到Field中
     *
     * @param name
     * @param value
     */
    public FieldMap putDoubleStringValueField(String name, double value) {
        Field field = new Field(name);
        field.setDoubleStringValue(value);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将double转换为字符串值设置到Field中
     * 在单位上会作一次万到元的转换
     *
     * @param name
     * @param value
     * @return
     */
    public FieldMap putDoubleStringValueField_WY(String name, double value) {
        Field field = new Field(name);
        field.setDoubleStringValue_WY(value);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将double转换为字符串值设置到Field中
     * 在单位上会作一次百分比到小数的转换
     *
     * @param name
     * @param value
     * @return
     */
    public FieldMap putDoubleStringValueField_PF(String name, double value) {
        Field field = new Field(name);
        field.setDoubleStringValue_PF(value);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将double转换为字符串值设置到Field中,可以控制小数位数
     *
     * @param name
     * @param value
     * @param fd
     */
    public FieldMap putDoubleStringValueField(String name, double value, int fd) {
        Field field = new Field(name);
        field.setDoubleStringValue(value, fd);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将double转换为字符串值设置到Field中,可以控制小数位数
     * 在单位上会作一次万到元的转换
     *
     * @param name
     * @param value
     * @param fd
     * @return
     */
    public FieldMap putDoubleStringValueField_WY(String name, double value, int fd) {
        Field field = new Field(name);
        field.setDoubleStringValue_WY(value, fd);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将double转换为字符串值设置到Field中,可以控制小数位数
     * 在单位上会作一次百分比到小数的转换
     *
     * @param name
     * @param value
     * @param fd
     * @return
     */
    public FieldMap putDoubleStringValueField_PF(String name, double value, int fd) {
        Field field = new Field(name);
        field.setDoubleStringValue_PF(value, fd);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将BigDecimal转换为字符串值设置到Field中
     *
     * @param name
     * @param bd
     * @return
     */
    public FieldMap putBigDecimalStringValueField(String name, BigDecimal bd) {
        Field field = new Field(name);
        field.setBigDecimalStringValue(bd);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将BigDecimal转换为字符串值设置到Field中
     * 在单位上会作一次万到元的转换
     *
     * @param name
     * @param bd
     * @return
     */
    public FieldMap putBigDecimalStringValueField_WY(String name, BigDecimal bd) {
        Field field = new Field(name);
        field.setBigDecimalStringValue_WY(bd);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将BigDecimal转换为字符串值设置到Field中
     * 在单位上会作一次百分比到小数的转换
     *
     * @param name
     * @param bd
     * @return
     */
    public FieldMap putBigDecimalStringValueField_PF(String name, BigDecimal bd) {
        Field field = new Field(name);
        field.setBigDecimalStringValue_PF(bd);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将数字字符串在单位上作一次万到元的转换后设置到Field中
     *
     * @param name
     * @param number
     * @return
     */
    public FieldMap putStringNamberField_WY(String name, String number) {
        Field field = new Field(name);
        field.setStringNumberValue_WY(number);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将数字字符串在单位上作一次百分比到小数的转换后设置到Field中
     *
     * @param name
     * @param number
     * @return
     */
    public FieldMap putStringNumberField_PF(String name, String number) {
        Field field = new Field(name);
        field.setStringNumberValue_PF(number);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将整数转换为字符串值设置到Field中
     *
     * @param name
     * @param value
     */
    public FieldMap putIntStringValueField(String name, int value) {
        Field field = new Field(name);
        field.setIntStringValue(value);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将日期转换为字符串值设置到Field中，格式为yyyyMMdd
     *
     * @param name
     * @param value
     */
    public FieldMap putDateStringValueField(String name, Date value) {
        Field field = new Field(name);
        field.setDateStringValue(value);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将日期转换为字符串值设置到Field中，格式可以配置
     *
     * @param name
     * @param value
     * @param format
     */
    public FieldMap putDateStringValueField(String name, Date value, String format) {
        Field field = new Field(name);
        field.setDateStringValue(value, format);
        return addField(field);
    }

    /**
     * 一种快捷加入Field的方法,将一个字符串日期按format转换再设置Field中
     *
     * @param name
     * @param value
     * @param format
     * @return
     */
    public FieldMap putStringDateField(String name, String value, String format) {
        Field field = new Field(name);
        field.setStringDateValue(value, format);
        return addField(field);
    }

    /**
     * 外部配置一个自定义的toString格式
     *
     * @param fmd
     */
    public FieldMap setFieldMapCustomToString(FieldMapCustomToString fmd) {
        this.fmToStr = fmd;
        return this;
    }

    /**
     * FieldMap的相等定义
     */
    public boolean equals(Object obj) {
        if (obj instanceof FieldMap) {
            if (!getName().equals(((FieldMap) obj).getName())) {
                return false;
            }
            if (getFieldCount() == ((FieldMap) obj).getFieldCount()) {
                Field[] f1 = toFieldArray();
                Field[] f2 = ((FieldMap) obj).toFieldArray();
                for (int i = 0; i < f1.length; i++) {
                    if (!f1[i].equals(f2[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 哈希码的定义
     */
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getName().hashCode();
        Field[] fs = toFieldArray();
        for (Field f : fs) {
            hash = 31 * hash + f.hashCode();
        }
        return hash;
    }

    /**
     * 定义toString()
     */
    public String toString() {
        if (fmToStr != null) {
            return fmToStr.toString(this);
        }
        String rs = getName() + ":\r\n";
        Field[] fs = toFieldArray();
        for (Field f : fs) {
            rs += f.toString() + "\r\n";
        }
        return rs;
    }
}
