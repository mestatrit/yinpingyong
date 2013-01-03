package com.mt.common.net.TBCServer;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.xml.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 一个通用的消息结构
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 14:32:30
 * To change this template use File | Settings | File Templates.
 */
public class CommonMsg {

    private final Logger logger = LoggerFactory.getLogger(CommonMsg.class);

    /**
     * 发送消息
     */
    static public final String SEND = "S";

    /**
     * 接收消息
     */
    static public final String Receive = "R";

    /**
     * 超时错误
     */
    static public final int TIMEOUT = -100;
    /**
     * 没有数据错误
     */
    static public final int NO_DATA_FOUND = -200;

    /**
     * 服务器消息无法解析
     */
    static public final String ServerMsgEx = "服务器返回的消息解析失败:(";

    /**
     * 消息类型
     */
    private String msgType;
    /**
     * FieldMap生成XML格式的模式TagNameMode或者AttrTagMode
     */
    private int mode;
    /**
     * 外部设置进来的FieldMap数据
     */
    private Object fieldData;
    /**
     * 从内部的xmlDocument解析的数据,FieldMap
     */
    private FieldMap pfData;
    /**
     * 从内部的xmlDocument解析的数据,FieldMap集合
     */
    private FieldMapSet pfsData;
    /**
     * 从内部的xmlDocument解析的数据,FieldMap树
     */
    private FieldMapNode pfnData;
    /**
     * 是否错误
     */
    private boolean isError = false;
    /**
     * 错误消息
     */
    private String errorMsg = "";
    /**
     * 错误类型
     */
    private int errrorType = -1;
    /**
     * 消息的FID
     */
    private String fid;
    /**
     * 原始的XML结构
     */
    private Document xmlDocument = null;
    /**
     * 原始的字符串
     */
    private String strMsg = "";

    public CommonMsg(String msgType) {
        this(msgType, FieldMapUtil.TagNameMode);
    }

    public CommonMsg(String msgType, int mode) {
        this.mode = mode;
        this.msgType = msgType;
    }

    /**
     * 获得构建XML的模式
     *
     * @return
     */
    public int getMode() {
        return mode;
    }

    /**
     * 获得消息类型
     *
     * @return
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * 是否错误
     *
     * @return
     */
    public boolean isError() {
        return isError;
    }

    /**
     * 设置是否错误
     *
     * @param e
     */
    public void setError(boolean e) {
        this.isError = e;
    }

    /**
     * 设置错误类型
     *
     * @param errrorType
     */
    public void setErrrorType(int errrorType) {
        setError(true);
        this.errrorType = errrorType;
    }

    /**
     * 获得错误类型
     *
     * @return
     */
    public int getErrrorType() {
        return errrorType;
    }

    /**
     * 获得错误消息
     *
     * @return
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误消息
     *
     * @param msg
     */
    public void setErrorMsg(String msg) {
        setError(true);
        this.errorMsg = msg;
    }

    public void setFID(String fid) {
        this.fid = fid;
    }

    public String getFID() {
        return fid;
    }

    /**
     * 设置FieldMap
     *
     * @param fm
     */
    public void setFieldMap(FieldMap fm) {
        fieldData = fm;
    }

    /**
     * 设置FieldMapSet
     *
     * @param fms
     */
    public void setFieldMapSet(FieldMapSet fms) {
        fieldData = fms;
    }

    /**
     * 设置FieldMapNode
     *
     * @param node
     */
    public void setFieldMapNode(FieldMapNode node) {
        fieldData = node;
    }

    /**
     * 获得FieldMap
     *
     * @return
     */
    public FieldMap getFieldMap() {
        if (msgType.equals(Receive)) {
            if (xmlDocument != null) {
                if (pfData == null) {
                    pfData = FieldMapUtil.createFieldMap(xmlDocument.getDocumentElement());
                }
                return pfData;
            } else {
                throw new RuntimeException(isError() ? getErrorMsg() : ServerMsgEx);
            }
        } else {
            if (fieldData != null) {
                return (FieldMap) fieldData;
            } else {
                return null;
            }
        }
    }

    /**
     * 获得FieldMapSet
     *
     * @return
     */
    public FieldMapSet getFieldMapSet() {
        if (msgType.equals(Receive)) {
            if (xmlDocument != null) {
                if (pfsData == null) {
                    pfsData = FieldMapUtil.createFieldMapSet(xmlDocument.getDocumentElement());
                }
                return pfsData;
            } else {
                throw new RuntimeException(isError() ? getErrorMsg() : ServerMsgEx);
            }
        } else {
            if (fieldData != null) {
                return (FieldMapSet) fieldData;
            } else {
                return null;
            }
        }
    }

