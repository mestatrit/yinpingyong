package org.sunside.core.generics;

import java.util.HashMap;
import java.util.Map;

import com.mt.common.dynamicDataDef.FieldMapSet;

/**
 * 泛型示例
 * @author:Ryan
 * @date:2012-11-7
 */
public class GenericsTester {
	
	public static void main(String[] args) {
		MyGenerics<FieldMapSet> impl1 = new MyImpl<FieldMapSet>();
		System.out.println(impl1.getFieldStr(new FieldMapSet("")));
		
		MyGenerics<Map> impl2 = new MyImpl<Map>();
		System.out.println(impl2.getFieldStr(new HashMap()));
		
	}
}
