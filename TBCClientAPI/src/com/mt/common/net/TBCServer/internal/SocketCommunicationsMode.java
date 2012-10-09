package com.mt.common.net.TBCServer.internal;

import com.mt.common.net.TBCServer.TBCServerConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

import static com.mt.common.net.TBCServer.NetConstants.EndTag;
import static com.mt.common.net.TBCServer.NetConstants.StartTag;

/**
 * 标准Socket通信模式
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 12:05:26
 * To change this template use File | Settings | File Templates.
 */
public class SocketCommunicationsMode extends CommunicationsMode {

    private Socket socket;
    private TBCServerConnectionInfo info;
    private MTBufferedReader reader;
    private OutputStreamWriter writer;
    private final Logger logger = LoggerFactory.getLogger(SocketCommunicationsMode.class);

    public void start(TBCServerConnectionInfo info) throws IOException {
        this.info = info;
        if (info.isSSL) {
            socket = SSLSocketFactory.getDefault().createSocket(info.IP, Integer.parseInt(info.port));
        } else {
            socket = new Socket(info.IP, Integer.parseInt(info.port));
        }
        socket.setTcpNoDelay(true);
        socket.setKeepAlive(true);
        setReadTimeOut(info.timeOut * 1000);
        reader = new MTBufferedReader(new InputStreamReader(socket.getInputStream(),
                info.msgEncoding));
        writer = new OutputStreamWriter(socket.getOutputStream(), info.msgEncoding);

    }

    @Override
    public String readMsg() throws IOException {
        return reader.readLine();
    }

    @Override
    public void sendMsg(String msg) throws IOException {
        writer.write(StartTag + msg + EndTag);
        writer.flush();
    }

    @Override
    public void close() {
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception ex) {
            logger.error(info.name + "关闭底层通信发生异常", ex);
        }
    }

    @Override
    final public void setReadTimeOut(int m) {
        try {
            socket.setSoTimeout(m);
        } catch (SocketException ex) {
            logger.error(info.name + "设置底层通信超时发生异常", ex);
        }
    }
}
