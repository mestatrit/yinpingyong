package org.sunside.core.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:Ryan
 * @date:2013-4-1
 */
public class HashCodeTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<Person, String> map = new HashMap<Person, String>();
		map.put(new Person("测试"), "测试");
		List<Person> list = new ArrayList<Person>();
		list.add(new Person("测试"));
		
		/**
		 * false:
		 * 因为先通过对象hashcode，找到对象存放地址；在取出地址下的值
		 * 因为没有覆写haosecode，所以二个对象生成不同的hashcode
		 */
		System.out.println(map.containsKey(new Person("测试")));
		// true
		System.out.println(list.contains(new Person("测试")));
	}
	
	static class Person{
		String name = "";
		public Person(String name) {
			this.name = name;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Person){
				return ((Person)obj).name.equals(name);
			}
			return false;	
		}
	}

}
