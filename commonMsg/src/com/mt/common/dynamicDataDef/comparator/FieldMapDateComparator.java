package com.mt.common.dynamicDataDef.comparator;

import com.mt.common.dynamicDataDef.Field;

import java.util.Date;

/**
 * FieldMap日期比较器
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-5-11
 * Time: 19:45:06
 * To change this template use File | Settings | File Templates.
 * <p/>
 */
public class FieldMapDateComparator extends FieldMapComparator {

    public FieldMapDateComparator(String fName, boolean descending) {
        super(fName, descending);
    }

    public int compare(Field f1, Field f2) {
        try {
            Date d1 = f1.getDateValue();
            Date d2 = f2.getDateValue();
            return d1.compareTo(d2);
        } catch (Exception e) {
            //解析日期失败，采用字符串比较
            return f1.getValue().toString().compareTo(f2.getValue().toString());
        }
    }
}
