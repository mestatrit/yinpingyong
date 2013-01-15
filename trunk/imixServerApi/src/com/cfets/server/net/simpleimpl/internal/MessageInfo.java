package com.cfets.server.net.simpleimpl.internal;

import imix.SessionID;

/**
 * @author:Ryan
 * @date:2013-1-10
 */
public class MessageInfo {

	private imix.Message msg;
	
	private SessionID sessionID;
	
	public MessageInfo(imix.Message msg, SessionID sessionID) {
		this.msg = msg;
		this.sessionID = sessionID;
	}
	
	public imix.Message getMsg() {
		return this.msg;
	}
	
	public SessionID getSessionID() {
		return this.sessionID;
	}
}
