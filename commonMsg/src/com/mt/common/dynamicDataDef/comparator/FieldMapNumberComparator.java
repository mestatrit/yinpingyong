package com.mt.common.dynamicDataDef.comparator;

import com.mt.common.dynamicDataDef.Field;

/**
 * FieldMap数字比较器
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-5-11
 * Time: 19:25:06
 * To change this template use File | Settings | File Templates.
 * <p/>
 */
public class FieldMapNumberComparator extends FieldMapComparator {

    public FieldMapNumberComparator(String fName, boolean descending) {
        super(fName, descending);
    }

    public int compare(Field f1, Field f2) {
        try {
            double dn1 = f1.getDoubleValue();
            double dn2 = f2.getDoubleValue();
            if (dn1 > dn2) {
                return 1;
            } else if (dn1 < dn2) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            //解析数值失败，采用字符串比较
            return f1.getValue().toString().compareTo(f2.getValue().toString());
        }

    }
}
