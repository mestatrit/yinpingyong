package org.sunside.gof.proxy;

public class ClientTester {

	public static void main(String[] args) {
		
		UserManager userManager = new UserManagerImpl();
		
		UserManager userManagerProxy = new UserManagerProxyImpl(userManager);
		
		userManagerProxy.addUser("Ryan");
	}
}
