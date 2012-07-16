package com.mt.common.dynamicDataDef;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mt.common.xml.WAX;
import com.mt.common.xml.XMLUtil;
import com.mt.common.xml.WAX.Version;

/**
 * 一些FieldMap的工具方法
 *
 * @author hanhui
 */
public class FieldMapUtil {

    private static Logger logger = LoggerFactory.getLogger(FieldMapUtil.class);
    /**
     * 以Element的方式构建FieldMap的XML
     */
    public static final int TagNameMode = 100;
    /**
     * 以属性的方式构建FieldMap的XML
     */
    public static final int AttrTagMode = 101;

    /**
     * 创建一个FieldMap的XML描述
     *
     * @param fm
     * @return
     */
    public static String createXMLString(FieldMap fm) {
        return createXMLString(fm, TagNameMode);
    }

    /**
     * 创建一个FieldMap的XML描述，可以指定模式
     *
     * @param fm
     * @param mode
     * @return
     */
    public static String createXMLString(FieldMap fm, int mode) {
        FieldMapSet set = new FieldMapSet("").addFieldMap(fm);
        return createXMLString(set, mode);


    }

    /**
     * 创建一个FieldMapSet的XML描述
     *
     * @param set
     * @return
     */
    public static String createXMLString(FieldMapSet set) {
        return createXMLString(set, TagNameMode);
    }

    /**
     * 创建一个FieldMapSet的XML描述，可以指定模式
     *
     * @param set
     * @param mode
     * @return
     */
    public static String createXMLString(FieldMapSet set, int mode) {
        return createXMLString(set, mode, true);
    }

    /**
     * @param set
     * @param isHeader
     * @return
     */
    public static String createXMLString(FieldMapSet set, boolean isHeader) {
        return createXMLString(set, TagNameMode, isHeader);
    }

    /**
     * 创建一个FieldMapSet的XML描述，可以指定模式
     *
     * @param set
     * @param mode
     * @return
     */
    public static String createXMLString(FieldMapSet set, int mode, boolean isHeader) {
        List<Field> attrs = set.getAttrList();
        StringBuilder rs = new StringBuilder();
        if (isHeader) {
            rs.append(XMLHeader);
        }
        if (set.getName().equals("")) {
            if (mode == TagNameMode) {
                if (attrs.size() > 0) {
                    rs.append("\n").append(createFieldListElementStr(attrs));
                }
            }
            for (int i = 0; i < set.getFieldMapCount(); i++) {
                if (mode == TagNameMode) {
                    rs.append("\n").append(createFieldMapElementXMLMsg(set.getFieldMap(i)));
                } else {
                    rs.append("\n").append(createFieldMapAttrXMLMsg(set.getFieldMap(i)));
                }
            }
        } else {
            if (mode == TagNameMode) {
                rs.append("\n<").append(set.getName()).append(">");
                if (attrs.size() > 0) {
                    rs.append("\n").append(createFieldListElementStr(attrs));
                }
            } else {
                rs.append("\n<").append(set.getName());
                if (attrs.size() > 0) {
                    rs.append(" ").append(createFieldListAttrStr(set.getAttrList()));
                }
                rs.append(">");
            }
            for (int i = 0; i < set.getFieldMapCount(); i++) {
                if (mode == TagNameMode) {
                    rs.append("\n").append(createFieldMapElementXMLMsg(set.getFieldMap(i)));
                } else {
                    rs.append("\n").append(createFieldMapAttrXMLMsg(set.getFieldMap(i)));
                }
            }
            rs.append("\n</").append(set.getName()).append(">");
        }

        return rs.toString();
    }

    /**
     * 创建一个FieldMapNode的XML描述
     *
     * @param node
     * @return
     */
    public static String createXMLString(FieldMapNode node) {
        StringWriter sw = new StringWriter();
        WAX wax = new WAX(sw, Version.V1_0);
        wax.start(node.getName());
        createXMLString(node, wax);
        wax.close();
        return sw.toString();
    }

    private static void createXMLString(FieldMapNode node, WAX wax) {
        Field[] fs = node.toFieldArray();
        for (Field f : fs) {
            wax.attr(XMLUtil.escape(f.getName()), XMLUtil.escape(f.getValue().toString()));
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            FieldMapNode cNode = node.getChildAt(i);
            wax.start(XMLUtil.escape(cNode.getName()));
            createXMLString(cNode, wax);
            wax.end();
        }
    }

    /**
     * 从一个Element创建一个FieldMap
     *
     * @param element
     * @return
     */
    public static FieldMap createFieldMap(Element element) {
        FieldMap fm = new FieldMap(element.getTagName());
        List<Field> fields = getFieldList(element);
        fm.addFieldList(fields);
        return fm;
    }

