package com.cfets.server.net.simpleimpl.internal;

import java.util.Map;

import imix.SessionID;
import imix.core.api.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.net.simpleimpl.ImixSessionInfo;

/**
 * 客户端登录、登出事件监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class EventListenerImpl implements EventListener {

	private final static Logger logger = LoggerFactory.getLogger(EventListenerImpl.class);
	
	private Map<SessionID, ImixSessionInfo> user2session;
	
	public EventListenerImpl(Map<SessionID, ImixSessionInfo> user2session) {
		this.user2session = user2session;
	}
	
	@Override
	public void onCreate(SessionID sessionID) {
		logger.info("onCreate()：创建客户端连接，sessionId={}.", sessionID);
	}

	@Override
	public void onLogon(SessionID sessionID) {
		logger.info("onLogon()：客户端登陆，sessionId={}.", sessionID);
	}

	@Override
	public void onLogout(SessionID sessionID) {
		logger.info("onLogout(): 客户端登出，sessionId={}.", sessionID);
		
		ImixSessionInfo sessionInfo = user2session.get(sessionID);
		
		if (sessionInfo != null) {
			
			user2session.remove(sessionID);
			
			logger.info("sessionID={},客户端已经移除！", sessionID.toString());
		} else {
			
			logger.info("sessionID={},客户端不存在！", sessionID.toString());
		}
	}

}
