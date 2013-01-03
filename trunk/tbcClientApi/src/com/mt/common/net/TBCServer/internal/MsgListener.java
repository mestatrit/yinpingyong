package com.mt.common.net.TBCServer.internal;

import com.mt.common.net.TBCServer.NetConstants;
import com.mt.common.net.TBCServer.TBCServerConnection;
import com.mt.common.net.TBCServer.TBCServerConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 底层网络消息监听器
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 12:25:56
 * To change this template use File | Settings | File Templates.
 */
public class MsgListener implements Runnable {

    private MsgQueue oueue;
    private TBCServerConnectionInfo info;
    private TBCServerConnection connection;
    private CommunicationsMode cMode;
    private boolean isReadMsgLog = false;
    private Thread eHandler;
    private Thread readThread;
    private Lock start_stopLock = new ReentrantLock();
    private static final String FID_COMPRESSED = "CE01";
    static final Logger logger = LoggerFactory.getLogger(MsgListener.class);

    public MsgListener(TBCServerConnection con, MsgQueue oueue) {
        this.oueue = oueue;
        this.connection = con;
        this.info = connection.getConnectionInfo();
        try {
            String rs = TBCServerConnection.GlobalConnectionSetting.getProperty("ReadMsgLog");
            isReadMsgLog = rs.equals("true") ? true : false;
        } catch (Exception e) {
        }
    }

    /**
     * 开始监听
     *
     * @param cMode
     */
    public void startListen(CommunicationsMode cMode) {
        start_stopLock.lock();
        try {
            this.cMode = cMode;
            readThread = new Thread(this);
            readThread.start();
        } finally {
            start_stopLock.unlock();
        }
    }

    /**
     * 停止监听
     */
    public void stopListen() throws InterruptedException {
        start_stopLock.lock();
        try {
            if (readThread != null) {
                readThread.interrupt();
                /**如果线程阻塞在readMsg()上，能否被中断取决于不同的IO实现
                 * 所以在中断后再关闭其IO，使得线程一定可以从readMsg()上跳出
                 */
                cMode.close();
                readThread.join();
            }
        } finally {
            start_stopLock.unlock();
        }
    }

    public void registerExceptionHandler(Thread handler) {
        this.eHandler = handler;
    }

    @Override
    public void run() {
        while (!readThread.isInterrupted()) {
            try {
                String msg = cMode.readMsg();
                //如果线程中断,立即退出
                if (readThread.isInterrupted()) {
                    logger.info("{}网络消息监听线程被中断", info.name);
                    break;
                }

                if (msg == null || msg.equals("")) {
                    logger.error("{}服务器返回空消息(null或\"\"),很可能已经死掉!程序将断线,重新尝试与服务器连接...", info.name);
                    this.eHandler.start();
                    break;
                }

                if (msg.startsWith("MSG_CRC_ERROR")) {
                    logger.warn("{}服务器返回有错误：MSG_CRC_ERROR[{}]", info.name, msg);
                    continue;
                }
                if (isReadMsgLog) {
                    if (!msg.substring(5, 9).equals(FID_COMPRESSED) && !msg.substring(5, 9).equals("0212"))//不记录时间处理的log
                        logger.info("读取{}服务器消息:{}", info.name, msg);
                }
                oueue.putMsg(msg);
            } catch (InterruptedException ex) {
                logger.info("{}网络消息监听线程被中断", info.name);
                break;
            } catch (ThreadDeath ex) {
                logger.error(info.name + "网络消息监听线程被终止", ex);
                break;
            } catch (ClosedChannelException ex) {
                if (connection.getStatus() == NetConstants.Closed) {
                    logger.info("{}网络channel被关闭,网络消息监听线程停止", info.name);
                    break;
                } else {
                    logger.error(info.name + "网络消息监听模块发生异常", ex);
                    this.eHandler.start();
                    break;
                }

            } catch (SocketException ex) {
                if (connection.getStatus() == NetConstants.Closed) {
                    logger.info("{}网络socket被关闭,网络消息监听线程停止", info.name);
                    break;
                } else {
                    logger.error(info.name + "网络消息监听模块发生异常", ex);
                    this.eHandler.start();
                    break;
                }
            } catch (Exception ex) {
                logger.error(info.name + "网络消息监听模块发生异常", ex);
                this.eHandler.start();
                break;
            }
        }
    }
}
