package org.sunside.gof.proxy;

/**
 * 交叉事物角色 
 */
public class SecurityManager {

	public boolean checkSecurity(String userName) {
		System.out.println("check security:" + userName);
		
		return true;
	}
	
}
