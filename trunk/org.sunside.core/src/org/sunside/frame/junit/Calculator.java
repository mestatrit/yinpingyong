package org.sunside.frame.junit;

/**
 * @author:Ryan
 * @date:2013-1-5
 */
public class Calculator {

	public int add(int a, int b) {
		return a + b;
	}

	public int multiply(int a, int b) {
		return a * b;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(CalculatorTest.class);
	}

}
