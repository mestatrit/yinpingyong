package com.mt.common.selectionBind;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 一个FieldColumn定义的集合
 * @author hanhui
 *
 */
public class FieldColumnList {

	private Logger logger = LoggerFactory.getLogger(FieldColumnList.class);
	
    private String name = "DefaultFieldColumnList";
    private List<FieldColumn> fcList = new ArrayList<FieldColumn>();

    /**
     * 构建一个空的FieldColumnList
     */
    public FieldColumnList() {
    }

    /**
     * 通过字符串便捷的方式构造FieldColumnList
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd";java.util.Date;false"
     * @param list
     * @return
     */
    public FieldColumnList(String... list) {
        addFCStringList(list);
    }

    /**
     * 从一个资源路径获取XML文件,构建FieldColumnList
     * @param path
     */
    public FieldColumnList(String path) {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(FieldColumnList.class.getResourceAsStream(path));
            setXMLDoc(doc);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);;
            System.err.println("XML解析失败");
        }
    }

    /**
     * 从一个XML Document构建FieldColumnList
     * @param xmlDoc
     */
    public FieldColumnList(Document xmlDoc) {
        setXMLDoc(xmlDoc);
    }

    /**
     * 增加一个FieldColumn
     * @param fc
     * @return
     */
    public FieldColumnList addFieldColumn(FieldColumn fc) {
        fcList.add(fc);
        return this;
    }

    /**
     * 增加一批FieldColumn
     * @param list
     * @return
     */
    public FieldColumnList addFieldColumnList(List<FieldColumn> list) {
        fcList.addAll(list);
        return this;
    }

    /**
     * 移除一个FieldColumn
     * @param fc
     * @return
     */
    public FieldColumnList removeFieldColumn(FieldColumn fc) {
        fcList.remove(fc);
        return this;
    }

    /**
     * 移除一批FieldColumn
     * @param list
     * @return
     */
    public FieldColumnList removeFieldColumnList(List<FieldColumn> list) {
        fcList.removeAll(list);
        return this;
    }

    /**
     * 通过列名删除FieldColumn
     * @param columnNames
     * @return
     */
    public FieldColumnList removeFieldColumn(String... columnNames) {
        for (String cn : columnNames) {
            for (int i = 0; i < fcList.size(); i++) {
                if (fcList.get(i).getColumnName().equals(cn)) {
                    fcList.remove(i);
                    break;
                }
            }
        }
        return this;
    }

    /**
     * 过滤得到一个新的FieldColumnList
     * @param columnNames 指定需要过滤掉的列名
     * @return
     */
    public FieldColumnList filterFieldColumnList(String... columnNames) {
        FieldColumnList fList = new FieldColumnList();
        fList.setName(this.getName());
        for (int i = 0; i < fcList.size(); i++) {
            FieldColumn fc = fcList.get(i);
            boolean find = false;
            for(String cn:columnNames){
                if(fc.getColumnName().equals(cn)){
                    find = true;
                    break;
                }
            }
            if(!find){
                fList.addFieldColumn(fc);
            }
        }
        return fList;
    }

    /**
     * 通过字符串便捷的方式加入一批FieldColumn定义
     * 列名;字段名;转换类型;显示格式;存储格式;ColumnClass;是否可编辑
     * 比如:"到期日;endDate;Date;yyyy/MM/dd;yyyyMMdd",java.util.Date;false"
     * @param list
     * @return
     */
    public FieldColumnList addFCStringList(String... list) {
        for (String item : list) {
            String[] temp = item.split(";");
            if (temp[0].equals("") || temp[1].equals("")) {
                continue;
            }
            FieldColumn fc = new FieldColumn();
            fc.setColumnName(temp[0]);
            fc.setFieldName(temp[1]);
            for (int i = 2; i < temp.length; i++) {
                if (i == 2) {
                    fc.setConverterType(temp[2].equals("") ? null : temp[2]);
                } else if (i == 3) {
                    fc.setViewFormat(temp[3].equals("") ? null : temp[3]);
                } else if (i == 4) {
                    fc.setSaveFormat(temp[4].equals("") ? null : temp[4]);
                } else if (i == 5) {
                    try {
                        fc.setColumnClass(temp[5].equals("") ? null : Class.forName(temp[5]));
                    } catch (ClassNotFoundException e) {
                        System.err.println(temp[5] + "反射失败,请检查类名");
                        fc.setColumnClass(null);
                    }
                } else if (i == 6) {
                    boolean rs = temp[6].equals("Y") || temp[6].equals("1") || temp[6].equals("true");
                    fc.setEditable(rs);
                }
            }
            addFieldColumn(fc);
        }
        return this;
    }

    /**
     * 获得FieldColumn
     * @param index
     * @return
     */
    public FieldColumn getFieldColumn(int index) {
        return fcList.isEmpty() ? null : fcList.get(index);
    }

    /**
     * 根据字段名得到FieldColumn
     * @param fName
     * @return
     */
    public FieldColumn getFieldColumn_FName(String fName){
        for(FieldColumn fc: fcList){
            if(fc.getFieldName().equals(fName)){
                return fc;
            }
        }
        return null;
    }

    /**
     * 根据字段名得到FieldColumn的index
     * @param fName
     * @return
     */
    public int getFieldColumnIndex_FName(String fName){
        for(int i = 0; i < fcList.size();i++){
            if(fcList.get(i).getFieldName().equals(fName)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 获得FieldColumn的数量
     * @return
     */
    public int getFieldColumnCount() {
        return fcList.size();
    }

    /**
     * 将XML解析到FieldColumn
     * @param xmlDoc
     */
    private void setXMLDoc(Document xmlDoc) {
        NodeList nodeList = xmlDoc.getElementsByTagName("FieldColumn");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element e = (Element) nodeList.item(i);
            String cn = e.getAttribute("columnName");
            String fn = e.getAttribute("fieldName").trim();
            String ct = e.getAttribute("converterType").trim();
            String vf = e.getAttribute("viewFormat").trim();
            String sf = e.getAttribute("saveFormat").trim();
            String cc = e.getAttribute("columnClass").trim();
            String ise = e.getAttribute("isEditable").trim();
            if (cn.equals("") || fn.equals("")) {
                continue;
            }
            FieldColumn fc = new FieldColumn();
            fc.setColumnName(cn);
            fc.setFieldName(fn);
            fc.setConverterType(ct.equals("") ? null : ct);
            fc.setViewFormat(vf.equals("") ? null : vf);
            fc.setSaveFormat(sf.equals("") ? null : sf);
            try {
                fc.setColumnClass(cc.equals("") ? null : Class.forName(cc));
            } catch (ClassNotFoundException e1) {
                System.err.println(cc + "反射失败,请检查类名");
                fc.setColumnClass(null);
            }
            boolean rs = ise.equals("Y") || ise.equals("1") || ise.equals("true");
            fc.setEditable(rs);
            addFieldColumn(fc);
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