    /**
     * 获得FieldMapNode
     *
     * @return
     */
    public FieldMapNode getFieldMapNode() {
        if (msgType.equals(Receive)) {
            if (xmlDocument != null) {
                if (pfnData == null) {
                    pfnData = FieldMapUtil.createFieldMapNode(xmlDocument.getDocumentElement());
                }
                return pfnData;
            } else {
                throw new RuntimeException(isError() ? getErrorMsg() : ServerMsgEx);
            }
        } else {
            if (fieldData != null) {
                return (FieldMapNode) fieldData;
            } else {
                return null;
            }
        }
    }

    /**
     * 明确获得外部设置的FieldData,有三种可能的类型:FieldMap FieldMapSet FieldMapNode
     *
     * @return
     */
    public Object getFieldData() {
        return fieldData;
    }

    /**
     * 获得原始的XML结构
     *
     * @return
     */
    public Document getXmlDocument() {
        if (xmlDocument == null) {
            throw new RuntimeException(isError() ? getErrorMsg() : ServerMsgEx);
        }
        return xmlDocument;
    }

    /**
     * 设置原始的字符串
     *
     * @param msg
     */
    public void setMsgString(String msg) {
        try {
            this.strMsg = msg;
            if (strMsg.startsWith("F")) {//消息有严重错误
                setErrorMsg(strMsg);
                return;
            }

            //不是"<"开头就本更不可能是XML
            if (!strMsg.startsWith("<")) {
                return;
            }

            try {
                xmlDocument = XMLUtil.createDocument(msg);
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
                return;
            }

            Element root = xmlDocument.getDocumentElement();
            if (root.getTagName().toLowerCase().equals("error")) {
                String err = root.getTextContent().trim();
                if (err.toLowerCase().contains("no data found") || err.contains("查无资料")) {
                    setErrrorType(CommonMsg.NO_DATA_FOUND);
                    setErrorMsg("没有数据");
                } else {
                    setErrorMsg(err);
                }
            } else if (root.getTagName().toLowerCase().equals("return")) {
                NodeList list = getNodeListIgnoreCase(root, "Status");
                if (list.getLength() > 0) {
                    String s = list.item(0).getTextContent().trim();
                    if (s.equals("Y")) {
                        setError(false);
                    } else {
                        String eMsg = getNodeListIgnoreCase(root, "Message").item(0).getTextContent().trim();
                        setErrorMsg(eMsg);
                    }
                } else {
                    String s = getNodeListIgnoreCase(root, "Result").item(0).getTextContent().trim();
                    if (s.equals("true") || s.equals("success")) {
                        setError(false);
                    } else {
                        String eMsg = getNodeListIgnoreCase(root, "Cause").item(0).getTextContent().trim();
                        setErrorMsg(eMsg);
                    }
                }
            }
        } catch (Throwable t) {
            logger.error("setMsg", t);
            setErrorMsg(t.getMessage());
        }
    }

    /**
     * server返回的tag有时是同样的意义，但大小写不同，比如 Status ,STATUS ,status
     *
     * @param element
     * @param tag
     * @return
     */
    private NodeList getNodeListIgnoreCase(Element element, String tag) {
        NodeList list = element.getElementsByTagName(tag);
        if (list == null || list.getLength() == 0) {
            list = element.getElementsByTagName(tag.toUpperCase());
        }
        if (list == null || list.getLength() == 0) {
            list = element.getElementsByTagName(tag.toLowerCase());
        }
        return list;
    }

    /**
     * 获得原始的字符串
     *
     * @return
     */
    public String getMsgString() {
        return strMsg;
    }

    /**
     * toString定义
     */
    @Override
    public String toString() {
        String rs = "";
        if (isError()) {
            rs = getErrorMsg();
        } else {
            if (this.strMsg != null) {
                rs = this.strMsg;
            } else {
                if (fieldData != null) {
                    rs = fieldData.toString();
                } else {
                    rs = "空消息";
                }
            }
        }
        return rs;
    }

    /**
     * 从模块名和消息创建一个支持模块名的消息
     * @param mName
     * @param msg
     * @return
     */
    static public String createModuleMsg(String mName,Object msg){
        String mTag = "<COMSTAR_SYSTEM>"+mName+"</COMSTAR_SYSTEM>";
        String oldMsg="";
        int mode = FieldMapUtil.TagNameMode;
        if(msg instanceof CommonMsg){
            mode = ((CommonMsg) msg).getMode();
            msg = ((CommonMsg) msg).getFieldData();
        }
        if(msg instanceof FieldMap){
            oldMsg = FieldMapUtil.createXMLString((FieldMap)msg,mode);
        }else if(msg instanceof FieldMapSet){
            oldMsg = FieldMapUtil.createXMLString((FieldMapSet)msg,mode);
        }else if(msg instanceof FieldMapNode){
            oldMsg = FieldMapUtil.createXMLString((FieldMapNode)msg,mode);
        }else{
            oldMsg=msg.toString();
        }
        return FieldMapUtil.XMLHeader+mTag+oldMsg.replace(XMLUtil.XMLHeader, "");
    }
}
