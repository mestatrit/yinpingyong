package com.cfets.server.net.simpleimpl;

import imix.SessionID;
import imix.core.api.EventListener;
import imix.core.api.IMIXApplication;
import imix.core.api.MessageReceiveListener;
import imix.core.api.MessageSendListener;
import imix.core.api.UserListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private IMIXApplication imixApplication;
	
	private EventListener eventListener;
	
	private UserListener userListener;
	
	private MessageReceiveListener messageReceiveListener;
	
	private MessageSendListener messageSendListener;
	
	public ImixServerImpl() {
		
		eventListener = new EventListenerImpl(user2session);
		
		userListener = new UserListenerImpl(user2session);
		
		messageReceiveListener = new MessageReceiveListenerImpl();
		
		messageSendListener = new MessageSendListenerImpl();
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
	}
	
	public static void main(String[] args) {
		
		ImixServerImpl imixServerImpl = new ImixServerImpl();
		
		imixServerImpl.startup();
	}

}
