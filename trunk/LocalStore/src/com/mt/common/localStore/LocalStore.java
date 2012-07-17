/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.localStore;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 本地存储的方案提供模块，可支持数据库，文本文件，对象序列化，FieldMap存储。
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-6-14
 * <p/>
 */
public class LocalStore {

    /**
     * 全局路径
     */
    static private String gPath = "C:";
    /**
     * 登录用户路径
     */
    static private String loginUserPath = gPath;
    /**
     * 当前运行路径
     */
    static private String runPath = "./localStore";

    static {
        makePath(runPath);
    }

    /**
     * 字段和值的分隔符
     */
    static private final char FVSP = 0x0F;
    /**
     * SQL数据库的模式定义
     * 嵌入模式
     */
    static public final int Embedded = -101;
    /**
     * 内存模式
     */
    static public final int Memory = -102;
    /**
     * 文本输出的编码
     */
    static public final String EncodingDef = "UTF-8";
    /**
     * 序列化到简洁文本的日期格式
     */
    static private final DateFormat TxtDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    static private final Logger logger = LoggerFactory.getLogger(LocalStore.class);

    /**
     * 配置全局路径
     *
     * @param path
     */
    static public void configGPath(String path) {
        gPath = path;
        makePath(gPath);
    }

    /**
     * 获取全局路径
     *
     * @return
     */
    static public String getGPath() {
        return gPath;
    }

    /**
     * 设置登录用户内部编号
     *
     * @param number
     */
    static public void setLoginUserNumber(int number) {
        loginUserPath = gPath + "/" + number;
        makePath(loginUserPath);
    }

    /**
     * 获得登录用户路径
     *
     * @return
     */
    static public String getLoginUserPath() {
        return loginUserPath;
    }

    /**
     * 获得当前运行路径
     */
    static public String getRunPath() {
        return runPath;
    }

    /**
     * 从指定的文件中反序列化对象
     *
     * @param filePath
     * @return
     */
    static public Object fromLocalXMLData(Class classV, String filePath) {
        try {
            logger.info("fromLocalXMLData_filePath:" + filePath);
            if (classV.isAssignableFrom(FieldMap.class)) {
                String rs = FileUtils.readFileToString(new File(filePath), EncodingDef);
                return FieldMapUtil.createFieldMap(XMLUtil.createDocument(rs).getDocumentElement());
            } else if (classV.isAssignableFrom(FieldMapNode.class)) {
                String rs = FileUtils.readFileToString(new File(filePath), EncodingDef);
                return FieldMapUtil.createFieldMapNode(XMLUtil.createDocument(rs).getDocumentElement());
            } else if (classV.isAssignableFrom(FieldMapSet.class)) {
                String rs = FileUtils.readFileToString(new File(filePath), EncodingDef);
                return FieldMapUtil.createFieldMapSet(XMLUtil.createDocument(rs).getDocumentElement());
            } else {
                XStream xstream = new XStream();
                return xstream.fromXML(new InputStreamReader(new FileInputStream(filePath), EncodingDef));
            }
        } catch (FileNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            logger.error("读取本地数据失败", ex);
            throw new RuntimeException("读取本地数据失败", ex);
        }
    }

    /**
     * 将对象序列化到指定的文件中
     *
     * @param data
     * @param filePath
     */
    static public void toLocalXMLData(Object data, String filePath) {
        try {
            logger.info("toLocalXMLData_filePath:" + filePath);
            if (data instanceof FieldMap) {
                if (data instanceof FieldMapNode) {
                    FileUtils.writeStringToFile(new File(filePath), FieldMapUtil.createXMLString((FieldMapNode) data), EncodingDef);
                } else {
                    FileUtils.writeStringToFile(new File(filePath), FieldMapUtil.createXMLString((FieldMap) data), EncodingDef);
                }

            } else if (data instanceof FieldMapSet) {
                FileUtils.writeStringToFile(new File(filePath), FieldMapUtil.createXMLString((FieldMapSet) data), EncodingDef);
            } else {
                XStream xstream = new XStream();
                xstream.toXML(data, new OutputStreamWriter(new FileOutputStream(filePath), EncodingDef));
            }
        } catch (Exception ex) {
            logger.error("保存数据到本地失败", ex);
            throw new RuntimeException("保存数据到本地失败", ex);
        }
    }

    /**
     * 从本地预定义的路径中将XML数据反序列化到对象
     *
     * @param name
     * @param classV
     * @param storePath
     * @return
     */
    static public Object fromLocalXMLData(String name, Class classV, StorePath storePath) {
        return fromLocalXMLData(classV, getFilePath(name, storePath));
    }

