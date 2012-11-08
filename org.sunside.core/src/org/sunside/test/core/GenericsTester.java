package org.sunside.test.core;

import java.util.HashMap;

import com.mt.common.dynamicDataDef.FieldMapSet;

/**
 * @author:Ryan
 * @date:2012-11-7
 */
public class GenericsTester {
	
	public static void main(String[] args) {
		MyGenerics impl = new MyImpl();
		System.out.println(impl.getFieldStr(new FieldMapSet("")));
		System.out.println(impl.getFieldStr(new HashMap()));
		
	}
}
