package org.sunside.frame.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author:Ryan
 * @date:2013-1-5
 */
public class TestAll extends TestCase {

	public static Test suite() {
		
		TestSuite suite = new TestSuite();
		
		// 加入你所想测试的测试类
		suite.addTestSuite(CalculatorTest.class);
		
		return suite;
	}

}
