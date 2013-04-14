package org.sunside.core.generics;

/**
 * @author:Ryan
 * @date:2013-4-14
 */
public class 协变 {

	public static void main(String[] args) {
		Father1 f1 = new Son1();
		System.out.println(f1.m1());
	}
}

class Father1 {
	
	public Number m1() {
		return 0;
	}
}

class Son1 extends Father1 {
	/**
	 * 子类返回类型范围，比父类小；
	 */
	@Override
	public Integer m1() {
		return 1;
	}
}
