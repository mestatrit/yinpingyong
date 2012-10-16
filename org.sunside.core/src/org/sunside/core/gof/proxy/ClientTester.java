package org.sunside.core.gof.proxy;

public class ClientTester {

	public static void main(String[] args) {
		
		/**
		 * 静态代理
		 */
		UserManager userManagerImpl = new UserManagerImpl();
		
		UserManager userManagerProxy = new UserManagerProxyImpl(userManagerImpl);
		
		userManagerProxy.addUser("Ryan");
		
		/**
		 * 动态代理
		 */
		DynamicProxyUserManagerImpl dyanmicProxy = new DynamicProxyUserManagerImpl();
		
		UserManager userManagerDynamicProxy = (UserManager)dyanmicProxy.bind(userManagerImpl);
		
		userManagerDynamicProxy.addUser("Ryan");
	}
}
