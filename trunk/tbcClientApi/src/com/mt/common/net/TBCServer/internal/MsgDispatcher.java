package com.mt.common.net.TBCServer.internal;

import com.mt.common.net.TBCServer.CommonMsg;
import com.mt.common.net.TBCServer.CommonMsgCallback;
import com.mt.common.net.TBCServer.TBCServerConnection;
import com.mt.common.net.TBCServer.TBCServerConnectionInfo;
import com.mt.util.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消息分派器
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 12:34:25
 * To change this template use File | Settings | File Templates.
 */
public class MsgDispatcher implements Runnable {

    private MsgQueue queue;
    private TBCServerConnectionInfo info;
    private TBCServerConnection connection;
    private Thread dispatchThread;
    private TimeoutScanner tScanner;
    private boolean isReadMsgLog = false;
    private Map<String, List<CommonMsgCallback>> listenerMap;
    private Map<String, CommonMsgCallback> msgIDMap;
    private List<TimeoutInfo> timeoutInfoList;
    private Lock callbackLock = new ReentrantLock();
    private Lock start_stopLock = new ReentrantLock();
    private final Logger logger = LoggerFactory.getLogger(MsgDispatcher.class);

    public MsgDispatcher(TBCServerConnection con, MsgQueue mq) {
        this.queue = mq;
        this.connection = con;
        this.info = connection.getConnectionInfo();
        listenerMap = new ConcurrentHashMap<String, List<CommonMsgCallback>>();
        msgIDMap = new ConcurrentHashMap<String, CommonMsgCallback>();
        timeoutInfoList = Collections.synchronizedList(new ArrayList<TimeoutInfo>());
        try {
            String rs = TBCServerConnection.GlobalConnectionSetting.getProperty("ReadMsgLog");
            isReadMsgLog = rs.equals("true") ? true : false;
        } catch (Exception e) {
        }
    }

    /**
     * 配置请求回调
     *
     * @param fid
     * @param msgID
     * @param timeout
     * @param callback
     * @return
     */
    public String putRequestCallback(String fid, String msgID, int timeout, CommonMsgCallback callback) {
        String id = fid + msgID;
        timeoutInfoList.add(new TimeoutInfo(fid, msgID, timeout));
        msgIDMap.put(id, callback);
        return id;
    }

    /**
     * 移除请求回调
     *
     * @param id
     */
    public void removeRequestCallback(String id) {
        msgIDMap.remove(id);
    }

    /**
     * 对某个FID注册回调
     *
     * @param fid
     * @param callback
     */
    public void resgisterCommonMsgCallback(String fid, CommonMsgCallback callback) {
        List<CommonMsgCallback> listeners = listenerMap.get(fid);
        if (listeners == null) {
            listeners = Collections.synchronizedList(new ArrayList<CommonMsgCallback>());
        }
        listeners.add(callback);
        listenerMap.put(fid, listeners);
        logger.info("{}增加[{}]Listener", info.name, fid);
    }

    /**
     * 移除某个FID的回调注册
     *
     * @param fid
     * @param callback
     */
    public void removeCommonMsgCallback(String fid, CommonMsgCallback callback) {
        List<CommonMsgCallback> listeners = listenerMap.get(fid);
        if (listeners != null) {
            listeners.remove(callback);
            if (listeners.isEmpty()) {
                listenerMap.remove(fid);
            }
            logger.info("{}移除[{}]Listener", info.name, fid);
        }
    }

    /**
     * 查看某个FID是否被注册了Callback
     *
     * @param fid
     * @return
     */
    public boolean isResgisterCommonMsgCallback(String fid) {
        return listenerMap.containsKey(fid);
    }

    /**
     * 查看某个FID是否被注册了指定的Callback
     *
     * @param fid
     * @param callback
     * @return
     */
    public boolean isResgisterCommonMsgCallback(String fid, CommonMsgCallback callback) {
        List<CommonMsgCallback> listeners = listenerMap.get(fid);
        if (listeners != null && callback != null) {
            return listeners.contains(callback);
        }
        return false;
    }

    /**
     * 启动分派
     */
    public void startDispatch() {
        start_stopLock.lock();
        try {
            dispatchThread = new Thread(this);
            dispatchThread.start();
            tScanner = new TimeoutScanner();
            tScanner.start();
        } finally {
            start_stopLock.unlock();
        }

    }

    /**
     * 停止分派
     */
    public void stopDispatch() throws InterruptedException {
        start_stopLock.lock();
        try {
            if (dispatchThread != null) {
                dispatchThread.interrupt();
                dispatchThread.join();
            }
            if (tScanner != null) {
                tScanner.interrupt();
                tScanner.join();
            }
        } finally {
            start_stopLock.unlock();
        }
    }

