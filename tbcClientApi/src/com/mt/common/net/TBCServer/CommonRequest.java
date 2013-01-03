package com.mt.common.net.TBCServer;

/**
 * 一个描述请求响应的对象
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 14:45:44
 * To change this template use File | Settings | File Templates.
 */
public class CommonRequest {

    /**
     * 服务器连接名
     */
    private String sName;

    /**
     * 请求的服务名
     */
    private String fid;

    /**
     * 请求的消息
     */
    private Object requestMsg;

    /**
     * 请求的超时时间
     */
    private int timeout;

    /**
     * 回应的处理者
     */
    private CommonMsgCallback callback;

    /**
     * 构建一个CommonRequest
     *
     * @param fid
     * @param callback
     */
    public CommonRequest(String sName, String fid, CommonMsgCallback callback) {
        this(sName, fid, null, callback);
    }

    /**
     * 构建一个CommonRequest
     *
     * @param fid
     * @param requestMsg
     * @param callback
     */
    public CommonRequest(String sName, String fid, Object requestMsg, CommonMsgCallback callback) {
        this(sName, fid, requestMsg, TBCServerConnection.DefaultTimeout, callback);
    }

    /**
     * 构建一个CommonRequest
     *
     * @param fid
     * @param requestMsg
     * @param timeout
     * @param callback
     */
    public CommonRequest(String sName, String fid, Object requestMsg, int timeout, CommonMsgCallback callback) {
        this.sName = sName;
        this.fid = fid;
        this.requestMsg = requestMsg;
        this.timeout = timeout;
        this.callback = callback;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFid() {
        return fid;
    }

    public void setRequestMsg(Object requestMsg) {
        this.requestMsg = requestMsg;
    }

    public Object getRequestMsg() {
        return requestMsg;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setCallback(CommonMsgCallback callback) {
        this.callback = callback;
    }

    public CommonMsgCallback getCallback() {
        return callback;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }
}
