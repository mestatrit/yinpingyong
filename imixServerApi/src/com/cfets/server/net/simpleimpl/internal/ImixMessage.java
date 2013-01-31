package com.cfets.server.net.simpleimpl.internal;

/**
 * IMIX消息
 * 
 * @author:Ryan
 * @date:2013-1-10
 */
public class ImixMessage {

	/**
	 * IMIX消息对象
	 */
	private imix.Message msg;
	
	public ImixMessage () {
		
	}
	
	public ImixMessage(imix.Message msg) {
		this.msg = msg;
	}
	
	public imix.Message getMsg() {
		return this.msg;
	}
	
	public void setMsg(imix.Message msg) {
		this.msg = msg;
	}
}
