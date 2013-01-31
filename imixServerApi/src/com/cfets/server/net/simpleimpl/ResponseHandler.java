package com.cfets.server.net.simpleimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 响应处理器
 * 
 * @author:Ryan
 * @date:2013-1-31
 */
public class ResponseHandler implements Runnable {

	static private final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
	
	/**
	 * 用户会话信息
	 */
	private ImixSessionInfo sessionInfo;
	
	public ResponseHandler() {
		
	}
	
	public ResponseHandler(ImixSessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
	
	public ImixSessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(ImixSessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	@Override
	public void run() {
		
		
		
	}

}
