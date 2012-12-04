package org.sunside.core.generics;

import java.util.Map;

import com.mt.common.dynamicDataDef.FieldMapSet;

/**
 * @author:Ryan
 * @date:2012-11-7
 */
public class MyImpl<T> extends MyGenerics<T> {

	@Override
	public String getFieldStr(T arg) {
		if (arg instanceof Map) {
			return "hashMap";
		} else if (arg instanceof FieldMapSet) {
			return "FieldMapSet";
		} else { 
			return "unknown";
		}
	}

}
