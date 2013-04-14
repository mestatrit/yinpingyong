package org.sunside.core.generics;

/**
 * @author:Ryan
 * @date:2013-4-14
 */
public class 逆变 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class Father2 {
	public void m1(Integer args) {
		System.out.println(0);
	}
}

class Son2 extends Father2 {
	
	/**
	 * 不构成覆写，只是逆变，m1只作为Son2的普通方法。
	 * 和Father2没有任何关系
	 */
	//@Override 
	public void m1(Number args) {
		System.out.println(0);
	}
}
