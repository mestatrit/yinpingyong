package com.mt.common.net.TBCServer;

import com.mt.common.dynamicDataDef.FieldMap;
import com.mt.common.dynamicDataDef.FieldMapNode;
import com.mt.common.dynamicDataDef.FieldMapSet;
import com.mt.common.dynamicDataDef.FieldMapUtil;
import com.mt.common.net.TBCServer.internal.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TBCServer连接定义
 * Created by IntelliJ IDEA.
 * User: hanhui
 * Date: 2009-12-23
 * Time: 11:30:17
 * To change this template use File | Settings | File Templates.
 */
public class TBCServerConnection {

    static public final int DefaultTimeout = 60;
    static public final Properties GlobalConnectionSetting = new Properties();
    static private final Logger logger = LoggerFactory.getLogger(TBCServerConnection.class);

    /**
     * 初始化GlobalConnectionSetting
     */
    static {
        try {
            InputStream inputStream = TBCServerConnection.class.getResourceAsStream("/TBCServerConnectionSetting.properties");
            GlobalConnectionSetting.load(inputStream);
            logger.info("读取TBCServerConnectionSetting.properties");
        } catch (Exception ex) {
            logger.warn("读取TBCServerConnectionSetting.properties失败", ex);
        }
    }

    static private TBCServerConnection defaultConnection;

    static public TBCServerConnection createTBCServerConnection(TBCServerConnectionInfo info) throws IOException,
            TBCServerConnectionException {

        CommunicationsMode mode = null;
        if (info.communicationsMode == NetConstants.SocketMode) {
            mode = new SocketCommunicationsMode();
        } else if (info.communicationsMode == NetConstants.XSocketMode) {
            mode = new XSocketCommunicationsMode();
        } else if (info.communicationsMode == NetConstants.HttpMode) {
            mode = new HttpCommunicationsMode();
        }

        if (mode == null) {
            throw new TBCServerConnectionException("invalid CommunicationsMode");
        }
        return new TBCServerConnection(mode, info);
    }

    static public TBCServerConnection createDefaultTBCServerConnection(TBCServerConnectionInfo info) throws IOException,
            TBCServerConnectionException {
        defaultConnection = createTBCServerConnection(info);
        return defaultConnection;
    }

    static public TBCServerConnection getDefaultTBCServerConnection() {
        return defaultConnection;
    }

    /**
     * 用于本地保存数据
     */
    private HashMap<String, CommonMsg> localData = new HashMap<String, CommonMsg>();
    /**
     * 记录了与服务器连接时的相关信息
     */
    private TBCServerConnectionInfo info;
    /**
     * CommunicationsMode提供了与服务器通信的接口
     */
    private CommunicationsMode cMode;
    /**
     * 消息队列
     */
    private MsgQueue queue;
    /**
     * 消息分派器
     */
    private MsgDispatcher msgDispatcher;
    /**
     * 消息监听器
     */
    private MsgListener msgListener;
    /**
     * 网络异常处理器
     */
    private NetExceptionHandler netHandler;
    /**
     * 0881处理器
     */
    private S0881Handler s0881Handler;
    /**
     * 记录TBCServerConnectionListener的容器
     */
    private List<TBCServerConnectionListener> cListeners;
    /**
     * 记录TBCServerConnectionLogoutListener的容器
     */
    private List<TBCServerConnectionLogoutListener> logoutListeners;
    /**
     * 保存OIDErrorListener
     */
    private List<OIDErrorListener> oidErrorListeners;
    /**
     * oid监听器映射表
     */
    private Map<String, List<OIDListener>> oidListenerMap;
    /**
     * oid值映射表
     */
    private Map<String, String> oidValueMap;
    /**
     * 表示MTServerConnection当前的状态
     */
    private int status;
    /**
     * 发送消息是否log
     */
    private boolean isSendMsgLog = true;
    /**
     * 定时器
     */
    private ScheduledFuture timer0212;
    /**
     * 是否收到0212
     */
    private boolean isReceive0212 = false;
    /**
     * 和服务器时间的差值
     */
    private long timeDifference = 0;
    /**
     * 服务器时间的格式化器
     */
    private DateFormat serverTimerFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 记录登录后服务器返回的信息
     */
    private String returnMsg = "";
    // windowID,userNumber, sessionKey是与服务器传递信息时的必要字段
    private String windowID = "";
    private String userNumber = "";
    private String cusNumber = "";
    private String sessionKey = "";

    /**
     * 权限字符串
     */
    private String priv = "";

    /**
     * 用于关闭和重连同步的锁
     */
    private Lock closeLock = new ReentrantLock();

    protected TBCServerConnection(CommunicationsMode cMode, TBCServerConnectionInfo info) throws IOException, TBCServerConnectionException {
        //设置变量
        this.cMode = cMode;
        this.info = info;
        cListeners = Collections.synchronizedList(new ArrayList<TBCServerConnectionListener>());
        logoutListeners = Collections.synchronizedList(new ArrayList<TBCServerConnectionLogoutListener>());
        oidErrorListeners = Collections.synchronizedList(new ArrayList<OIDErrorListener>());
        oidListenerMap = new ConcurrentHashMap<String, List<OIDListener>>();
        oidValueMap = new ConcurrentHashMap<String, String>();
        status = NetConstants.Undefine;
        netHandler = new NetExceptionHandler();

        //读取配置
        try {
            String rs = GlobalConnectionSetting.getProperty("SendMsgLog");
            isSendMsgLog = rs.equals("true") ? true : false;
        } catch (Exception ex) {
            logger.warn("读取配置失败", ex);
        }

        //开始连接登录
        connect(false);
        run();
        initTimer0212();
        initLogoutListener();
        initOIDListener();
        statusChange(NetConstants.Connected, false);

    }

    /**
     * 获得连接名字
     *
     * @return
     */
    public String getName() {
        return this.info.name;
    }

