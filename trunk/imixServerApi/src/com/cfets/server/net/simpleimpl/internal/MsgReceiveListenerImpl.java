package com.cfets.server.net.simpleimpl.internal;

import imix.FieldNotFound;
import imix.IncorrectDataFormat;
import imix.IncorrectTagValue;
import imix.Message;
import imix.SessionID;
import imix.UnsupportedMessageType;
import imix.core.api.MessageReceiveListener;
import imix.engine.core.RejectLogon;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.net.simpleimpl.ImixSessionInfo;
import com.cfets.server.util.StringUtil;

/**
 * 消息接收监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class MsgReceiveListenerImpl implements MessageReceiveListener {

	private final static Logger logger = LoggerFactory.getLogger(MsgReceiveListenerImpl.class);

	/**
	 * 用户回话信息
	 */
	private Map<SessionID, ImixSessionInfo> user2session;
	
	public Map<SessionID, ImixSessionInfo> getUser2session() {
		return user2session;
	}

	public void setUser2session(Map<SessionID, ImixSessionInfo> user2session) {
		this.user2session = user2session;
	}

	public MsgReceiveListenerImpl(Map<SessionID, ImixSessionInfo> user2session) {
		this.user2session = user2session;
	}
	
	@Override
	public void fromAdmin(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		logger.info("fromAdmin() - " + sessionID + ": " + msg);
	}

	@Override
	public void fromApp(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType, IOException {
		
		logger.info("读取用户={} 请求={}", sessionID, msg);
		
		ImixSessionInfo sessionInfo = user2session.get(sessionID);
		
		if (sessionInfo != null) {
			
			ImixMessage imixMessage = new ImixMessage(msg);
			
			try {
				
				// 阻塞式保存用户请求
				sessionInfo.getRequestQueue().putImixMessage(imixMessage);
				
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				logger.error("用户={}请求存入请求队列发生中断异常,异常信息={}", sessionID, StringUtil.getStackTrace(e));
			}
		} else {
			logger.warn("用户={}回话信息不存在，系统可能已经发生未知异常!", sessionID);
		}
	}

}
