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

import com.cfets.server.net.simpleimpl.internal.EventListenerImpl;
import com.cfets.server.net.simpleimpl.internal.MsgReceiveListenerImpl;
import com.cfets.server.net.simpleimpl.internal.MsgSendListenerImpl;
import com.cfets.server.net.simpleimpl.internal.UserListenerImpl;
import com.cfets.server.util.StringUtil;

/**
 * IMIX应用启动类
 * 1、实例化四个监听类
 * 2、加载IMIX上下文，启动IMIX服务
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class ImixServerImpl {

	private static final Logger logger = LoggerFactory.getLogger(ImixServerImpl.class);
	
	/**
	 * IMIX服务端配置文件路径
	 */
	private static final String path = "etc/server.cfg";
	
	/**
	 * 用户会话信息
	 */
	private Map<SessionID, ImixSessionInfo> user2session = new ConcurrentHashMap<SessionID, ImixSessionInfo>();
	
	/**
	 * IMIX应用上下文
	 */
	private IMIXApplication imixApplication;
	
	/**
	 * 事件（登录、登出、用户创建）监听器
	 */
	private EventListener eventListener;
	
	/**
	 * 用户登录验证监听器
	 */
	private UserListener userListener;
	
	/**
	 * 消息接收监听器
	 */
	private MessageReceiveListener messageReceiveListener;
	
	/**
	 * 消息发送监听器
	 */
	private MessageSendListener messageSendListener;
	
	public ImixServerImpl() {
		
		eventListener = new EventListenerImpl(user2session);
		
		userListener = new UserListenerImpl(user2session);
		
		messageReceiveListener = new MsgReceiveListenerImpl(user2session);
		
		messageSendListener = new MsgSendListenerImpl();
	}
	
	/**
	 * 启动IMIX服务
	 */
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
	
	/**
	 * 关闭IMIX服务
	 */
	public void stop() {
		try {
			imixApplication.stop();
			
			logger.info("imix server 停止.");	
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