    /**
     * 获得用户名
     *
     * @return
     */
    public String getUserName() {
        return this.info.userName;
    }

    /**
     * 获得用户的号码
     *
     * @return
     */
    public String getUserNumber() {
        return this.userNumber;
    }

    /**
     * 获得机构号码
     *
     * @return
     */
    public String getCusNumber() {
        return this.cusNumber;
    }

    /**
     * 获得权限字符串
     *
     * @return
     */
    public String getPriv() {
        return this.priv;
    }

    /**
     * 获得连接信息
     *
     * @return
     */
    public TBCServerConnectionInfo getConnectionInfo() {
        return this.info;
    }

    /**
     * 获得连接登录后服务器返回的信息
     *
     * @return
     */
    public String getReturnMsg() {
        return this.returnMsg;
    }

    /**
     * 获得服务器时间
     *
     * @return
     */
    public Date getServerTime() {
        if (isReceive0212) {
            return new Date(System.currentTimeMillis() + timeDifference);
        } else {
            return null;
        }
    }

    private void initTimer0212() {
        if (this.info.isTimer0212) {
            //同步请求0212
            proc0212Msg(syncRequestRemoteService("0212"));

            registerRemoteServiceListener("0212", new CommonMsgCallback() {

                @Override
                public void onMessage(CommonMsg msg) {
                    proc0212Msg(msg);
                }
            });

            //使用这种定时方式比Java定时器更可靠，它不会受到调整系统时间的影响
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            Runnable ex0212 = new Runnable() {

                @Override
                public void run() {
                    requestRemoteService("0212");
                }
            };
            timer0212 = ses.scheduleAtFixedRate(ex0212, this.info.intervalTime0212, this.info.intervalTime0212, TimeUnit.SECONDS);
        }
    }

    private void proc0212Msg(CommonMsg msg) {
        try {
            if (msg.isError()) {
                logger.error("0212消息有错误:" + msg.getErrorMsg());
            } else {
                String sTime = msg.getMsgString();
                Date serverDate = serverTimerFormat.parse(sTime);
                long localTime = System.currentTimeMillis();
                timeDifference = serverDate.getTime() - localTime;
                isReceive0212 = true;
//                Object[] infoV = {info.name, sTime, timeDifference};
//                logger.info("{}服务器时间信息:ServerTime:{} TimeDifference(ServerTime - LocalTime):{}", infoV);
            }
        } catch (Exception ex) {
            logger.error("解析服务器时间发生异常", ex);
        }
    }

    private void initLogoutListener() {
        registerRemoteServiceListener("0881", new CommonMsgCallback() {

            @Override
            public void onMessage(CommonMsg msg) {
                if (info.isServerLogout) {
                    if (s0881Handler == null) {
                        s0881Handler = new S0881Handler(msg);
                        s0881Handler.start();
                    } else {
                        logger.info("0881处理器已经被启动");
                    }
                } else {
                    logger.info("连接不支持服务器0881的强行关闭");
                }
            }
        });
    }

    private void initOIDListener() {
        CommonMsgCallback callback = new CommonMsgCallback() {

            public void onMessage(CommonMsg cMsg) {
                try {
                    if (cMsg.isError()) {
                        logger.error("返回的OID消息有错误:{}", cMsg.getErrorMsg());
                        notifyOIDErrorListener(cMsg);
                        return;
                    }
                    NodeList dataList = cMsg.getXmlDocument().getDocumentElement().getElementsByTagName("Data");
                    for (int i = 0; i < dataList.getLength(); i++) {
                        Element data = (Element) dataList.item(i);
                        String key = data.getAttribute("ID");
                        String value = data.getTextContent().trim();
                        oidValueMap.put(key, value);
                        notifyOIDListener(key, value);
                    }
                } catch (Exception ex) {
                    logger.error("解析OID消息发生异常", ex);
                    notifyOIDErrorListener(cMsg);
                }
            }
        };
        this.registerRemoteServiceListener("BD01", callback);
        this.registerRemoteServiceListener("BD04", callback);

        //如果连接发生断线,重连成功后对于被监听的OID重新向服务器请求注册一次
        this.addTBCServerConnectionListener(new TBCServerConnectionListener() {

            public void statusChange(int state) {
                if (state == NetConstants.Connected) {
                    Iterator<String> i = oidListenerMap.keySet().iterator();
                    String sendMsg = "";
                    int count = 0;
                    while (i.hasNext()) {
                        String key = i.next();
                        sendMsg += "|" + key;
                        count++;
                        //服务器承受的最大订阅是1024个OID
                        if (count == 1024) {
                            requestRemoteService(NetConstants.OID_Add, sendMsg.substring(1));
                            count = 0;
                            sendMsg = "";
                        }
                    }
                    if (sendMsg.length() > 0) {
                        requestRemoteService(NetConstants.OID_Add, sendMsg.substring(1));
                    }
                }
            }
        });
    }

    private void notifyOIDListener(String key, String value) {
        List<OIDListener> ls = oidListenerMap.get(key);
        if (ls == null) {
            return;
        }
        for (int i = 0; i < ls.size(); i++) {
            try {
                ls.get(i).onOIDMessage(key, value);
            } catch (Exception ex) {
                logger.error("onOIDMessage发生异常", ex);
            }
        }
    }

    private void notifyOIDErrorListener(final CommonMsg errorMsg) {
        Thread notifyThread = new Thread() {

            @Override
            public void run() {
                for (int i = 0; i < oidErrorListeners.size(); i++) {
                    try {
                        oidErrorListeners.get(i).onErrorOIDMsg(errorMsg);
                    } catch (Exception ex) {
                        logger.error("onOIDMessage发生异常", ex);
                    }
                }
            }
        };
        notifyThread.start();
    }

