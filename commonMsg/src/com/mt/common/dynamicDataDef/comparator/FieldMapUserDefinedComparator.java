package com.mt.common.dynamicDataDef.comparator;

import com.mt.common.dynamicDataDef.Field;

import java.util.HashMap;

/**
 * 一个可以用户自定义字段的比较器
 */
public class FieldMapUserDefinedComparator extends FieldMapComparator {

    private HashMap<String, Integer> userDefinedMap;

    public FieldMapUserDefinedComparator(HashMap<String, Integer> userDefinedMap, String fname, boolean descending) {
        super(fname, descending);
        this.userDefinedMap = userDefinedMap;
    }

    @Override
    public int compare(Field f1, Field f2) {
        Object value1 = f1.getValue();
        Object value2 = f2.getValue();

        if (!userDefinedMap.containsKey(value2) && !userDefinedMap.containsKey(value1)) {
            return value1.toString().compareTo(value2.toString());
        } else if (userDefinedMap.containsKey(value1) && !userDefinedMap.containsKey(value2)) {
            return -1;
        } else if (!userDefinedMap.containsKey(value1) && userDefinedMap.containsKey(value2)) {
            return 1;
        } else {
            Integer index1 = userDefinedMap.get(value1);
            Integer index2 = userDefinedMap.get(value2);
            if (index1 < index2) return -1;
            else if (index1 > index2) return 1;
            else return 0;
        }
    }

}
