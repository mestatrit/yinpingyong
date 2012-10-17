package com.mt.common.dynamicDataDef.comparator;

import com.mt.common.dynamicDataDef.Field;
import com.mt.common.gui.PinyinHandler;

/**
 * FieldMap字符串比较器
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2010-5-11
 * Time: 19:44:31
 * To change this template use File | Settings | File Templates.
 * <p/>
 */
public class FieldMapStringComparator extends FieldMapComparator {

    public FieldMapStringComparator(String fName, boolean descending) {
        super(fName, descending);
    }

    public int compare(Field f1, Field f2) {
        String f1py = PinyinHandler.getMixPy(f1.getStringValue());
        String f2py = PinyinHandler.getMixPy(f2.getStringValue());
        f2.getStringValue();
        return f1py.compareTo(f2py);
    }

}
