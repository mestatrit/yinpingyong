package com.mt.common.dynamicDataDef.comparator;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.dynamicDataDef.FieldMap;

import java.util.Comparator;

/**
 * FieldMap比较器的抽象定义
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-5-11
 * Time: 19:26:06
 * To change this template use File | Settings | File Templates.
 * <p/>
 */
public abstract class FieldMapComparator implements Comparator {

    protected String fName;
    protected boolean descending;

    public FieldMapComparator(String fName, boolean descending) {
        this.fName = fName;
        this.descending = descending;
    }

    public int compare(Object o1, Object o2) {
        FieldMap fmo1 = (FieldMap) o1;
        FieldMap fmo2 = (FieldMap) o2;

        Field f1 = fmo1.getField(fName);
        Field f2 = fmo2.getField(fName);

        int comparison = 0;
        if (f1 != null && f2 == null) {
            comparison = 1;
        } else if (f1 == null && f2 == null) {
            comparison = 0;
        } else if (f1 == null && f2 != null) {
            comparison = -1;
        } else {
            comparison = compare(f1, f2);
        }

        return descending ? -comparison : comparison;
    }

    abstract public int compare(Field f1, Field f2);
}
