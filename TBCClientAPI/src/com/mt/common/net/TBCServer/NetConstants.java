package com.mt.common.net.TBCServer;

/**
 * 一些通信模块相关常量的定义
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 11:34:46
 * To change this template use File | Settings | File Templates.
 * <p/>
 */
public class NetConstants {

    /**
     * 默认消息编码
     */
    public static final String DefaultEncoding = "UTF-8";

    /**
     * Socket模式
     */
    public static final int SocketMode = 0;

    /**
     * Http模式
     */
    public static final int HttpMode = 1;

    /**
     * XSocket模式
     */
    public static final int XSocketMode = 2;

    /**
     * 普通Login格式
     */
    public static final int PLAIN = -1004;

    /**
     * XMLLogin格式
     */
    public static final int XML = -1005;

    //表明网络连接的常量定义
    /**
     * 未定义
     */
    public static final int Undefine = -1000;

    /**
     * 连接状态
     */
    public static final int Connected = -1001;

    /**
     * 断线状态
     */
    public static final int DisConnected = -1002;

    /**
     * 重新连接
     */
    public static final int ReConnect = -1003;

    /**
     * 关闭
     */
    public static final int Closed = -1006;

    /**
     * OID监听注册
     */
    public static final String OID_Add = "CF02";

    /**
     * OID移除监听
     */
    public static final String OID_Remove = "C997";
    /**
     * 普通Login格式的FID
     */
    public static final String PLAIN_RELOGIN = "0004";
    /**
     * XMLLogin格式的FID
     */
    public static final String XML_RELOGIN = "0199";
    /**
     * 系统退出的FID
     */
    public static final String LOGOUT = "0202";
    /**
     * 消息的开头符号
     */
    public static final String StartTag = new String(new byte[]{(byte) 0x1b, (byte) 0x1b});
    /**
     * 消息的结尾符号
     */
    public static final String EndTag = new String(new byte[]{(byte) 0x1c, (byte) 0x1c});
}
