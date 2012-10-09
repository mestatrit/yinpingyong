package com.mt.common.net.TBCServer.internal;

import com.jcraft.jhttptunnel.InBoundSocket;
import com.jcraft.jhttptunnel.JHttpTunnelClient;
import com.jcraft.jhttptunnel.OutBoundSocket;
import com.mt.common.net.TBCServer.TBCServerConnectionInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.mt.common.net.TBCServer.NetConstants.EndTag;
import static com.mt.common.net.TBCServer.NetConstants.StartTag;

/**
 * Http通信模式
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 12:08:06
 * To change this template use File | Settings | File Templates.
 */
public class HttpCommunicationsMode extends CommunicationsMode {

    private JHttpTunnelClient jhtc;
    private TBCServerConnectionInfo info;
    private MTBufferedReader reader;
    private OutputStreamWriter writer;

    public void start(TBCServerConnectionInfo info) throws IOException {
        this.info = info;
        jhtc = new JHttpTunnelClient(info.IP, Integer.parseInt(info.port));
        jhtc.setInBound(new InBoundSocket());
        jhtc.setOutBound(new OutBoundSocket());
        jhtc.connect();
        reader = new MTBufferedReader(new InputStreamReader(jhtc.getInputStream(),
                info.msgEncoding));
        writer = new OutputStreamWriter(jhtc.getOutputStream(), info.msgEncoding);
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
        jhtc.close();
    }

    @Override
    public void setReadTimeOut(int m) {
    }
}
