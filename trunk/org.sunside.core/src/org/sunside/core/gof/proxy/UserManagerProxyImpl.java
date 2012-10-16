package org.sunside.core.gof.proxy;

/**
 * 代理实现角色(静态代理实现)
 */
public class UserManagerProxyImpl implements UserManager {

	private UserManager userManager;
	
	public UserManagerProxyImpl(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Override
	public boolean addUser(String userName) {
		System.out.println("add user before...");
		
		return userManager.addUser(userName);	
	}

	@Override
	public boolean removeUser(Integer id) {
		System.out.println("remove user before...");
		
		return userManager.removeUser(id);
	}

}
