package com.cfets.server.net.simpleimpl;

import imix.FieldNotFound;
import imix.Message;
import imix.field.FreeMsgID;
import imix.field.FreeMsgType;
import imix.field.Text;
import imix.imix10.FreeFormatMessage;

import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.net.simpleimpl.internal.ImixMessage;
import com.cfets.server.net.simpleimpl.internal.MsgBlockingQueue;
import com.cfets.server.util.StringUtil;

/**
 * 用户请求处理器
 * 
 * @author:Ryan
 * @date:2013-1-10
 */
public class RequestHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	
	/**
	 * 用户会话信息
	 */
	private ImixSessionInfo sessionInfo;
	
	public RequestHandler() {
		
	}
	
	public RequestHandler(ImixSessionInfo sessionInfo) {
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
		
		if (sessionInfo == null) {
			logger.error("用户会话信息为null，请求处理器终止启动！");
			return;
		}
		
		ExecutorService requestThread = sessionInfo.getRequestThread();
		if (requestThread == null) {
			logger.error("请求处理线程为null，请求处理器终止启动！");
			return;
		}
		
		MsgBlockingQueue requestQueue = sessionInfo.getRequestQueue();
		if (requestQueue == null) {
			logger.error("请求队列为null，请求处理器终止启动！");
			return;
		}
		
		logger.info("开始启动用户={}请求处理器.", sessionInfo.getSessionID());
		
		while (requestThread.isShutdown() == false) {
			
			try {
				
				// 1、阻塞式读取请求
				ImixMessage imixMessage = requestQueue.takeImixMessage();
				
				Message message = imixMessage.getMsg();
				
				// 2、根据请求消息类型，处理不同的请求
				if (message instanceof FreeFormatMessage) {
					
					FreeFormatMessage msg = (FreeFormatMessage)message;
					FreeMsgID msgIdField = new FreeMsgID();
					
					try {
						if (msg.isSetField(FreeMsgID.FIELD)) {
							msg.get(msgIdField);
						}
					} catch (FieldNotFound e) {
					}
					
					
					FreeMsgType fidField = new FreeMsgType();
					if (msg.isSetField(FreeMsgType.FIELD)) {
						try {
							msg.get(fidField);
						} catch (FieldNotFound e) {
						}
					}
					String msgId = msgIdField.getValue();
					String fid = fidField.getValue();
					
					FreeFormatMessage retMsg = new FreeFormatMessage(); 
					retMsg.set(new FreeMsgID(msgId));
					retMsg.set(new FreeMsgType(fid));
					retMsg.set(new Text("<====>"));
					
					// 3、将响应存入响应队列中
					MsgBlockingQueue responseQueue = sessionInfo.getResponseQueue();
					boolean ret = responseQueue.offerImixMessage(new ImixMessage(retMsg));
					if (ret == false) {
						logger.warn("用户={}响应队列已满，本次响应结果={}无法存入响应队列.", sessionInfo.getSessionID(), retMsg.toString());
					}
				}
				
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				logger.error("读取用户={}请求发生中断异常,异常信息={}", sessionInfo.getSessionID(), StringUtil.getStackTrace(e));
				logger.error("请求处理器将被中断！");
				break;
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("读取用户={}请求发生未知异常,异常信息={}", sessionInfo.getSessionID(), StringUtil.getStackTrace(e));
				logger.error("请求处理器将被中断！");
				break;
			}
		}
	}

}
