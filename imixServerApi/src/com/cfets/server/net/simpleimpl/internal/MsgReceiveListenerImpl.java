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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.util.StringUtil;

/**
 * 消息接收监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class MsgReceiveListenerImpl implements MessageReceiveListener {

	private final static Logger logger = LoggerFactory.getLogger(MsgReceiveListenerImpl.class);

	private MsgBlockingQueue queue;
	
	public MsgReceiveListenerImpl(MsgBlockingQueue queue) {
		this.queue = queue;
	}
	
	@Override
	public void fromAdmin(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			RejectLogon {
		logger.info("fromAdmin() - " + sessionID + ": " + msg);
	}

	@Override
	public void fromApp(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			UnsupportedMessageType, IOException {
		
		logger.info("读取客户端:{}消息:{}", sessionID, msg);
		
		try {
			MessageInfo msgInfo = new MessageInfo(msg, sessionID);
			
			queue.putMsgInfo(msgInfo);
		
		} catch (InterruptedException e) {
			
			logger.error(e.getMessage());
			
			logger.error("网络消息接收队列被中断.异常信息={}", StringUtil.getStackTrace(e));
		}
	}

}
