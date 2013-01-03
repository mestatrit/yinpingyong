package com.mt.common.net.TBCServer.internal;

import com.mt.common.net.TBCServer.TBCServerConnectionInfo;

import java.io.IOException;

/**
 * 通信模式定义
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 12:01:21
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommunicationsMode {

    /**
     * 开启
     *
     * @param info
     * @throws IOException
     */
    abstract public void start(TBCServerConnectionInfo info) throws IOException;

    /**
     * 读取消息
     *
     * @return
     */
    abstract public String readMsg() throws IOException;

    /**
     * 发送消息
     *
     * @param msg
     */
    abstract public void sendMsg(String msg) throws IOException;

    /**
     * 设置超时，单位毫秒
     *
     * @param m
     */
    abstract public void setReadTimeOut(int m);

    /**
     * 关闭
     */
    abstract public void close();
}
