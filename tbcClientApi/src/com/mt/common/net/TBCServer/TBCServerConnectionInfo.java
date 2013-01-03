package com.mt.common.net.TBCServer;

/**
 * TBCServer连接信息
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 11:38:57
 * To change this template use File | Settings | File Templates.
 */
public class TBCServerConnectionInfo {

    /**
     * 连接名字
     */
    public String name = "";

    /**
     * 地址
     */
    public String IP;
    /**
     * 端口
     */
    public String port;

    /**
     * 监控端口
     */
    public String monitorPort;
    /**
     * 用户名
     */
    public String userName;

    /**
     * 密码
     */
    public String password;

    /**
     * 机构号码
     */
    public String cusNumber;

    /**
     * 消息字符集
     */
    public String msgEncoding = NetConstants.DefaultEncoding;

    /**
     * 压缩消息字符集
     */
    public String zipMsgEncoding = NetConstants.DefaultEncoding;

    /**
     * 通信模式
     */
    public int communicationsMode = NetConstants.SocketMode;

    /**
     * 是否使用SSL
     */
    public boolean isSSL = false;

    /**
     * 是否开启0212的对时
     */
    public boolean isTimer0212 = true;

    /**
     * 0212对时的间隔时间，单位是秒
     */
    public int intervalTime0212 = 15;

    /**
     * 是否重连
     */
    public boolean isReconnect = true;

    /**
     * 重连间隔的时间，单位是秒
     */
    public int intervalTime = 10;

    /**
     * 登录的超时时间,单位是秒
     */
    public int timeOut = 60;

    /**
     * 登录消息格式
     */
    public int loginMsgFormat = NetConstants.PLAIN;

    /**
     * 客户端版本号
     */
    public String clientVersion = "v1_0_20771231a";

    /**
     * 是否支持服务器强制退出
     */
    public boolean isServerLogout = true;

    /**
     * 客户端的IP
     */
    public String c_ip="";

    /**
     * 客户端的名称
     */
    public String c_name="";

    /**
     * 客户端的mac
     */
    public String c_mac="";
}
