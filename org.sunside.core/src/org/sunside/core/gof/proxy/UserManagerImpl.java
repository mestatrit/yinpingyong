package org.sunside.core.gof.proxy;

/**
 * 真实实现角色 
 */
public class UserManagerImpl implements UserManager {

	@Override
	public boolean addUser(String userName) {
		System.out.println("add user:" + userName);
		
		return true;
	}

	@Override
	public boolean removeUser(Integer id) {
		System.out.println("remove user：" + id);
		
		return true;
	}

}
