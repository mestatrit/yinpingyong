package org.sunside.core.generics;

/**
 * @author:Ryan
 * @date:2013-4-14
 */
public class GenericsTest<T> {
	
	public void testGenerics(T args) {
		if (args instanceof String) {
			System.out.println("args is string");
		} 
	}
	
	public static void main(String[] args) {
		/**
		 * GenericsTest t = new GenericsTest();只是产生告警信息，编译依然可以通过；
		 * 最好的定义方式，创建对象时指定泛型类型.
		 * GenericsTest<String> t = new GenericsTest<String>();
		 */
		GenericsTest t = new GenericsTest();
		t.testGenerics("1");
	}

}
