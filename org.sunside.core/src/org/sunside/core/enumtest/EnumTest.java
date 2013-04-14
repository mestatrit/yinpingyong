package org.sunside.core.enumtest;

/**
 * @author:Ryan
 * @date:2013-4-14
 */
public class EnumTest {

	public void testEnum(Season session) {
		switch (session) {
		case Spring:
			System.out.println(session);
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		EnumTest t = new EnumTest();
		t.testEnum(Season.Spring);
	}

}

enum Season {
	Spring,
	Summer,
	Autumn,
	Winter
}