    @Override
    public void run() {
        while (!dispatchThread.isInterrupted()) {
            try {
                String msg = queue.getMsg();
                msg = msg.substring(5);
                String fid = msg.substring(0, 4);
                if (fid.equals("CE01")) {
                    String zipped = msg.substring(msg.indexOf('<'), msg.lastIndexOf('>') + 1);
                    Document doc = null;
                    try {
                        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        doc = docBuilder.parse(new InputSource(new StringReader(zipped)));
                    } catch (Exception ex) {
                        logger.error(info.name + "无法解析成XML的压缩消息", ex);
                        continue;
                    }

                    String content = doc.getDocumentElement().getTextContent();
                    content = content.substring(content.indexOf("\n") + 1, content.lastIndexOf("\n"));
                    byte[] decGZBytes = Base64.decode(content);

                    try {
                        String restoredGZStr = new String(decGZBytes, info.zipMsgEncoding);
                        if (isReadMsgLog) {
                            logger.info("{}消息解压:{}", info.name, restoredGZStr);
                        }
                        msg = restoredGZStr;
                    } catch (Exception ex) {
                        logger.error(info.name + "一条压缩消息解压失败", ex);
                        continue;
                    }
                }

                fid = msg.substring(0, 4);
                String winid = msg.substring(4, 8);
                String para = "";
                int rIndex = msg.indexOf("<");
                if (rIndex == -1) {
                    para = msg.substring(4 + 9);
                } else {
                    para = msg.substring(rIndex);
                }
                procCommonMsg(fid, winid, para);
            } catch (InterruptedException ex) {
                logger.info("{}消息分派线程被中断", info.name);
                break;
            } catch (ThreadDeath ex) {
                logger.error(info.name + "消息分派线程被终止", ex);
                break;
            } catch (Throwable t) {
                logger.error(info.name + "分派一条消息时发生未预料的异常", t);
            }
        }
    }

    private void procCommonMsg(String fid, String winid, String para) {
        CommonMsg cmsg = new CommonMsg(CommonMsg.Receive);
        cmsg.setFID(fid);
        cmsg.setMsgString(para);
        callHandler(fid, winid, cmsg);
    }

    private void callHandler(String fid, String msgid, CommonMsg msg) {
        List<CommonMsgCallback> listeners = listenerMap.get(fid);
        if (listeners != null) {
            callMsgListener(listeners, msg);
        }
        callRequestCallback(fid, msgid, msg);
    }

    private void callRequestCallback(String fid, String msgID, CommonMsg msg) {
        try {
            callbackLock.lock();
            String id = fid + msgID;
            CommonMsgCallback callBack = this.msgIDMap.get(id);
            if (callBack != null) {
                String[] var = {info.name, fid, msgID};
                logger.info("{}[{}|{}]调用对应Callback", var);
                try {
                    callBack.onMessage(msg);
                } catch (Throwable ex) {
                    logger.error(info.name + "调用onMessage发生异常", ex);
                }
                this.msgIDMap.remove(id);
            } else {
                //找不到Callback
            }
        } finally {
            callbackLock.unlock();
        }
    }

    private void callMsgListener(List<CommonMsgCallback> listeners, CommonMsg msg) {
        for (int i = 0; i < listeners.size(); i++) {
            try {
                listeners.get(i).onMessage(msg);
            } catch (Throwable ex) {
                logger.error(info.name + "调用onMessage发生异常", ex);
                continue;
            }
        }
    }

    class TimeoutScanner extends Thread {

        private int scanInterval = 2000;

        public TimeoutScanner() {
            try {
                String si = TBCServerConnection.GlobalConnectionSetting.getProperty("TimeoutScanInterval");
                scanInterval = Integer.parseInt(si);
            } catch (Exception e) {
            }
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    for (int i = 0; i < timeoutInfoList.size(); i++) {
                        try {
                            long t = System.currentTimeMillis() - timeoutInfoList.get(i).requestTime;
                            t = t / 1000;
                            if (t >= timeoutInfoList.get(i).timeout) {//已经超时
                                String fid = timeoutInfoList.get(i).fid;
                                String mid = timeoutInfoList.get(i).mid;
                                timeoutInfoList.remove(i);
                                CommonMsg msg = new CommonMsg(CommonMsg.Receive);
                                msg.setFID(fid);
                                msg.setErrrorType(CommonMsg.TIMEOUT);
                                msg.setErrorMsg("请求超时");
                                callRequestCallback(fid, mid, msg);
                            }
                        } catch (Exception e) {
                            logger.error(info.name + "超时扫描过程中捕获一个未处理异常", e);
                        }
                    }
                    Thread.sleep(scanInterval);
                } catch (InterruptedException ex) {
                    logger.info("{}超时扫描线程被中断", info.name);
                    break;
                } catch (ThreadDeath ex) {
                    logger.error(info.name + "超时扫描线程被终止", ex);
                    break;
                } catch (Exception ex) {
                    logger.error(info.name + "超时扫描线程捕获一个未处理异常", ex);
                }
            }
        }
    }

    /**
     * 超时信息
     */
    class TimeoutInfo {

        public TimeoutInfo(String fid, String mid, int timeout) {
            this(fid, mid, System.currentTimeMillis(), timeout);
        }

        public TimeoutInfo(String fid, String mid, long requestTime, int timeout) {
            this.fid = fid;
            this.mid = mid;
            this.requestTime = requestTime;
            this.timeout = timeout;
        }

        String fid;
        String mid;
        long requestTime;//请求时间
        int timeout; //超时时间，单位秒
    }
}
