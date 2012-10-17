/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mt.common.localStore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * SQL访问的一个简单定义
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-7-17
 * <p/>
 */
public interface SQLDao {

    public boolean isExist_Table(String tableName);

    public boolean createTable(Class classV, String tableName);

    public boolean isExist_Column(String tableName, String column);

    public void setAutoCommit(boolean isAuto);

    public void commit();

    public void rollback();

    public boolean executeSQL(String sql);

    public boolean executeSQL(PreparedStatement sql);

    public PreparedStatement prepareStatement(String sql);

    public ResultSet query(String sql);

    public List query(String sql, Class classV);

    public List query(String sql, Class classV, Map<String, String> ffMap);

    public ResultSet query(PreparedStatement sql);

    public List query(PreparedStatement sql, Class classV);

    public List query(PreparedStatement sql, Class classV, Map<String, String> ffMap);

    public int update(String sql);

    public int update(PreparedStatement sql);

    public void update(List data, Class classV, String tableName, String fName);

    public void update(List data, Class classV, Map<String, String> ffMap, String tableName, String fName);

    public void insert(List data, Class classV, String tableName);

    public void insert(List data, Class classV, Map<String, String> ffMap, String tableName);

    public void close();

}
