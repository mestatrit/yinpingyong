package org.sunside.gof.proxy;

/**
 * 代理实现角色(静态代理实现)
 */
public class UserManagerProxyImpl implements UserManager {

	private UserManager userManager;
	
	private SecurityManager securityManager = new SecurityManager();
	
	public UserManagerProxyImpl(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Override
	public boolean addUser(String userName) {
		if (securityManager.checkSecurity(userName)){
			return userManager.addUser(userName);	
		}
		else 
		{
			System.out.println("权限检查不通过...");
			return false;
		}
	}

	@Override
	public boolean removeUser(Integer id) {
		return userManager.removeUser(id);
	}

}
