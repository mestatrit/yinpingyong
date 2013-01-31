package com.cfets.server.net.simpleimpl;

import java.util.concurrent.ExecutorService;

import imix.SessionID;

import com.cfets.server.net.simpleimpl.internal.MsgBlockingQueue;

/**
 * IMIX Session信息
 * 登录验证通过后，会创建此对象，用于存储用户的回话信息 
 * 包括：
 * 1、SessionID
 * 2、请求队列
 * 3、响应队列
 * 4、请求处理线程
 * 5、响应处理线程
 * 
 * @author:Ryan
 * @date:2013-1-31
 */
public class ImixSessionInfo {

	/**
	 * 用户会话ID
	 */
	private SessionID sessionID;
	
	/**
	 * 请求队列
	 */
	private MsgBlockingQueue requestQueue;
	
	/**
	 * 响应队列
	 */
	private MsgBlockingQueue responseQueue;
	
	/**
	 * 请求处理线程
	 */
	private ExecutorService requestThread;
	
	/**
	 * 响应处理线程
	 */
	private ExecutorService responseThread;
	
	public ImixSessionInfo() {
		
	}
	
	/**
	 * 关闭用户会话
	 */
	public void stopImixSession() {
		// 关闭请求处理线程
		requestThread.shutdown();
		// 关闭响应处理线程
		responseThread.shutdown();
	}
	
	public SessionID getSessionID() {
		return sessionID;
	}

	public void setSessionID(SessionID sessionID) {
		this.sessionID = sessionID;
	}

	public MsgBlockingQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(MsgBlockingQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public MsgBlockingQueue getResponseQueue() {
		return responseQueue;
	}

	public void setResponseQueue(MsgBlockingQueue responseQueue) {
		this.responseQueue = responseQueue;
	}

	public ExecutorService getRequestThread() {
		return requestThread;
	}

	public void setRequestThread(ExecutorService requestThread) {
		this.requestThread = requestThread;
	}

	public ExecutorService getResponseThread() {
		return responseThread;
	}

	public void setResponseThread(ExecutorService responseThread) {
		this.responseThread = responseThread;
	}		
}
