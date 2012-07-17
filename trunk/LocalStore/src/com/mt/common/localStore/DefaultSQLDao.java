/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.localStore;

import com.mt.common.dynamicDataDef.FieldMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * SQLDao的一个默认实现
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-7-17
 * <p/>
 */
public class DefaultSQLDao implements SQLDao {

    private Connection con = null;
    private Statement sttm = null;
    private final Logger logger = LoggerFactory.getLogger(DefaultSQLDao.class);

    public DefaultSQLDao(Connection con) {
        try {
            this.con = con;
            this.sttm = con.createStatement();
        } catch (Exception ex) {
            logger.error("创建SimpleSQLDAO失败", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public PreparedStatement prepareStatement(String sql) {
        try {
            return con.prepareStatement(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResultSet query(String sql) {
        try {
            return sttm.executeQuery(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List query(String sql, Class classV) {
        return query(sql, classV, null);
    }

    @Override
    public List query(String sql, Class classV, Map<String, String> ffMap) {
        return queryImp(sql, classV, ffMap);
    }

    @Override
    public ResultSet query(PreparedStatement sql) {
        try {
            return sql.executeQuery();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List query(PreparedStatement sql, Class classV) {
        return query(sql, classV, null);
    }

    @Override
    public List query(PreparedStatement sql, Class classV, Map<String, String> ffMap) {
        return queryImp(sql, classV, ffMap);
    }

    private List queryImp(Object sql, Class classV, Map<String, String> ffMap) {
        ResultSet rs = null;
        try {
            if (sql instanceof String) {
                rs = query((String) sql);
            } else {
                rs = query((PreparedStatement) sql);
            }
            List rList = new ArrayList();
            if (classV.isAssignableFrom(FieldMap.class)) {
                while (rs.next()) {
                    rList.add(LocalStore.getFieldMap(rs));
                }
            } else {
                Map<String, Field> nfMap = LocalStore.getClassFieldMap(classV, true);
                while (rs.next()) {
                    rList.add(LocalStore.getObject(classV, rs, nfMap, ffMap));
                }
            }
            return rList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (Exception ex) {
                logger.error("关闭ResultSet失败", ex);
            }
        }
    }

    @Override
    public void close() {
        try {
            sttm.close();
            con.close();
            sttm = null;
            con = null;
        } catch (Exception ex) {
            logger.error("关闭数据库连接失败", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int update(String sql) {
        try {
            return sttm.executeUpdate(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int update(PreparedStatement sql) {
        try {
            return sql.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean executeSQL(String sql) {
        try {
            return sttm.execute(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean executeSQL(PreparedStatement sql) {
        try {
            return sql.execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(List data, Class classV, String tableName, String fName) {
        update(data, classV, null, tableName, fName);
    }

    public void update(List data, Class classV, Map<String, String> ffMap, String tableName, String fName) {
        PreparedStatement pst = null;
        try {
            StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
            Field[] fs = classV.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);
                String fn = fs[i].getName();
                if (ffMap != null) {
                    fn = ffMap.get(fn);
                }
                sqlBuilder.append(fn).append('=').append('?');
                if (i < fs.length - 1) {
                    sqlBuilder.append(',');
                }
            }
            sqlBuilder.append(" WHERE ").append(fName).append('=').append('?');
            String sql = sqlBuilder.toString();
            logger.info("生成的SQL:\n{}", sql);
            pst = con.prepareStatement(sql);
            for (Object obj : data) {
                Field ff = null;
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    setPSValue(pst, f, obj, i + 1);
                    if (fName.equals(f.getName())) {
                        ff = f;
                    }
                }
                setPSValue(pst, ff, obj, fs.length + 1);
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                    pst = null;
                } catch (Exception ex) {
                    logger.error("关闭PreparedStatement失败", ex);
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void insert(List data, Class classV, String tableName) {
        insert(data, classV, null, tableName);
    }

    @Override
    public void insert(List data, Class classV, Map<String, String> ffMap, String tableName) {
        PreparedStatement pst = null;
        try {
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
            sqlBuilder.append(tableName).append('(');
            Field[] fs = classV.getDeclaredFields();
            StringBuilder values = new StringBuilder();
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);
                String fName = fs[i].getName();
                if (ffMap != null) {
                    fName = ffMap.get(fName);
                }
                sqlBuilder.append(fName);
                values.append('?');
                if (i < fs.length - 1) {
                    sqlBuilder.append(',');
                    values.append(',');
                }
            }
            sqlBuilder.append(") VALUES(").append(values).append(')');
            String sql = sqlBuilder.toString();
            logger.info("生成的SQL:\n{}", sql);
            pst = con.prepareStatement(sql);
            for (Object obj : data) {
                for (int i = 0; i < fs.length; i++) {
                    setPSValue(pst, fs[i], obj, i + 1);
                }
                pst.addBatch();
            }
            //long cc = System.currentTimeMillis();
            pst.executeBatch();
            //long cc2 = System.currentTimeMillis();
            //System.err.println("executeBatch===" + (cc2 - cc));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                    pst = null;
                } catch (Exception ex) {
                    logger.error("关闭PreparedStatement失败", ex);
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void setPSValue(PreparedStatement pst, Field f, Object obj, int i) throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class type = f.getType();
        if (type.isAssignableFrom(double.class)) {
            pst.setDouble(i, f.getDouble(obj));
        } else if (type.isAssignableFrom(String.class)) {
            Object v = f.get(obj);
            if (v != null) {
                pst.setString(i, v.toString());
            } else {
                pst.setString(i, null);
            }
        } else if (type.isAssignableFrom(Date.class)) {
            Object v = f.get(obj);
            if (v != null) {
                pst.setTimestamp(i, new Timestamp(((Date) v).getTime()));
            } else {
                pst.setTimestamp(i, null);
            }
        } else if (type.isAssignableFrom(int.class)) {
            pst.setInt(i, f.getInt(obj));
        } else if (type.isAssignableFrom(long.class)) {
            pst.setLong(i, f.getLong(obj));
        } else if (type.isAssignableFrom(float.class)) {
            pst.setFloat(i, f.getFloat(obj));
        } else if (type.isAssignableFrom(short.class)) {
            pst.setShort(i, f.getShort(obj));
        } else if (type.isAssignableFrom(BigDecimal.class)) {
            pst.setBigDecimal(i, (BigDecimal) f.get(obj));
        } else if (type.isAssignableFrom(char.class)) {
            pst.setString(i, String.valueOf(f.getChar(obj)));
        } else if (type.isAssignableFrom(boolean.class)) {
            pst.setBoolean(i, f.getBoolean(obj));
        }
    }

    @Override
    public boolean isExist_Table(String tableName) {
        return LocalStore.isExist_Table(con, tableName);
    }

    @Override
    public boolean createTable(Class classV, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE ");
        sqlBuilder.append(tableName).append('(');
        Field[] fs = classV.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            sqlBuilder.append(fs[i].getName()).append(' ').append(getSQLDataType(fs[i].getType()));
            if (i < fs.length - 1) {
                sqlBuilder.append(',');
            }
        }
        sqlBuilder.append(')');
        String sql = sqlBuilder.toString();
        logger.info("生成的SQL:\n{}", sql);
        return executeSQL(sql);
    }

    @Override
    public boolean isExist_Column(String tableName, String column) {
        return LocalStore.isExist_Column(con, tableName, column);
    }

    /**
     * Java类型到SQL数据库数据类型的一个转换
     *
     * @param classV
     * @return
     */
    private String getSQLDataType(Class classV) {
        String rs = "";
        if (classV.isAssignableFrom(double.class)) {
            return "DOUBLE";
        } else if (classV.isAssignableFrom(String.class)) {
            return "VARCHAR";
        } else if (classV.isAssignableFrom(Date.class)) {
            return "TIMESTAMP";
        } else if (classV.isAssignableFrom(int.class)) {
            return "INTEGER";
        } else if (classV.isAssignableFrom(long.class)) {
            return "BIGINT";
        } else if (classV.isAssignableFrom(float.class)) {
            return "FLOAT";
        } else if (classV.isAssignableFrom(short.class)) {
            return "SMALLINT";
        } else if (classV.isAssignableFrom(BigDecimal.class)) {
            return "NUMERIC";
        } else if (classV.isAssignableFrom(char.class)) {
            return "CHAR(1)";
        } else if (classV.isAssignableFrom(boolean.class)) {
            return "BIT";
        }
        return rs;

    }

    @Override
    public void setAutoCommit(boolean isAuto) {
        try {
            this.con.setAutoCommit(isAuto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void commit() {
        try {
            this.con.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void rollback() {
        try {
            this.con.rollback();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
