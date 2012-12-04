package org.sunside.core.annotation;

import java.lang.reflect.Method;

/**
 * 注解测试类
 * @author:Ryan
 * @date:2012-12-5
 */
@MyAnnotation(value="Class:TestController",multiValues={"1","2"})
public class TestMyAnnotation {

	@MyAnnotation(value="Method:method1",multiValues={"3"},number=1)
	public void method1() {
		
	}
	
	public static void main(String[] args) {
		
		boolean flag = TestMyAnnotation.class.isAnnotationPresent(MyAnnotation.class);
		if (flag == true) {
			MyAnnotation controller = TestMyAnnotation.class.getAnnotation(MyAnnotation.class);
			printMyAnnotation3(controller);
		}
		
		System.out.println("===========================");
		
		Method[] methods = TestMyAnnotation.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(MyAnnotation.class)) {
				printMyAnnotation3(method.getAnnotation(MyAnnotation.class));
			}
		}
	}
	
	private static void printMyAnnotation3(MyAnnotation annotation3) {
        if (annotation3 == null ) {
            return;
        }
        
        System.out.println("{value= " + annotation3.value());
        
        String multiValues = "";
        for (String value: annotation3.multiValues()) {
            multiValues += ", " + value;
        }
        
        System.out.println("multiValues= " + multiValues);
        
        System.out.println("number= " + annotation3.number() + "} ");
    }
}
