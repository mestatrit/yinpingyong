package com.cfets.client.net.simpleimpl.internal;

import imix.FieldNotFound;
import imix.IncorrectDataFormat;
import imix.IncorrectTagValue;
import imix.Message;
import imix.UnsupportedMessageType;
import imix.client.core.ImixSession;
import imix.client.core.Listener;
import imix.client.core.MessageCracker;
import imix.imix10.FreeFormatMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DoNotSend;

/**
 * 消息监听器 
 * 
 * 功能：消息监听+消息分派
 * Listener：消息监听 
 * MessageCracker：消息分派
 * 
 * @author:Ryan
 * @date:2013-1-9
 */
public class ImixMsgListener extends MessageCracker implements Listener {

	private final static Logger logger = LoggerFactory.getLogger(ImixMsgListener.class);

	@Override
	public void onMessage(FreeFormatMessage message) throws FieldNotFound,
			UnsupportedMessageType, IncorrectTagValue {
		
	}

	@Override
	public void fromAdmin(Message message, ImixSession session)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue {
		logger.info("fromAdmin() - " + session + ": " + message);
	}

	@Override
	public void fromApp(Message message, ImixSession arg1)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			UnsupportedMessageType {
		
		// 消息分派
		messageCrack(message);
	}

	@Override
	public void onError(ImixSession session, int errorType) {
		logger.info("onError() - " + session + ": " + errorType);
	}

	@Override
	public void onLogon(ImixSession session) {
		logger.info("onLogon() - " + session);
	}

	@Override
	public void onLogout(ImixSession session) {
		logger.info("onLogout() - " + session);
	}

	@Override
	public void toApp(Message message, ImixSession session) throws DoNotSend {
		logger.info("toApp() - " + session + ": " + message);
	}

}
