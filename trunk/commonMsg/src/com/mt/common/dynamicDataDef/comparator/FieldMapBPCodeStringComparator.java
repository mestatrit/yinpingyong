/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.dynamicDataDef.comparator;

import com.mt.common.dynamicDataDef.Field;

/**
 * 针对字符串括号对里面的Code作字符串排序
 * Created by NetBeans.
 * Author: hanhui
 * Date:2010-4-12
 * Time:14:44:02
 */
public class FieldMapBPCodeStringComparator extends FieldMapComparator {

    public FieldMapBPCodeStringComparator(String fName, boolean descending) {
        super(fName, descending);
    }

    @Override
    public int compare(Field f1, Field f2) {
        String v1 = f1.getValue().toString();
        String v2 = f2.getValue().toString();
        int v1Index1 = v1.lastIndexOf("(");
        int v1Index2 = v1.lastIndexOf(")");
        int v2Index1 = v2.lastIndexOf("(");
        int v2Index2 = v2.lastIndexOf(")");
        if (v1Index1 >= 0 && v1Index2 >= 0 && v2Index1 >= 0 && v2Index2 >= 0) {
            String code1 = v1.substring(v1Index1, v1Index2);
            String code2 = v2.substring(v2Index1, v2Index2);
            return code1.compareTo(code2);
        } else if ((v1Index1 >= 0 && v1Index2 >= 0) && (v2Index1 < 0 || v2Index2 < 0)) {
            return -1;
        } else if ((v1Index1 < 0 || v1Index2 < 0) && (v2Index1 >= 0 && v2Index2 >= 0)) {
            return 1;
        } else {
            return v1.compareTo(v2);
        }
    }

}