    private void connect(boolean isReconnect) throws IOException,
            TBCServerConnectionException {
        assert cMode != null;
        assert info != null;

        logger.info("开始连接服务器[{}:{}]...", info.IP, info.port);
        cMode.start(info);
        logger.info("连接服务器成功");

        logger.info("开始登录服务器...");
        if (isReconnect) {
            reLogin();
        } else {
            login();
        }
        logger.info("登录服务器成功");
    }

    /**
     * 重新连接服务器
     *
     * @return
     */
    private boolean reConnect() {
        try {
            connect(true);
            return true;
        } catch (Exception e) {
            logger.warn(info.name + "连接失败", e);
            return false;
        }
    }

    /**
     * 登录服务器，作为连接的最后一个阶段
     *
     * @throws TBCServerConnectionException,IOException
     *
     */
    private void login() throws TBCServerConnectionException, IOException {
        StringBuilder sendStr = new StringBuilder();
        String TID = "00000";
        this.windowID = WindowIDGen();
        if (info.loginMsgFormat == NetConstants.PLAIN) {
            //sendStr = sendStr.append("0001").append(windowID).append(fill(info.userName, " ", 8)).append(fill(info.password, " ", 8)).append(TID).append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<VersionInfo><CurVer>v20881218a</CurVer>");
            sendStr = sendStr.append("0001").append(windowID).append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<LoginInfo><UserID>").append(info.userName).append("</UserID>").append("<Password>").append(info.password).append("</Password>").append("<Cus_number>").append(info.cusNumber == null || info.cusNumber.equals("") ? "-2" : info.cusNumber).append("</Cus_number>")
                    .append("<ClientMac>").append(info.c_mac).append("</ClientMac><ClientIp>").append(info.c_ip).append("</ClientIp><ClientHost>").append(info.c_name).append("</ClientHost>")
                    .append("<TermID>").append(TID).append("</TermID>").append("<CurVer>").append(info.clientVersion.equals("") ? "v1_0_20771231a" : info.clientVersion).append("</CurVer>").append("<NextVer>").append(info.clientVersion.equals("") ? "v1_0_20771231a" : info.clientVersion).append("</NextVer>");
        } else if (info.loginMsgFormat == NetConstants.XML) {
            sendStr = sendStr.append("0200").append(windowID).append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<LoginInfo><UserID>").append(info.userName).append("</UserID>").append("<Password>").append(info.password).append("</Password>").append("<TermID>").append(TID).append("</TermID>").append("<CurVer>").append(info.clientVersion.equals("") ? "v1_0_20771231a" : info.clientVersion).append("</CurVer>").append("<NextVer>").append(info.clientVersion.equals("") ? "v1_0_20771231a" : info.clientVersion).append("</NextVer>");
        }

        sendMsg(sendStr.toString(), false);

        String msg = cMode.readMsg();
        logger.info("{}服务器对login的回应：{}", info.name, msg);

        if (msg == null || msg.equals("")) {
            throw new TBCServerConnectionException("null msg");
        }

        msg = msg.substring(5);
        String isPassed = msg.substring(8, 9);
        String errorCode = msg.substring(9, 13);
        String content = msg.substring(13);

        if (!(isPassed.equals("T") && errorCode.equals("0000"))) {
            throw new TBCServerConnectionException(msg);
        }

        this.returnMsg = content;

        if (info.loginMsgFormat == NetConstants.PLAIN) {
            processReturnMsg_PLAIN(content);
        } else {
            processReturnMsg_XML(content);
        }
    }

    private void reLogin() throws IOException, TBCServerConnectionException {
        if (info.loginMsgFormat == NetConstants.PLAIN) {
            sendMsg(NetConstants.PLAIN_RELOGIN + windowID + userNumber
                    + sessionKey);
        } else {
            StringBuilder para = new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("<LoginInfo><UserID>").append(info.userName).append("</UserID>").append("<Password>").append(info.password).append("</Password>").append("<TermID>00000</TermID></LoginInfo>");

            sendMsg(NetConstants.XML_RELOGIN + windowID + userNumber
                    + sessionKey + para);
        }

        String msg = cMode.readMsg();
        logger.info("服务器对重新login的回应：{}", msg);

        if (msg == null || msg.equals("")) {
            throw new TBCServerConnectionException("null msg");
        }

        msg = msg.substring(5);
        String isPassed = msg.substring(8, 9);
        String errorCode = msg.substring(9, 13);
        //String content = msg.substring(13);
        if (!(isPassed.equals("T") && errorCode.equals("0000"))) {
            throw new TBCServerConnectionException(msg);
        }
    }

