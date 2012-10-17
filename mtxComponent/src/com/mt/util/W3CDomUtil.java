package com.mt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 一个处理一些W3CDom XML老的工具类
 * 这个类方法目前使用的并不多，是一个兼容性考虑的比较老的类
 */
public class W3CDomUtil {

    /**
     * Logger for this class
     */
    static private final Logger logger = LoggerFactory.getLogger(W3CDomUtil.class);

    public static Document buildDocument(String input) {
        //System.err.println("msg======================"+input);
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(new InputSource(new StringReader(input)));
            return doc;
        } catch (ParserConfigurationException e) {
            logger.error("String -  : exception: ", e);
        } catch (FactoryConfigurationError e) {
            logger.error("String -  : exception: ", e);
        } catch (SAXException e) {
            logger.error("String -  : exception: ", e);
        } catch (IOException e) {
            logger.error("String -  : exception: ", e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
        }
        return null;
    }

    /**
     * huang hanhui 2005/12/27 Exp
     */
    public static Document buildDocument(InputStream input, EntityResolver entity) {

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ///steven
            docBuilder.setEntityResolver(entity);
            return docBuilder.parse(input);

        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
            ;
        } catch (FactoryConfigurationError e) {
            logger.error(e.getMessage(), e);
            ;
        } catch (SAXException e) {
            logger.error(e.getMessage(), e);
            ;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            ;
        }
        return null;
    }

    public static Document buildDocument(InputStream input) {

        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return docBuilder.parse(input);

        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
            ;
        } catch (FactoryConfigurationError e) {
            logger.error(e.getMessage(), e);
            ;
        } catch (SAXException e) {
            logger.error(e.getMessage(), e);
            ;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            ;
        }
        return null;
    }

    /**
     * Method escapeElementEntities. Input text is normal text, returns a String
     * with Reference Entities escaped
     *
     * @param text Input text string
     * @return String
     */
    public static String escapeElementEntities(String text) {
        /** buffer used when escaping strings */
        StringBuffer buffer = new StringBuffer();
        char[] block = null;
        int i, last = 0, size = text.length();
        for (i = 0; i < size; i++) {
            String entity = null;
            switch (text.charAt(i)) {
                case '<':
                    entity = "&lt;";
                    break;
                case '>':
                    entity = "&gt;";
                    break;
                case '&':
                    entity = "&amp;";
                    break;
                case '\'':
                    entity = "&apos;";
                case '"':
                    entity = "&quot;";
            }
            if (entity != null) {
                if (block == null) {
                    block = text.toCharArray();
                }
                buffer.append(block, last, i - last);
                buffer.append(entity);
                last = i + 1;
            }
        }
        if (last == 0) {
            return text;
        }
        if (last < size) {
            if (block == null) {
                block = text.toCharArray();
            }
            buffer.append(block, last, i - last);
        }
        String answer = buffer.toString();
        buffer.setLength(0);
        return answer;
    }

    /**
     * Method unEscapeElementEntities. Input text is escaped text, returns a
     * String with Reference Entities restored
     *
     * @param text Input text string
     * @return String
     */
    public static String unEscapeElementEntities(String text) {
        /** buffer used when un-escaping strings */
        if ((text.indexOf("&lt;") == -1) && (text.indexOf("&gt;") == -1)
                && (text.indexOf("&amp;") == -1)
                && (text.indexOf("&apos;") == -1)
                && (text.indexOf("&quot;") == -1)) {
            return text;
        }
        int firstIndexOfAmp = text.indexOf('&');
        if (text.substring(firstIndexOfAmp + 1).startsWith("lt")) {
            return text.substring(0, firstIndexOfAmp)
                    + '<'
                    + unEscapeElementEntities(text.substring(firstIndexOfAmp + 4));

        }
        if (text.substring(firstIndexOfAmp + 1).startsWith("gt")) {
            return text.substring(0, firstIndexOfAmp)
                    + '>'
                    + unEscapeElementEntities(text.substring(firstIndexOfAmp + 4));
        }
        if (text.substring(firstIndexOfAmp + 1).startsWith("amp")) {
            return text.substring(0, firstIndexOfAmp)
                    + '&'
                    + unEscapeElementEntities(text.substring(firstIndexOfAmp + 5));
        }
        if (text.substring(firstIndexOfAmp + 1).startsWith("apos")) {
            return text.substring(0, firstIndexOfAmp)
                    + '\''
                    + unEscapeElementEntities(text.substring(firstIndexOfAmp + 6));
        }
        if (text.substring(firstIndexOfAmp + 1).startsWith("quot")) {
            return text.substring(0, firstIndexOfAmp)
                    + '\"'
                    + unEscapeElementEntities(text.substring(firstIndexOfAmp + 6));
        }
        return null;
    }

    /**
     * 全角转半角
     */
    public static String toDBC(String input) {
        //现在的server已经能处理全角，无需再做转换。
        return input;
//        if (input != null) {
//            if (input.contains("—")) {
//                input = input.replaceAll("—", "-");
//            }
//            if (input.contains("——")) {
//                input = input.replaceAll("——", "-");
//            }
//            if (input.contains("“")) {
//                input = input.replaceAll("“", "\"");
//            }
//            if (input.contains("”")) {
//                input = input.replaceAll("”", "\"");
//            }
//            if (input.contains("（")) {
//                input = input.replaceAll("（", "(");
//            }
//            if (input.contains("）")) {
//                input = input.replaceAll("）", ")");
//            }
//            char[] c = input.toCharArray();
//            for (int i = 0; i < c.length; i++) {
//                if (c[i] == 12288) {
//                    c[i] = (char) 32;
//                    continue;
//                }
//
//                if (c[i] > 65280 && c[i] < 65375) {
//                    c[i] = (char) (c[i] - 65248);
//                }
//            }
//
//            String value = new String(c);
//            return value;
//        } else {
//            return null;
//        }
    }


    public static String handleStr(String input) {
        //return escapeElementEntities(toDBC(input));
        //因为escapeElementEntities已经在XmlUtil.appendXml处理，
        //所以这个函数不能再执行escapeElementEntities
        return input;
    }

    public static String handleStrWithEscapeXML(String input) {
        return escapeElementEntities(input);
    }

    // hhh_w3c
    /*
    * 2005/09/21 Huang HanHui
    * Use XPath
    */
    public static ArrayList<String> getIDList(Document document,
                                              String tagName, String attribName) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//" + tagName, document,
                    XPathConstants.NODESET);//i have problem ?
            int length = list.getLength();
            int ind = 0;
            for (int i = 0; i < length; i++) {
                Element node = (Element) list.item(i);
                String attribValue = node.getAttribute(attribName);
                if (!attribValue.equals("")) {
                    result.add(attribValue.trim());
                }
                ind++;
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    // hhh_w3c end
    public static Map getID(Document document, String id) {
        Map<String, String> map = new HashMap<String, String>();
        NodeList dataList = document.getDocumentElement().getElementsByTagName(
                "Data");
        for (int i = 0; i < dataList.getLength(); i++) {
            Node data = dataList.item(i);
            String key = data.getAttributes().getNamedItem(id).getNodeValue();
            String value = data.getTextContent();
            map.put(key, value);
        }
        return map;
    }

    public static Map getAttribByTagname(Document document, String tagName,
                                         String attribName) {
        Map<String, String> map = new HashMap<String, String>();
        NodeList dataList = document.getDocumentElement().getElementsByTagName(
                tagName);
        for (int i = 0; i < dataList.getLength(); i++) {
            Element data = (Element) dataList.item(i);
            String key = data.getAttribute("ID");
            String val = data.getAttribute(attribName);
            if (val == null) {
                val = "S";
            }
            // note here use attribute value as key to retrieve the id
            map.put(key.trim(), val.trim());
        }
        return map;
    }

    // hhh
    public static Node getButton(Document doc, int index) {
        NodeList list = null;
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            list = (NodeList) xpath.evaluate("//JButton", doc,
                    XPathConstants.NODESET);
            return list.item(index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    public static String getButtonName(Document doc, int index) {
        Node node = getButton(doc, index);
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String name = (String) xpath.evaluate("./@name", node,
                    XPathConstants.STRING);
            return name.trim();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }

    }

    public static int getButtonSize(Document doc) {
        NodeList list = null;
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            list = (NodeList) xpath.evaluate("//JButton", doc,
                    XPathConstants.NODESET);
            return list.getLength();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return 0;
        }

    }

    public static String getButtonText(Document doc, int index) {
        Node node = getButton(doc, index);
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String name = (String) xpath.evaluate("./@text", node,
                    XPathConstants.STRING);
            name.trim();
            return name;
        } catch (XPathExpressionException e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    public static Map getCellType(Document document, String comp, String id) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//" + comp, document,
                    XPathConstants.NODESET);//steven, i have problem ?
            int length = list.getLength();
            for (int i = 0; i < length; i++) {
                Element attr = (Element) list.item(i);
                String key = attr.getAttribute(id);
                String val = attr.getAttribute("TYPE");
                if (val == null) {
                    val = "s";
                }
                map.put(key.trim(), val.trim());
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            //UtilFunc.log("com.mt", ex);
            return null;
        }
    }

    public static Map getColumnRenderer(Document document, String name) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//TableColumn",
                    document, XPathConstants.NODESET);
            int length = list.getLength();
            for (int i = 0; i < length; i++) {
                Element attr = (Element) list.item(i);
                String key = attr.getAttribute("headerValue");
                String val = attr.getAttribute(name);
                map.put(key.trim(), val.trim());
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            //UtilFunc.log("com.mt", ex);
            return null;
        }
    }

    public static Node getComboBox(Document doc, int index) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList rows = (NodeList) xpath.evaluate("//JComboBox", doc,
                    XPathConstants.NODESET);
            return rows.item(index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    public static int getComboBoxSize(Document doc) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//JComboBox", doc,
                    XPathConstants.NODESET);
            return list.getLength();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return -1;
        }
    }

    public static String getComboBoxName(Document doc, int index) {
        Element node = (Element) getComboBox(doc, index);
        String name = node.getAttribute("name");
        return name;
    }

    /**
     * "com.mt" differs from the above that getIDList returns a vector
     * containing ONLY items with an ACT attrbute. But "com.mt" one traverses
     * the whole list and returns it (It's very stupid since it virtually
     * ignores the
     * <code>name<code> argument and returns the element whether it has that
     * attribute or not. Should reduce it to save time
     */
    public static ArrayList getFullIDList(Document document, String id,
                                          String name) {

        ArrayList<String> result = new ArrayList<String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//" + id, document,
                    XPathConstants.NODESET);
            int length = list.getLength();
            int ind = 0;
            for (int i = 0; i < length; i++) {
                Element attr = (Element) list.item(i);
                String key = attr.getAttribute(name);
                // note here use attribute value as key to retrieve the id
                result.add(key.trim());
                ind++;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            //UtilFunc.log("com.mt", ex);
            return null;
        }
    }

    public static ArrayList getIDList(Document doc, String id) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//" + id, doc,
                    XPathConstants.NODESET);
            int length = list.getLength();
            for (int i = 0; i < length; i++) {
                Element attr = (Element) list.item(i);
                String key = attr.getAttribute("name");
                result.add(key.trim());
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }

    }

    public static Node getJComponent(Document doc, int index, String comp) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList rows = (NodeList) xpath.evaluate("//" + comp, doc,
                    XPathConstants.NODESET);
            return rows.item(index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    public static int getJComponentSize(Document doc, String comp) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList rows = (NodeList) xpath.evaluate("//" + comp, doc,
                    XPathConstants.NODESET);
            return rows.getLength();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return -1;
        }
    }

    public static String getJPanelSize(Document doc) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//JPanel", doc,
                    XPathConstants.NODESET);
            Element element = (Element) list.item(0);
            Attr attr = element.getAttributeNode("constraints");
            if (attr != null) {
                return attr.getNodeValue();
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    public static Map getKey(Document document, String id, String name) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//" + id, document,
                    XPathConstants.NODESET);
            int length = list.getLength();
            for (int i = 0; i < length; i++) {
                Element attr = (Element) list.item(i);
                String key = attr.getAttribute(name);
                String val = attr.getTextContent();
                // note here use attribute value as key to retrieve the id
                map.put(val.trim(), key.trim());
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            //UtilFunc.log("com.mt", ex);
            return null;
        }
    }

    public static String getName(Document doc, int index, String comp) {
        Node node = getJComponent(doc, index, comp);
        String name = node.getAttributes().getNamedItem("name").getNodeValue();
        return name;
    }

    public static List getRenderer(Document document) {
        List<String> list = new ArrayList<String>();
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list2 = (NodeList) xpath.evaluate("//TableColumn",
                    document, XPathConstants.NODESET);
            int length = list2.getLength();
            for (int i = 0; i < length; i++) {
                Element attr = (Element) list2.item(i);
                // String key = attr.valueOf("./@headerValue");
                String val = attr.getAttribute("cellRenderer");
                list.add(val.trim());
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            //UtilFunc.log("com.mt", ex);
            return null;
        }
    }

    public static int getSize(Document doc, String id) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xpath.evaluate("//" + id, doc,
                    XPathConstants.NODESET);
            return list.getLength();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return -1;
        }
    }

    public static Node getTable(Document doc, int index, String id) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList rows = (NodeList) xpath.evaluate("//" + id, doc,
                    XPathConstants.NODESET);
            return rows.item(index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ;
            return null;
        }
    }

    public static String getTableName(Document doc, int index, String id) {
        Element node = (Element) getTable(doc, index, id);
        String name = node.getAttribute("name");
        return name;
    }
}
