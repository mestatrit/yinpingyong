package org.sunside.gof.proxy;

/**
 * 抽象接口角色
 */
public interface UserManager {
	
	public boolean addUser(String userName);
	
	public boolean removeUser(Integer id);
}
