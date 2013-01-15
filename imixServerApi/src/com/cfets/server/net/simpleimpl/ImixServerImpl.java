package com.cfets.server.net.simpleimpl;

import imix.SessionID;
import imix.core.api.EventListener;
import imix.core.api.IMIXApplication;
import imix.core.api.MessageReceiveListener;
import imix.core.api.MessageSendListener;
import imix.core.api.UserListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.net.simpleimpl.internal.EventListenerImpl;
import com.cfets.server.net.simpleimpl.internal.MsgBlockingQueue;
import com.cfets.server.net.simpleimpl.internal.MsgDispatcher;
import com.cfets.server.net.simpleimpl.internal.MsgReceiveListenerImpl;
import com.cfets.server.net.simpleimpl.internal.MsgSendListenerImpl;
import com.cfets.server.net.simpleimpl.internal.UserListenerImpl;
import com.cfets.server.util.StringUtil;

/**
 * ImixServerImpl
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class ImixServerImpl {

	private static final Logger logger = LoggerFactory.getLogger(ImixServerImpl.class);
	
	private static final String path = "etc/server.cfg";
	
	private Map<String, SessionID> user2session = new ConcurrentHashMap<String, SessionID>();
	
	private MsgBlockingQueue queue = new MsgBlockingQueue();
	
	private MsgDispatcher msgDispatcher;
	
	private IMIXApplication imixApplication;
	
	private EventListener eventListener;
	
	private UserListener userListener;
	
	private MessageReceiveListener messageReceiveListener;
	
	private MessageSendListener messageSendListener;
	
	public ImixServerImpl() {
		
		eventListener = new EventListenerImpl(user2session);
		
		userListener = new UserListenerImpl(user2session);
		
		messageReceiveListener = new MsgReceiveListenerImpl(queue);
		
		messageSendListener = new MsgSendListenerImpl();
		
		msgDispatcher = new MsgDispatcher(queue);
	}
	
	private void startup() {
		
		imixApplication = new IMIXApplication(path);
		
		if (!imixApplication.init()) {
			throw new RuntimeException("Comstp server download 初始化失败！");
		}
		
		imixApplication.setUsrListener(userListener);
		
		imixApplication.setMsgSendListener(messageSendListener);
		
		imixApplication.setMsgRcvListener(messageReceiveListener);
		
		imixApplication.setEventListener(eventListener);
		
		imixApplication.start();
		
		logger.info("imix server 启动完成.");
		
		msgDispatcher.startDispatch();
		
		logger.info("消息分派线程启动完成.");
	}
	
	public void stop() {
		try {
			msgDispatcher.stopDispatch();
			logger.info("消息分派线程停止.");
			
			imixApplication.stop();
			logger.info("imix server 停止.");	
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			logger.error("停止服务过程中出现中断异常,异常信息={}",StringUtil.getStackTrace(e));
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("停止服务过程中出现未预料的异常,异常信息={}",StringUtil.getStackTrace(e));
		}
	}
	
	public static void main(String[] args) {
		
		ImixServerImpl imixServerImpl = new ImixServerImpl();
		
		imixServerImpl.startup();
	}

}