    private void processReturnMsg_XML(String msg) {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(new InputSource(new StringReader(
                    msg)));
            this.userNumber = doc.getDocumentElement().getElementsByTagName(
                    "UserNumber").item(0).getTextContent().trim();
            this.sessionKey = doc.getDocumentElement().getElementsByTagName(
                    "Key").item(0).getTextContent().trim();

        } catch (Exception ex) {
            logger.error(info.name + "登录返回信息处理时发生异常", ex);
        }

    }

    private void processReturnMsg_PLAIN(String msg) {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(new InputSource(new StringReader(
                    msg)));

            NodeList lists = doc.getDocumentElement().getElementsByTagName(
                    "Data");
            for (int i = 0; i < lists.getLength(); i++) {
                if (lists.item(i) instanceof Element) {
                    Element temp = (Element) lists.item(i);
                    if (temp.getAttribute("ID").equals("UserNumber")) {
                        this.userNumber = temp.getTextContent().trim();
                    } else if (temp.getAttribute("ID").equals("CustNumber")) {
                        this.cusNumber = temp.getTextContent().trim();
                    } else if (temp.getAttribute("ID").equals("SessionKey")) {
                        this.sessionKey = temp.getTextContent().trim();
                    } else if (temp.getAttribute("ID").equals("Priv")) {
                        this.priv = temp.getTextContent().trim();
                    }
                }
            }

        } catch (Exception ex) {
            logger.error(info.name + "登录返回信息处理时发生异常", ex);
        }

    }

    private String fill(String str, String tofill, int length) {
        if (str.getBytes().length > length) {
            throw new IllegalArgumentException("字符长度不能超过" + length + "个字符");
        }

        String tmp = str;
        for (int i = str.getBytes().length; i < length; i++) {
            tmp += tofill;
        }
        return tmp;
    }

    private String WindowIDGen() {
        String GenID;
        GenID = Long.toString(System.currentTimeMillis());
        GenID = GenID.substring(GenID.length() - 4, GenID.length());
        return GenID;
    }

    /**
     * 发送消息
     *
     * @param msg
     * @return
     */
    private boolean sendMsg(String msg) {
        if (this.isSendMsgLog) {
            return sendMsg(msg, true);
        } else {
            return sendMsg(msg, false);
        }
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    private boolean sendMsg(String msg, boolean isLog) {
        byte[] bytes = null;
        try {
            bytes = msg.getBytes(info.msgEncoding);
        } catch (UnsupportedEncodingException ex) {
            logger.error("编码不支持", ex);
            return false;
        }

        String lenStr = Integer.toString(bytes.length + 9);
        int len = lenStr.length();
        if (len > 5) {
            lenStr = lenStr.substring(0, 5);
        }
        for (int i = 5; i - len > 0; i--) {
            lenStr = "0" + lenStr;
        }
        try {
            if (isLog && !msg.startsWith("0212")) {  // 0212 为对时的fid ,不再计入log
                logger.info("向{}服务器发送消息：{}", info.name, lenStr + msg);
            }
            cMode.sendMsg(lenStr + msg);
        } catch (Exception ex) {
            logger.error("发送消息出现异常", ex);
            return false;
        }
        return true;
    }

    private boolean sendMsg(String fid, String winId, String para) {
        return sendMsg(fid + winId + userNumber + sessionKey
                + para);
    }

    /**
     * 将CommonMsg序列化成XML
     *
     * @param msg
     * @return
     */
    public String createXMLMsg(CommonMsg msg) {
        Object fd = msg.getFieldData();
        int mode = msg.getMode();
        if (fd != null) {
            if (fd instanceof FieldMap) {
                return FieldMapUtil.createXMLString((FieldMap) fd, mode);
            } else if (fd instanceof FieldMapSet) {
                return FieldMapUtil.createXMLString((FieldMapSet) fd, mode);
            } else {
                return FieldMapUtil.createXMLString((FieldMapNode) fd);
            }
        } else {
            return "";
        }
    }

    /**
     * 启动消息监听和消息分派
     */
    private void run() {

        this.cMode.setReadTimeOut(Integer.MAX_VALUE);

        queue = new MsgQueue();
        msgDispatcher = new MsgDispatcher(this, queue);
        msgListener = new MsgListener(this, queue);

        this.msgListener.registerExceptionHandler(netHandler);
        this.msgListener.startListen(cMode);
        this.msgDispatcher.startDispatch();
    }

    /**
     * 重新启动消息监听
     */
    private void restartMsgListener() {

        this.cMode.setReadTimeOut(Integer.MAX_VALUE);

        this.msgListener.registerExceptionHandler(netHandler);
        this.msgListener.startListen(cMode);
    }

    /**
     * 改变MTServerConnection的状态
     *
     * @param status
     * @param notify
     */
    private void statusChange(int status, boolean notify) {
        this.status = status;
        if (notify) {
            notifyListener(null);
        }
    }

    /**
     * 通知listener,如果传入参数是null,那就通知全部listeners
     *
     * @param listener
     */
    private void notifyListener(TBCServerConnectionListener listener) {
        if (listener == null) {
            int count = this.cListeners.size();
            for (int i = 0; i < count; i++) {
                try {
                    cListeners.get(i).statusChange(this.status);
                } catch (Exception ex) {
                }
            }
        } else {
            try {
                listener.statusChange(status);
            } catch (Exception ex) {
            }
        }
    }

    private int msgid = 0;

    private String getMsgID() {
        if (msgid > 9999) {
            msgid = 0;
        }
        String idStr = Integer.toString(msgid++);
        for (int i = idStr.length(); i < 4; i++) {
            idStr += "0";
        }
        return idStr;
    }

    /**
     * 关闭连接
     * 关闭后这个连接不可再使用，如果要再建起连接需要创建新的连接
     */
    public void close() {
        this.netHandler.stopRun();
        try {
            closeLock.lock();
            if (status == NetConstants.Closed) {
                return;
            }
            statusChange(NetConstants.Closed, false);
            logger.info("开始关闭{}连接...", info.name);
            if (this.timer0212 != null) {
                timer0212.cancel(true);
            }
            sendLogoutRequest();
            Thread.sleep(1000*2);
            this.msgListener.stopListen();
            this.msgDispatcher.stopDispatch();
            this.cMode.close();
            logger.info("{}连接已被关闭...", info.name);
        } catch (InterruptedException ex) {
            logger.error("close所在线程被中断", ex);
        } catch (Exception ex) {
            logger.error("关闭连接过程中发生未预料的异常", ex);
        } finally {
            closeLock.unlock();
        }
    }

    /**
     * 发送登出请求
     */
    public void sendLogoutRequest() {
        sendMsg(NetConstants.LOGOUT + windowID + userNumber + sessionKey);
    }

    /**
     * 获得MTServerConnection的状态
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 添加一个TBCServerConnectionListener
     *
     * @param listener
     */
    public void addTBCServerConnectionListener(
            TBCServerConnectionListener listener) {
        this.cListeners.add(listener);
    }

    /**
     * 删除一个TBCServerConnectionListener
     *
     * @param listener
     */
    public void removeTBCServerConnectionListener(
            TBCServerConnectionListener listener) {
        this.cListeners.remove(listener);
    }

    /**
     * 添加一个TBCServerConnectionLogoutListener
     *
     * @param listener
     */
    public void addTBCServerConnectionLogoutListener(TBCServerConnectionLogoutListener listener) {
        this.logoutListeners.add(listener);
    }

    /**
     * 删除一个TBCServerConnectionLogoutListener
     *
     * @param listener
     */
    public void removeTBCServerConnectionListener(TBCServerConnectionLogoutListener listener) {
        this.logoutListeners.remove(listener);
    }

    /**
     * 增加一个OIDErrorListener
     *
     * @param listener
     */
    public void addOIDErrorListener(OIDErrorListener listener) {
        this.oidErrorListeners.add(listener);
    }

    /**
     * 移除一个OIDErrorListener
     *
     * @param listener
     */
    public void removeOIDErrorListener(OIDErrorListener listener) {
        this.oidErrorListeners.remove(listener);
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务,有默认超时时间
     *
     * @param fid
     * @param fm
     * @param mcb
     */
    public String requestRemoteService(String fid, FieldMap fm, CommonMsgCallback mcb) {
        return requestRemoteService(fid, fm, DefaultTimeout, mcb);
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务
     *
     * @param fid
     * @param fm
     * @param timeout
     * @param mcb
     */
    public String requestRemoteService(String fid, FieldMap fm, int timeout, CommonMsgCallback mcb) {
        String rs;
        if (fm == null) {
            rs = requestRemoteService(fid, (CommonMsg) null, timeout, mcb);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND);
            msg.setFieldMap(fm);
            rs = requestRemoteService(fid, msg, timeout, mcb);
        }
        return rs;
    }

    /**
     * 直接使用FieldMapSet向服务器请求某个服务,有默认超时时间
     *
     * @param fid
     * @param fms
     * @param mcb
     */
    public String requestRemoteService(String fid, FieldMapSet fms, CommonMsgCallback mcb) {
        return requestRemoteService(fid, fms, DefaultTimeout, mcb);
    }

    /**
     * 直接使用FieldMapSet向服务器请求某个服务
     *
     * @param fid
     * @param fms
     * @param timeout
     * @param mcb
     */
    public String requestRemoteService(String fid, FieldMapSet fms, int timeout, CommonMsgCallback mcb) {
        String rs;
        if (fms == null) {
            rs = requestRemoteService(fid, (CommonMsg) null, timeout, mcb);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND);
            msg.setFieldMapSet(fms);
            rs = requestRemoteService(fid, msg, timeout, mcb);
        }
        return rs;
    }

    /**
     * 直接使用FieldMapNode向服务器请求某个服务,有默认超时时间
     *
     * @param fid
     * @param node
     * @param mcb
     */
    public String requestRemoteService(String fid, FieldMapNode node, CommonMsgCallback mcb) {
        return requestRemoteService(fid, node, DefaultTimeout, mcb);
    }

    /**
     * 直接使用FieldMapNode向服务器请求某个服务
     *
     * @param fid
     * @param node
     * @param timeout
     * @param mcb
     */
    public String requestRemoteService(String fid, FieldMapNode node, int timeout, CommonMsgCallback mcb) {
        String rs;
        if (node == null) {
            rs = requestRemoteService(fid, (CommonMsg) null, timeout, mcb);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND);
            msg.setFieldMapNode(node);
            rs = requestRemoteService(fid, msg, timeout, mcb);
        }
        return rs;
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务，可以传mode,有默认超时时间
     *
     * @param fid
     * @param fm
     * @param mode
     * @param mcb
     */
    public String requestRemoteService_Mode(String fid, FieldMap fm, int mode, CommonMsgCallback mcb) {
        return requestRemoteService_Mode(fid, fm, mode, DefaultTimeout, mcb);
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务，可以传mode
     *
     * @param fid
     * @param fm
     * @param mode
     * @param timeout
     * @param mcb
     */
    public String requestRemoteService_Mode(String fid, FieldMap fm, int mode, int timeout, CommonMsgCallback mcb) {
        String rs;
        if (fm == null) {
            rs = requestRemoteService(fid, (CommonMsg) null, timeout, mcb);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND,mode);
            msg.setFieldMap(fm);
            rs = requestRemoteService(fid, msg, timeout, mcb);
        }
        return rs;
    }

    /**
     * 向服务器请求某个服务，不带请求参数,有默认超时时间
     *
     * @param fid
     * @param mcb
     */
    public String requestRemoteService(String fid, CommonMsgCallback mcb) {
        return requestRemoteService(fid, DefaultTimeout, mcb);
    }

    /**
     * 向服务器请求某个服务，不带请求参数
     *
     * @param fid
     * @param timeout
     * @param mcb
     */
    public String requestRemoteService(String fid, int timeout, CommonMsgCallback mcb) {
        return requestRemoteService(fid, (CommonMsg) null, timeout, mcb);
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务,没有处理者
     * 这表明也许这个请求服务器是不会有回应的，或者这个回应是以另一个FID回的
     * 或者请求者对回应不敢兴趣，不会去处理
     *
     * @param fid
     * @param fm
     */
    public String requestRemoteService(String fid, FieldMap fm) {
        return requestRemoteService(fid, fm, null);
    }

    /**
     * 直接使用FieldMapSet向服务器请求某个服务,没有处理者
     * 这表明也许这个请求服务器是不会有回应的，或者这个回应是以另一个FID回的
     * 或者请求者对回应不敢兴趣，不会去处理
     *
     * @param fid
     * @param fms
     */
    public String requestRemoteService(String fid, FieldMapSet fms) {
        return requestRemoteService(fid, fms, null);
    }

    /**
     * 直接使用FieldMapNode向服务器请求某个服务,没有处理者
     * 这表明也许这个请求服务器是不会有回应的，或者这个回应是以另一个FID回的
     * 或者请求者对回应不敢兴趣，不会去处理
     *
     * @param fid
     * @param node
     */
    public String requestRemoteService(String fid, FieldMapNode node) {
        return requestRemoteService(fid, node, null);
    }

    /**
     * 使用一个CommonMsg的消息结构向服务器请求某个服务,没有处理者
     * 这表明也许这个请求服务器是不会有回应的，或者这个回应是以另一个FID回的
     * 或者请求者对回应不敢兴趣，不会去处理
     *
     * @param fid
     * @param msg
     */
    public String requestRemoteService(String fid, CommonMsg msg) {
        return requestRemoteService(fid, msg, null);
    }

    /**
     * 向服务器请求某个服务，不带请求参数,也没有处理者
     * 这表明也许这个请求服务器是不会有回应的，或者这个回应是以另一个FID回的
     * 或者请求者对回应不敢兴趣，不会去处理
     *
     * @param fid
     */
    public String requestRemoteService(String fid) {
        return requestRemoteService(fid, (CommonMsg) null, null);
    }

    /**
     * 使用一个CommonRequest请求
     *
     * @param request
     */
    public String requestRemoteService(CommonRequest request) {
        String rs;
        Object msg = request.getRequestMsg();
        if (msg == null) {
            rs = requestRemoteService(request.getFid(), (CommonMsg) null, request.getTimeout(), request.getCallback());
        } else {
            if (msg instanceof FieldMap) {
                rs = requestRemoteService(request.getFid(), (FieldMap) msg, request.getTimeout(), request.getCallback());
            } else if (msg instanceof FieldMapSet) {
                rs = requestRemoteService(request.getFid(), (FieldMapSet) msg, request.getTimeout(), request.getCallback());
            } else if (msg instanceof FieldMapNode) {
                rs = requestRemoteService(request.getFid(), (FieldMapNode) msg, request.getTimeout(), request.getCallback());
            } else if (msg instanceof CommonMsg) {
                rs = requestRemoteService(request.getFid(), (CommonMsg) msg, request.getTimeout(), request.getCallback());
            } else {
                rs = requestRemoteService(request.getFid(), msg.toString(), request.getTimeout(), request.getCallback());
            }
        }
        return rs;
    }

    /**
     * 使用一个CommonMsg的消息结构向服务器请求某个服务,有默认超时时间
     *
     * @param fid
     * @param msg
     * @param mcb
     */
    public String requestRemoteService(String fid, CommonMsg msg, CommonMsgCallback mcb) {
        return requestRemoteService(fid, msg, DefaultTimeout, mcb);
    }

    /**
     * 使用一个CommonMsg的消息结构向服务器请求某个服务
     *
     * @param fid
     * @param msg
     * @param mcb
     * @param timeout 单位是秒
     */
    public String requestRemoteService(String fid, CommonMsg msg, int timeout, CommonMsgCallback mcb) {
        String msgId = getMsgID();
        if (mcb != null) {
            msgDispatcher.putRequestCallback(fid, msgId, timeout, mcb);
        }
        sendMsg(fid, msgId, msg == null ? "" : createXMLMsg(msg));
        return fid + msgId;
    }

    /**
     * 直接使用一个String作为消息向服务器请求某个服务
     *
     * @param fid
     * @param msg
     * @return
     */
    public String requestRemoteService(String fid, String msg) {
        return requestRemoteService(fid, msg, null);
    }

    /**
     * 直接使用一个String作为消息向服务器请求某个服务,有默认超时时间
     *
     * @param fid
     * @param msg
     * @param mcb
     */
    public String requestRemoteService(String fid, String msg, CommonMsgCallback mcb) {
        return requestRemoteService(fid, msg, DefaultTimeout, mcb);
    }

    /**
     * 直接使用一个String作为消息向服务器请求某个服务
     * 一般情况下不推荐使用这个方法,只有当你发送的数据无法用FieldMap或FieldMapSet以及FieldMapNode
     * 表示的时候才可能使用这个方法，比如多层次的结构另类的树形数据等
     *
     * @param fid
     * @param msg
     * @param mcb
     * @param timeout 单位是秒
     */
    public String requestRemoteService(String fid, String msg, int timeout, CommonMsgCallback mcb) {
        String msgId = getMsgID();
        if (mcb != null) {
            msgDispatcher.putRequestCallback(fid, msgId, timeout, mcb);
        }
        sendMsg(fid, msgId, msg);
        return fid + msgId;
    }

    /**
     * 向服务器请求某个服务，不带请求参数,有默认超时时间
     * 这个函数只有第一次的时候向Server请求，然后保存起来，当第二次调用的时候只会返回保存的
     * 而不再向服务器请求。
     *
     * @param fid
     * @param mcb
     */
    synchronized public void requestRemoteService_LocalSave(String fid, CommonMsgCallback mcb) {
        requestRemoteService_LocalSave(fid, DefaultTimeout, mcb);
    }

    /**
     * 向服务器请求某个服务，不带请求参数
     * 这个函数只有第一次的时候向Server请求，然后保存起来，当第二次调用的时候只会返回保存的
     * 而不再向服务器请求。
     *
     * @param fid
     * @param mcb
     * @param timeout
     */
    synchronized public void requestRemoteService_LocalSave(String fid, int timeout, CommonMsgCallback mcb) {
        CommonMsg msg = localData.get(fid);
        if (msg == null) {
            requestRemoteService(fid, timeout, new CommonMsgCallbackAdapter(mcb) {

                public void onMessage(CommonMsg msg) {
                    if (msg.isError()) {
                        getCommonMsgCallback().onMessage(msg);
                    } else {
                        localData.put(msg.getFID(), msg);
                        getCommonMsgCallback().onMessage(msg);
                    }
                }
            });
        } else {//如果已经存在直接返回
            mcb.onMessage(msg);
        }
    }

    /**
     * 取消请求
     *
     * @param requestId
     */
    public void cancelRequest(String requestId) {
        this.msgDispatcher.removeRequestCallback(requestId);
    }

    /**
     * 无参数的同步调用,有默认超时时间
     *
     * @param fid
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid) {
        return syncRequestRemoteService(fid, DefaultTimeout);
    }

    /**
     * 无参数的同步调用
     *
     * @param fid
     * @param timeout
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, int timeout) {
        return syncRequestRemoteService(fid, (CommonMsg) null, timeout);
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务,是个同步调用,有默认超时时间
     *
     * @param fid
     * @param fm
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, FieldMap fm) {
        return syncRequestRemoteService(fid, fm, DefaultTimeout);
    }

    /**
     * 直接使用FieldMap向服务器请求某个服务,是个同步调用
     *
     * @param fid
     * @param fm
     * @param timeout
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, FieldMap fm, int timeout) {
        if (fm == null) {
            return syncRequestRemoteService(fid, (CommonMsg) null, timeout);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND);
            msg.setFieldMap(fm);
            return syncRequestRemoteService(fid, msg, timeout);
        }

    }

    /**
     * 直接使用FieldMapSet向服务器请求某个服务,是个同步调用,有默认超时时间
     *
     * @param fid
     * @param fms
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, FieldMapSet fms) {
        return syncRequestRemoteService(fid, fms, DefaultTimeout);
    }

    /**
     * 直接使用FieldMapSet向服务器请求某个服务,是个同步调用
     *
     * @param fid
     * @param fms
     * @param timeout
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, FieldMapSet fms, int timeout) {
        if (fms == null) {
            return syncRequestRemoteService(fid, (CommonMsg) null, timeout);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND);
            msg.setFieldMapSet(fms);
            return syncRequestRemoteService(fid, msg, timeout);
        }
    }

    /**
     * 直接使用FieldMapNode向服务器请求某个服务,是个同步调用
     *
     * @param fid
     * @param node
     * @param timeout
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, FieldMapNode node, int timeout) {
        if (node == null) {
            return syncRequestRemoteService(fid, (CommonMsg) null, timeout);
        } else {
            CommonMsg msg = new CommonMsg(CommonMsg.SEND);
            msg.setFieldMapNode(node);
            return syncRequestRemoteService(fid, msg, timeout);
        }
    }

    /**
     * 向服务器请求的同步方法，参数是CommonMsg结构,有默认超时时间
     *
     * @param fid
     * @param msg
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, CommonMsg msg) {
        return syncRequestRemoteService(fid, msg, DefaultTimeout);
    }

    /**
     * 向服务器请求的同步方法，参数是CommonMsg结构
     *
     * @param fid
     * @param msg
     * @param timeout
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, CommonMsg msg, int timeout) {
        final Lock syncRequestLock = new ReentrantLock();
        try {
            final Condition condition = syncRequestLock.newCondition();
            final CommonMsg[] tempCommonMsg = new CommonMsg[1];
            syncRequestLock.lock();
            requestRemoteService(fid, msg, timeout, new CommonMsgCallback() {

                public void onMessage(CommonMsg msg) {
                    try {
                        syncRequestLock.lock();
                        tempCommonMsg[0] = msg;
                        condition.signalAll();
                    } finally {
                        syncRequestLock.unlock();
                    }
                }
            });

            condition.awaitUninterruptibly();
            if (tempCommonMsg[0] == null) {//tempCommonMsg[0]不应该等于null
                CommonMsg errorMsg = new CommonMsg(CommonMsg.Receive);
                errorMsg.setFID(fid);
                errorMsg.setErrorMsg("内部错误");
                return errorMsg;
            } else {
                return tempCommonMsg[0];
            }
        } finally {
            syncRequestLock.unlock();
        }
    }

    /**
     * 向服务器请求的同步方法,参数是字符串
     *
     * @param fid
     * @param msg
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, String msg) {
        return syncRequestRemoteService(fid, msg, DefaultTimeout);
    }

    /**
     * 向服务器请求的同步方法,参数是字符串,可以设定超时时间
     *
     * @param fid
     * @param msg
     * @param timeout
     * @return
     */
    public CommonMsg syncRequestRemoteService(String fid, String msg, int timeout) {
        final Lock syncRequestLock = new ReentrantLock();
        try {
            final Condition condition = syncRequestLock.newCondition();
            final CommonMsg[] tempCommonMsg = new CommonMsg[1];
            syncRequestLock.lock();
            requestRemoteService(fid, msg, timeout, new CommonMsgCallback() {

                public void onMessage(CommonMsg msg) {
                    try {
                        syncRequestLock.lock();
                        tempCommonMsg[0] = msg;
                        condition.signalAll();
                    } finally {
                        syncRequestLock.unlock();
                    }
                }
            });

            condition.awaitUninterruptibly();
            if (tempCommonMsg[0] == null) {//tempCommonMsg[0]不应该等于null
                CommonMsg errorMsg = new CommonMsg(CommonMsg.Receive);
                errorMsg.setFID(fid);
                errorMsg.setErrorMsg("内部错误");
                return errorMsg;
            } else {
                return tempCommonMsg[0];
            }
        } finally {
            syncRequestLock.unlock();
        }
    }

    /**
     * 注册远程服务监听器
     *
     * @param fid
     * @param cmb
     */
    public void registerRemoteServiceListener(String fid, CommonMsgCallback cmb) {
        msgDispatcher.resgisterCommonMsgCallback(fid, cmb);
    }

    /**
     * 删除远程服务监听器
     *
     * @param fid
     * @param cmb
     */
    public void removeRemoteServiceListener(String fid, CommonMsgCallback cmb) {
        msgDispatcher.removeCommonMsgCallback(fid, cmb);
    }

    /**
     * 查看某个FID是否被注册了Callback
     *
     * @param fid
     * @return
     */
    public boolean isResgisterCommonMsgCallback(String fid) {
        return msgDispatcher.isResgisterCommonMsgCallback(fid);
    }

    /**
     * 查看某个FID是否被注册了指定的Callback
     *
     * @param fid
     * @param callback
     * @return
     */
    public boolean isResgisterCommonMsgCallback(String fid, CommonMsgCallback callback) {
        return msgDispatcher.isResgisterCommonMsgCallback(fid, callback);
    }

    /**
     * 注册OID监听器
     *
     * @param key
     * @param listener
     */
    public void registerOIDListener(String key, OIDListener listener) {
        registerOIDListener(new String[]{key}, listener);
    }

    /**
     * 注册OID监听器
     *
     * @param keys
     * @param listener
     */
    public void registerOIDListener(String[] keys, OIDListener listener) {
        registerOIDListener(Arrays.asList(keys), listener);
    }

    /**
     * 注册OID监听器
     *
     * @param keys
     * @param listener
     */
    public void registerOIDListener(List<String> keys, OIDListener listener) {
        String sendMsg = "";
        for (int i = 0; i < keys.size(); i++) {
            boolean isSendMsg = false;
            List<OIDListener> listeners = oidListenerMap.get(keys.get(i));
            if (listeners == null) {
                isSendMsg = true;
                listeners = Collections.synchronizedList(new ArrayList<OIDListener>());
            }
            listeners.add(listener);
            oidListenerMap.put(keys.get(i), listeners);
            String value = oidValueMap.get(keys.get(i));
            if (value != null) {
                try {
                    listener.onOIDMessage(keys.get(i), value);
                } catch (Exception ex) {
                    logger.error("onOIDMessage发生异常", ex);
                }
            }
            if (isSendMsg) {
                if (sendMsg.equals("")) {
                    sendMsg = (String) keys.get(i);
                } else {
                    sendMsg = sendMsg + "|" + keys.get(i);
                }
            }
        }
        if (!sendMsg.equals("")) {
            this.requestRemoteService(NetConstants.OID_Add, sendMsg);
        }
    }

    /**
     * 移除OID监听器
     *
     * @param key
     * @param listener
     */
    public void removeOIDListener(String key, OIDListener listener) {
        removeOIDListener(new String[]{key}, listener);
    }

    /**
     * 移除OID监听器
     *
     * @param keys
     * @param listener
     */
    public void removeOIDListener(String[] keys, OIDListener listener) {
        removeOIDListener(Arrays.asList(keys), listener);
    }

    /**
     * 移除OID监听器
     *
     * @param keys
     * @param listener
     */
    public void removeOIDListener(List<String> keys, OIDListener listener) {
        String sendMsg = "";
        for (int i = 0; i < keys.size(); i++) {
            List<OIDListener> listeners = oidListenerMap.get(keys.get(i));
            if (listeners != null) {
                listeners.remove(listener);
                if (listeners.isEmpty()) {
                    oidListenerMap.remove(keys.get(i));
                    if (sendMsg.equals("")) {
                        sendMsg = keys.get(i);
                    } else {
                        sendMsg += "|" + keys.get(i);
                    }
                    oidValueMap.remove(keys.get(i));
                }
            }
        }
        if (!sendMsg.equals("")) {
            this.requestRemoteService(NetConstants.OID_Remove, sendMsg);
        }
    }

    /**
     * 0881处理器
     */
    class S0881Handler extends Thread {

        private CommonMsg s0881Msg;

        public S0881Handler(CommonMsg msg) {
            s0881Msg = msg;
        }

        @Override
        public void run() {
            close();
            String info = s0881Msg.getFieldMap().getField("Msg").getValue().toString().trim();
            for (int i = 0; i < logoutListeners.size(); i++) {
                try {
                    logoutListeners.get(i).onLogout(info);
                } catch (Exception ex) {
                    logger.error("onLogout处理函数发生异常", ex);
                }
            }
            logoutListeners = null;
        }
    }

    /**
     * 针对网络I/O等异常的处理器，处理器运行于一个独立的线程中
     */
    class NetExceptionHandler extends Thread {

        private boolean isRun = true;

        @Override
        public void run() {
            closeLock.lock();
            try {
                if (status == NetConstants.Closed) {
                    logger.warn("{}连接已被关闭，重连等动作请求无效", info.name);
                    return;
                }
                stopMsgCommunications();
                isRun = true;
                if (info.isReconnect) {
                    logger.info("开始尝试与{}服务器重新连接...", info.name);
                    statusChange(NetConstants.ReConnect, true);
                    boolean isCon = false;
                    while (isRun && !(isCon = reConnect())) {
                        logger.info("重新与{}服务器连接失败!", info.name);
                        try {
                            Thread.sleep(info.intervalTime * 1000);
                        } catch (InterruptedException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    if (isCon) {
                        logger.info("重新与{}服务器连接成功!", info.name);
                        statusChange(NetConstants.Connected, true);
                        netHandler = new NetExceptionHandler();
                        TBCServerConnection.this.restartMsgListener();
                    } else {
                        statusChange(NetConstants.DisConnected, true);
                    }

                } else {
                    statusChange(NetConstants.DisConnected, true);
                }
            } finally {
                closeLock.unlock();
            }
        }

        private void stopMsgCommunications() {
            try {
                msgListener.stopListen();
                cMode.close();
            } catch (InterruptedException ex) {
                logger.error("stopMsgCommunications所在线程被中断", ex);
            } catch (Exception ex) {
                logger.error("stopMsgCommunications所在线程发生异常", ex);
            }
        }

        public void stopRun() {
            isRun = false;
        }
    }
}
