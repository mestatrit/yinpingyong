package org.sunside.frame.ibatis.repository;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * @author:Ryan
 * @date:2012-10-22
 */
public class SqlMapClientManager {
	
	private static SqlMapClientManager instance;
	
	private static SqlMapClient sqlMapClient;
	
	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
	
	public static synchronized SqlMapClientManager getInstance() throws IOException{
		if (instance == null) {
			
			instance = new SqlMapClientManager();
			
			Reader reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		}
		
		return instance;
	}
	
	private SqlMapClientManager() {
		
	}
}