    /**
     * 将对象以XML序列化到本地预定义的路径中
     *
     * @param name
     * @param data
     * @param storePath
     */
    static public void toLocalXMLData(String name, Object data, StorePath storePath) {
        toLocalXMLData(data, getFilePath(name, storePath));
    }

    /**
     * 从指定的文件中将简洁文本反序列化到List
     * 在数据量大的时候这种方式比XML序列化要快很多
     * 但是它无法支持复杂对象
     *
     * @param classV
     * @param filePath
     * @return
     */
    static public List fromLocalTxtData(Class classV, String filePath) {
        BufferedReader reader = null;
        try {
            List rs = new ArrayList();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), EncodingDef));
            Map<String, Field> nfMap = getClassFieldMap(classV);
            String temp = null;
            for (; (temp = reader.readLine()) != null; ) {
                rs.add(convertTextToObject(temp, classV, nfMap));
            }
            return rs;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            logger.error("读取数据发生异常", ex);
            throw new RuntimeException(ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("关闭输入流发生异常", ex);
                }
            }
        }
    }

    /**
     * 将List数据序列化到简洁文本,指定文件路径
     * 在数据量大的时候这种方式比XML序列化要快很多
     * 但是它无法支持复杂对象
     *
     * @param objs
     * @param classV
     * @param filePath
     */
    static public void toLocalTxtData(List objs, Class classV, String filePath) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), EncodingDef));
            Field[] fs = classV.getDeclaredFields();
            for (Object obj : objs) {
                writer.write(convertObjectToText(obj, fs).toString());
            }
        } catch (Exception ex) {
            logger.error("输出BondDef发生异常", ex);
            throw new RuntimeException(ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    logger.error("关闭输出流发生异常", ex);
                }
            }
        }
    }

    /**
     * 将数据从预定义的路径中反序列化到List
     * 在数据量大的时候这种方式比XML序列化要快很多
     * 但是它无法支持复杂对象
     *
     * @param name
     * @param classV
     * @param storePath
     * @return
     */
    static public List fromLocalTxtData(String name, Class classV, StorePath storePath) {
        return fromLocalTxtData(classV, getFilePath(name, storePath));
    }

    /**
     * 将List数据序列化到简洁文本,使用预定义的路径
     * 在数据量大的时候这种方式比XML序列化要快很多
     * 但是它无法支持复杂对象
     *
     * @param objs
     * @param name
     * @param classV
     * @param storePath
     */
    static public void toLocalTxtData(List objs, String name, Class classV, StorePath storePath) {
        toLocalTxtData(objs, classV, getFilePath(name, storePath));
    }

    /**
     * 获得类型的字段Map
     *
     * @param classV
     * @return
     */
    static Map<String, Field> getClassFieldMap(Class classV) {
        return getClassFieldMap(classV, false);
    }

    /**
     * 获得类型的字段Map
     *
     * @param classV
     * @param isUp
     * @return
     */
    static Map<String, Field> getClassFieldMap(Class classV, boolean isUp) {
        Map<String, Field> rs = new HashMap<String, Field>();
        Field[] fs = classV.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            if (isUp) {
                rs.put(f.getName().toUpperCase(), f);
            } else {
                rs.put(f.getName(), f);
            }
        }
        return rs;
    }

    /**
     * 将文本转换为Object
     *
     * @param text
     * @param classV
     * @param nfMap
     * @return
     */
    static private Object convertTextToObject(String text, Class classV, Map<String, Field> nfMap) {
        try {
            Object rs = classV.newInstance();
            String[] fData = new String[2];
            StringBuilder b = new StringBuilder();
            int size = text.length();
            for (int i = 0; i < size; i++) {
                char t = text.charAt(i);
                if (t == FVSP) {
                    fData[0] = b.toString();
                    b = new StringBuilder();
                } else if (t == '\t') {
                    fData[1] = b.toString();
                    setObjectFieldValue(rs, fData, nfMap);
                    fData = new String[2];
                    b = new StringBuilder();
                } else {
                    b.append(t);
                }
            }
            return rs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 设置对象字段的值
     *
     * @param obj
     * @param fData
     * @param nfMap
     */
    static private void setObjectFieldValue(Object obj, String[] fData, Map<String, Field> nfMap) {
        try {
            if (fData[1] != null && !fData[1].equals("")) {
                Field f = nfMap.get(fData[0]);
                if (f == null) {
                    return;
                }
                f.setAccessible(true);
                if (f.getType().isAssignableFrom(double.class)) {
                    f.set(obj, Double.parseDouble(fData[1]));
                } else if (f.getType().isAssignableFrom(String.class)) {
                    f.set(obj, fData[1]);
                } else if (f.getType().isAssignableFrom(Date.class)) {
                    f.set(obj, TxtDateFormat.parse(fData[1]));
                } else if (f.getType().isAssignableFrom(int.class)) {
                    f.set(obj, Integer.parseInt(fData[1]));
                } else if (f.getType().isAssignableFrom(long.class)) {
                    f.set(obj, Long.parseLong(fData[1]));
                } else if (f.getType().isAssignableFrom(float.class)) {
                    f.set(obj, Float.parseFloat(fData[1]));
                } else if (f.getType().isAssignableFrom(short.class)) {
                    f.set(obj, Short.parseShort(fData[1]));
                } else if (f.getType().isAssignableFrom(char.class)) {
                    f.set(obj, fData[1].charAt(0));
                } else if (f.getType().isAssignableFrom(boolean.class)) {
                    f.set(obj, Boolean.parseBoolean(fData[1]));
                } else if (f.getType().isAssignableFrom(BigDecimal.class)) {
                    f.set(obj, new BigDecimal(fData[1]));
                } else if (f.getType().isAssignableFrom(TextField.class)) {
                    Object temp = f.get(obj);
                    if (temp != null) {
                        ((TextField) temp).fromString(fData[1]);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 将一个Object转换为文本形式
     *
     * @param obj
     * @param fs
     * @return
     */
    static private StringBuilder convertObjectToText(Object obj, Field[] fs) {
        try {
            StringBuilder builder = new StringBuilder();
            int size = fs.length;
            for (int i = 0; i < size; i++) {
                if (!fs[i].isAccessible()) {
                    fs[i].setAccessible(true);
                }
                Object ov = fs[i].get(obj);
                String v = ov == null ? "" : toString(ov);
                builder.append(fs[i].getName()).append(FVSP).append(v).append('\t');
            }
            builder.append('\n');
            return builder;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    static private String toString(Object ov) {
        if (ov instanceof Date) {
            return TxtDateFormat.format((Date) ov);
        } else {
            return ov.toString();
        }
    }

    /**
     * 获得数据库连接
     *
     * @param name
     * @return
     */
    static public Connection getSQLDBConnection(String name, StorePath storePath) {
        return getSQLDBConnection(name, Embedded, getPath(storePath));
    }

    /**
     * 获得数据库连接
     *
     * @param name
     * @param mode
     * @param filePath
     * @return
     */
    static public Connection getSQLDBConnection(String name, int mode, String filePath) {
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
            //Class.forName("org.sqlite.JDBC");
            String url = null;
            if (mode == Embedded) {
                url = "jdbc:h2:" + filePath + "/" + name;
                //url = "jdbc:sqlite:" + filePath + "/" + name;
            } else if (mode == Memory) {
                url = "jdbc:h2:mem:" + name;
                //url = "jdbc:sqlite:" + name;
            }
            con = DriverManager.getConnection(url);
            return con;
        } catch (Exception ex) {
            logger.error("获得数据库连接失败", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 创建一个SQLDao
     *
     * @param name
     * @return
     */
    static public SQLDao createSQLDao(String name) {
        return createSQLDao(name, StorePath.GPath);
    }

    /**
     * 创建一个SQLDao
     *
     * @param name
     * @return
     */
    static public SQLDao createSQLDao(String name, StorePath storePath) {
        return createSQLDao(name, Embedded, storePath);
    }

    /**
     * 创建一个SQLDao
     *
     * @param name
     * @param mode
     * @return
     */
    static public SQLDao createSQLDao(String name, int mode, StorePath storePath) {
        return new DefaultSQLDao(getSQLDBConnection(name, mode, getPath(storePath)));
    }

    /**
     * 创建一个默认的SQLDao
     *
     * @param con
     * @return
     */
    static public SQLDao createDefaultSQLDao(Connection con) {
        return new DefaultSQLDao(con);
    }

    /**
     * 判断数据库是否存在对应的表
     *
     * @param con
     * @param tableName
     * @return
     */
    static public boolean isExist_Table(Connection con, String tableName) {
        ResultSet rs = null;
        try {
            rs = con.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
            return rs.next();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {
                logger.error("关闭ResultSet失败", ex);
            }
        }
    }

    /**
     * 判断数据库是否存在对应的列
     *
     * @param con
     * @param tableName
     * @param column
     * @return
     */
    static public boolean isExist_Column(Connection con, String tableName, String column) {
        ResultSet rs = null;
        try {
            rs = con.getMetaData().getColumns(null, null, tableName.toUpperCase(), column.toUpperCase());
            return rs.next();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {
                logger.error("关闭ResultSet失败", ex);
            }
        }
    }

    /**
     * 对SQLDB查询数据
     *
     * @param name
     * @param sql
     * @param classV
     * @return
     */
    static public List querySQLDB(String name, String sql, Class classV) {
        return querySQLDB(name, sql, classV, null);
    }

    /**
     * 对SQLDB查询数据
     *
     * @param name
     * @param sql
     * @param classV
     * @param ffMap
     * @return
     */
    static public List querySQLDB(String name, String sql, Class classV, Map<String, String> ffMap) {
        SQLDao dao = null;
        try {
            dao = createSQLDao(name);
            return dao.query(sql, classV, ffMap);
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Exception sex) {
                logger.error("关闭数据库连接发生异常", sex);
            }
        }
    }

    /**
     * 从ResultSet获得对象
     *
     * @param classV
     * @param rs
     * @param nfMap
     * @param ffMap
     * @return
     */
    static Object getObject(Class classV, ResultSet rs, Map<String, Field> nfMap, Map<String, String> ffMap) {
        try {
            Object obj = null;
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            if (count > 1) {
                obj = classV.newInstance();
                for (int i = 1; i <= count; i++) {
                    String name = metaData.getColumnName(i);
                    int type = metaData.getColumnType(i);
                    if (ffMap != null) {
                        String n = ffMap.get(name);
                        if (n != null) {
                            name = n.toUpperCase();
                        }
                    }
                    Field f = nfMap.get(name);
                    if (f != null) {
                        f.setAccessible(true);
                        f.set(obj, getResultSetValue(type, rs, i));
                    }
                }
            } else {
                int type = metaData.getColumnType(1);
                obj = getResultSetValue(type, rs, 1);
            }
            return obj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 从ResultSet获得FieldMap
     *
     * @param rs
     */
    static FieldMap getFieldMap(ResultSet rs) {
        try {
            FieldMap fm = new FieldMap("");
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String name = metaData.getColumnName(i);
                int type = metaData.getColumnType(i);
                fm.putField(name, getResultSetValue(type, rs, i));
            }
            return fm;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 获得ResultSet的值
     *
     * @param type
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    static private Object getResultSetValue(int type, ResultSet rs, int i) throws SQLException {
        Object value = null;
        switch (type) {
            case Types.DOUBLE:
                value = rs.getDouble(i);
                break;
            case Types.VARCHAR:
                value = rs.getString(i);
                break;
            case Types.TIMESTAMP:
                Date date = rs.getTimestamp(i);
                //Timestamp虽然继承自java.util.Date，但是为了避免一些潜在Timestamp问题
                //还是构造一个真正的java.util.Date
                if (date != null) {
                    value = new Date(date.getTime());
                }
                break;
            case Types.INTEGER:
                value = rs.getInt(i);
                break;
            case Types.BIGINT:
                value = rs.getLong(i);
                break;
            case Types.FLOAT:
                value = rs.getFloat(i);
                break;
            case Types.SMALLINT:
                value = rs.getShort(i);
                break;
            case Types.NUMERIC:
                value = rs.getBigDecimal(i);
                break;
            case Types.CHAR:
                value = rs.getString(i).charAt(0);
                break;
            case Types.BIT:
                value = rs.getBoolean(i);
                break;
        }
        return value;
    }

    static public int updateSQLDB(String name, String sql) {
        SQLDao dao = null;
        try {
            dao = createSQLDao(name);
            return dao.update(sql);
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Exception sex) {
                logger.error("关闭数据库连接发生异常", sex);
            }
        }
    }

    static public void executeSQL(String name, String sql) {
        SQLDao dao = null;
        try {
            dao = createSQLDao(name);
            dao.executeSQL(sql);
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Exception sex) {
                logger.error("关闭数据库连接发生异常", sex);
            }
        }
    }

    static public String getFilePath(String name, StorePath storePath) {
        return getPath(storePath) + "/" + name + ".db";
    }

    static private String getPath(StorePath storePath) {
        String rs = null;
        switch (storePath) {
            case GPath:
                rs = gPath;
                break;
            case LoginUserPath:
                rs = loginUserPath;
                break;
            case RunPath:
                rs = runPath;
        }
        return rs;
    }

    static private void makePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
