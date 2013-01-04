package org.sunside.frame.junit;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author:Ryan
 * @date:2013-1-5
 */
public class CalculatorTest extends TestCase {

	/**
	 * 在Junit3.8中，测试方法满足如下原则 
	 * 1)public 
	 * 2)void 
	 * 3)无方法参数 
	 * 4)最重要的方法名称必须以test开头
	 */
	private Calculator cal;

	// 在执行每个test之前，都执行setUp；
	public void setUp() {
		cal = new Calculator();
	}

	// 在执行每个test之后，都要执行tearDown
	public void tearDown() {

	}

	public void testAdd() {
		int result = cal.add(1, 2);
		Assert.assertEquals(3, result);
	}

	public void testMultiply() {
		int result = cal.multiply(4, 2);
		Assert.assertEquals(8, result);
	}

}
