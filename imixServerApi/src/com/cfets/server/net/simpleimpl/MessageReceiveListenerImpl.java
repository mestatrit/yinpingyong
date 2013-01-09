package com.cfets.server.net.simpleimpl;

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

/**
 * 消息接收监听器
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class MessageReceiveListenerImpl implements MessageReceiveListener {

	private final static Logger logger = LoggerFactory.getLogger(MessageReceiveListenerImpl.class);

	@Override
	public void fromAdmin(Message message, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			RejectLogon {
		logger.info("fromAdmin() - " + sessionID + ": " + message);

	}

	@Override
	public void fromApp(Message message, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			UnsupportedMessageType, IOException {
		logger.info("fromApp() - " + sessionID + ": " + message);

	}

}
