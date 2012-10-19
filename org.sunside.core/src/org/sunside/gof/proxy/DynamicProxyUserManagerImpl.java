package org.sunside.gof.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态实现类 
 */
public class DynamicProxyUserManagerImpl implements InvocationHandler{
	
	private Object target;
	
	/**
	 * 绑定真实实现角色
	 * @param target
	 * @return
	 */
	public Object bind(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("事物开始...");
		
		Object result = method.invoke(target, args);
		
		System.out.println("事物结束.");
		
		return result;
	}

}
