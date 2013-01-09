package com.cfets.server.net.simpleimpl;

import imix.Message;
import imix.SessionID;
import imix.core.api.MessageSendListener;
import imix.engine.core.DoNotSend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息发送监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class MessageSendListenerImpl implements MessageSendListener {

	private final static Logger logger = LoggerFactory.getLogger(MessageSendListenerImpl.class);

	@Override
	public void toAdmin(Message message, SessionID sessionID) {
		logger.info("toAdmin() - " + sessionID + ": " + message);
	}

	@Override
	public void toApp(Message message, SessionID sessionID) throws DoNotSend {
		logger.info("toApp() - " + sessionID + ": " + message);
	}

}
