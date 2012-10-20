package org.sunside.core.object;

/**
 * 双精度十进制转成二进制：
 * 正数部分除2取余、小数部门乘2取整
 * 判断双精度，采用精度比较
 * @author:Ryan
 * @date:2012-10-20
 */
public class DoubleTester {

	public static void main(String[] args) {
		
		double temp = 2.2;
		
		System.out.println(temp - 2);
		
		System.out.println(0.2+1E-6);
		
		if (temp - 2 < 0.2+1E-6) {
			System.out.println(true);
		}
		
	}

}
