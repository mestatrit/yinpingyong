/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.common.net.TBCServer.internal;

import com.mt.common.net.TBCServer.TBCServerConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.mt.common.net.TBCServer.NetConstants.EndTag;
import static com.mt.common.net.TBCServer.NetConstants.StartTag;

/**
 * XSocket通信模式
 * Created by NetBeans.
 *
 * @Author: hanhui
 * @Date: 2010-7-10
 */
public class XSocketCommunicationsMode extends CommunicationsMode {

    private IBlockingConnection bc;
    private TBCServerConnectionInfo info;
    private final Logger logger = LoggerFactory.getLogger(XSocketCommunicationsMode.class);

    @Override
    public void start(TBCServerConnectionInfo info) throws IOException {
        this.info = info;
        if (info.isSSL) {
            try {
                bc = new BlockingConnection(info.IP, Integer.parseInt(info.port), SSLContext.getDefault(), true);
            } catch (NoSuchAlgorithmException ex) {
                logger.error("构建SSL BlockingConnection失败", ex);
                throw new RuntimeException(ex);
            }
        } else {
            bc = new BlockingConnection(info.IP, Integer.parseInt(info.port));
        }
        setReadTimeOut(info.timeOut * 1000);
    }

    @Override
    final public void setReadTimeOut(int m) {
        try {
            bc.setReadTimeoutMillis(m);
        } catch (Exception ex) {
            logger.error(info.name + "设置底层通信超时发生异常", ex);
        }
    }

    @Override
    public void close() {
        try {
            bc.close();
        } catch (Exception ex) {
            logger.error(info.name + "关闭底层通信发生异常", ex);
        }
    }

    @Override
    public String readMsg() throws IOException {
        return bc.readStringByDelimiter(EndTag, info.msgEncoding).substring(2);
    }

    @Override
    public void sendMsg(String msg) throws IOException {
        bc.write(StartTag + msg + EndTag, info.msgEncoding);
        bc.flush();
    }
}