    /**
     * 从一个Element创建一个FieldMapSet
     *
     * @param element
     * @return
     */
    public static FieldMapSet createFieldMapSet(Element element) {
        FieldMapSet fms = new FieldMapSet(element.getTagName());
        fms.addAttrList(getAttrFieldList(element));
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                Element e = (Element) node;
                List<Field> fList = getFieldList(e);
                if (fList.size() > 0) {//这个Element是FieldMap
                    FieldMap fm = new FieldMap(e.getTagName());
                    fm.addFieldList(fList);
                    fms.addFieldMap(fm);
                } else {//这个Element仅仅作为FieldMapSet的属性
                    fms.setAttr(e.getTagName(), e.getTextContent().trim());
                }
            }
        }
        return fms;
    }

    /**
     * 从一个Element创建一个FieldMapSet,会做一些智能的判断来确定这个Element
     * 是否是FieldMap，如果仅仅是FieldMap的话会创建一个无名FieldMapSet来容纳
     * 这个FieldMap，然后返回无名FieldMapSet
     *
     * @param element
     * @return
     */
    public static FieldMapSet createAutoFieldMapSet(Element element) {
        ArrayList<Element> oneList = new ArrayList<Element>();
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                oneList.add((Element) node);
            }
        }

        if (oneList.isEmpty()) {//根元素就是一个FieldMap
            FieldMap fm = new FieldMap(element.getTagName());
            List<Field> fields = getFieldList(element);
            fm.addFieldList(fields);
            return new FieldMapSet("").addFieldMap(fm);
        } else {
            boolean isAllField = true;
            HashMap<Element, List<Field>> allList = new HashMap<Element, List<Field>>();
            for (Element e : oneList) {
                List<Field> fList = getFieldList(e);
                allList.put(e, fList);
                if (fList.size() > 0) {
                    isAllField = false;
                }
            }
            if (isAllField) {//根元素是一个FieldMap
                FieldMap fm = new FieldMap(element.getTagName());
                List<Field> fields = getFieldList(element);
                fm.addFieldList(fields);
                return new FieldMapSet("").addFieldMap(fm);
            } else {//根元素是一个FieldMapSet
                FieldMapSet set = new FieldMapSet(element.getTagName());
                set.addAttrList(getAttrFieldList(element));
                for (Element e : oneList) {
                    List<Field> fList = allList.get(e);
                    if (fList.size() > 0) {
                        FieldMap fm = new FieldMap(e.getTagName());
                        fm.addFieldList(fList);
                        set.addFieldMap(fm);
                    } else {
                        set.setAttr(e.getTagName(), e.getTextContent().trim());
                    }

                }
                return set;
            }
        }
    }

    /**
     * 设置默认的Field的值
     *
     * @param fm
     * @param name
     * @param value
     */
    public static void setDefaultValue(FieldMap fm, String name, Object value) {
        if (!fm.isSetField(name)) {
            fm.putField(name, value);
        } else if (fm.getField(name).getValue() == null) {
            fm.getField(name).setValue(value);
        }
    }

    /**
     * 设置字段的值，如果没有字段就会创建字段
     *
     * @param fm
     * @param name
     * @param value
     */
    public static void setFieldValue(FieldMap fm, String name, Object value) {
        Field f = fm.getField(name);
        if (f == null) {
            fm.putField(name, value);
        } else {
            f.setValue(value);
        }
    }

    public static void setFieldValue(FieldMap fm, String value, String... names) {
        for (String name : names) {
            FieldMapUtil.setFieldValue(fm, name, value);
        }
    }

    /**
     * 从一个Element创建一个FieldMapNode
     *
     * @param e
     * @return
     */
    public static FieldMapNode createFieldMapNode(Element e) {
        FieldMapNode root = new FieldMapNode(e.getTagName());
        createFieldMapNode(root, e);
        return root;
    }

    /**
     * 从单个fieldMap构建单个fieldMapNode
     *
     * @param fm
     * @return
     */
    static public FieldMapNode createFieldMapNode(FieldMap fm) {
        FieldMapNode node = new FieldMapNode(fm.getName());
        Field[] fields = fm.toFieldArray();
        for (Field field : fields) {
            node.addField(field.getName(), field.getValue());
        }
        return node;
    }

    /**
     * 递归创建FieldMapNode
     *
     * @param node
     * @param e
     */
    static private void createFieldMapNode(FieldMapNode node, Element e) {
        node.addFieldList(getAttrFieldList(e));
        String text = e.getTextContent().trim();
        if (!text.equals("")) {
            node.putField(e.getTagName(), text);
        }
        NodeList list = e.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Object item = list.item(i);
            if (item instanceof Element) {
                Element cItem = (Element) item;
                FieldMapNode cNode = new FieldMapNode(cItem.getTagName());
                node.addChildNode(cNode);
                createFieldMapNode(cNode, cItem);
            }
        }
    }

    static private String createFieldMapElementXMLMsg(FieldMap map) {
        StringBuilder rs = new StringBuilder();
        if (!map.getName().equals("")) {
            rs.append("<").append(map.getName()).append(">\n");
        }
        rs.append(createFieldListElementStr(map.toFieldList()));
        if (!map.getName().equals("")) {
            rs.append("\n</").append(map.getName()).append(">");
        }
        return rs.toString();
    }

    static private String createFieldMapAttrXMLMsg(FieldMap map) {
        StringBuilder rs = new StringBuilder();
        rs.append("<").append(map.getName()).append(" ").append(createFieldListAttrStr(map.toFieldList())).append("/>");
        return rs.toString();
    }

    static private String createFieldListElementStr(List<Field> list) {
        StringBuilder rs = new StringBuilder();
        for (Field f : list) {
            rs.append(createElementString(f.getName(), f.getValue() == null ? "" : f.getValue().toString()));
            rs.append("\n");
        }
        if (rs.length() > 0) {
            rs.delete(rs.length() - 1, rs.length());
        }
        return rs.toString();
    }

    static private String createFieldListAttrStr(List<Field> list) {
        StringBuilder rs = new StringBuilder();
        for (Field f : list) {
            rs.append(f.getName()).append("=\"").append(XMLUtil.escape(f.getValue() == null ? "" : f.getValue().toString())).append("\" ");
        }
        return rs.toString();
    }

    static private List<Field> getFieldList(Element e) {
        List<Field> fields = new ArrayList<Field>();
        NodeList list = e.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                String fName = ((Element) node).getTagName();
                String fValue = ((Element) node).getTextContent().trim();
                fields.add(new Field(fName, fValue));
            }
        }

        NamedNodeMap map = e.getAttributes();
        for (int i = 0; i < map.getLength(); i++) {
            Node node = map.item(i);
            String fName = node.getNodeName();
            String fValue = node.getNodeValue().trim();
            fields.add(new Field(fName, fValue));
        }

        return fields;
    }

    static private List<Field> getAttrFieldList(Element e) {
        List<Field> fields = new ArrayList<Field>();
        NamedNodeMap map = e.getAttributes();
        for (int i = 0; i < map.getLength(); i++) {
            Node node = map.item(i);
            String fName = node.getNodeName();
            String fValue = node.getNodeValue().trim();
            fields.add(new Field(fName, fValue));
        }
        return fields;
    }

    public static String XMLHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

    private static String createElementString(String tag, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tag).append(">").append(XMLUtil.escape(content)).append("</").append(tag).append(">");
        return sb.toString();
    }

    static public void main(String[] args) {
    	PropertyConfigurator.configure("etc/log4j.properties");
    	
    	String xml = "";
        xml = FieldMapUtil.createXMLString(new FieldMap(""));
        System.err.println(xml);
        xml = FieldMapUtil.createXMLString(new FieldMapSet(""));
        System.err.println(xml);
        xml = FieldMapUtil.createXMLString(new FieldMap("Data"));
        System.err.println(xml);
        xml = FieldMapUtil.createXMLString(new FieldMapSet("Data"));
        System.err.println(xml);
        FieldMapSet set = new FieldMapSet("Bonds");
        FieldMap fm = new FieldMap("Bond").putField("ID", "060001").putField("Name", "06国债01");
        xml = FieldMapUtil.createXMLString(fm);
        System.err.println(xml);
        set = new FieldMapSet("Bonds");
        set.addFieldMap(fm).addFieldMap(fm);
        xml = FieldMapUtil.createXMLString(set);
        System.err.println(xml);
        xml = FieldMapUtil.createXMLString(fm, AttrTagMode);
        System.err.println(xml);
        xml = FieldMapUtil.createXMLString(set, AttrTagMode);
        System.err.println(xml);

        fm = new FieldMap("").putField("ID", "060001").putField("Name", "06国债01");
        xml = FieldMapUtil.createXMLString(fm);
        System.err.println(xml);
        xml = FieldMapUtil.createXMLString(fm, AttrTagMode);
        System.err.println(xml);
        try {
            Document doc = XMLUtil.createDocumentFromPath("/FunctionTree.xml");
            FieldMapNode node = createFieldMapNode(doc.getDocumentElement());
            printFieldMapNode(node);

            String s = createXMLString(node);
            System.err.println(s);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    static private void printFieldMapNode(FieldMapNode node) {
        System.err.println(node);
        for (int i = 0; i < node.getChildCount(); i++) {
            printFieldMapNode(node.getChildAt(i));
        }
    }

    static public List<FieldMap> copyFieldMapList(List<FieldMap> list) {
        if (list == null) {
            return null;
        }

        List<FieldMap> copyList = new ArrayList<FieldMap>();
        for (FieldMap fm : list) {
            copyList.add(fm.copyFieldMap());
        }

        return copyList;
    }
}
