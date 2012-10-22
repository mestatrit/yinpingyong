package org.sunside.test.ibatis;

import java.io.IOException;
import java.sql.SQLException;

import org.sunside.frame.ibatis.repository.SqlMapClientManager;
import org.sunside.frame.ibatis.repository.dao.SysUserDAO;
import org.sunside.frame.ibatis.repository.dao.SysUserDAOImpl;
import org.sunside.frame.ibatis.repository.model.SysUserExample;
import org.sunside.frame.ibatis.repository.model.SysUserExample.Criteria;

/**
 * @author:Ryan
 * @date:2012-10-22
 */
public class UserManager {

	public static void main(String[] args) {
		try {
			SysUserDAO sysUserDAO = new SysUserDAOImpl(SqlMapClientManager.getInstance().getSqlMapClient());
			
			SysUserExample example = new SysUserExample();
			
			Criteria criteria = example.createCriteria();
			
			criteria.andIdEqualTo(1);
			
			System.out.println(sysUserDAO.selectByExample(example));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			
			System.err.println("无法找到sqlMapConfig.xml文件，请核实!"+e.getStackTrace());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			
			System.err.println("执行SQL出现异常!"+e.getStackTrace());
		}
	}

}
