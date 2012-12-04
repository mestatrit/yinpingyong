package org.sunside.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 注释类型所适用的程序元素的种类：类（接口）、方法
 * 注释类型的注释要保留多久：运行时刻
 * @author:Ryan
 * @date:2012-12-5
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
	
	public String value();
	
    public String[] multiValues();
    
    public int number() default 0;
	
}
