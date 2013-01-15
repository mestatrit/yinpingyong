package com.cfets.server.net.simpleimpl.internal;

import imix.FieldNotFound;
import imix.Message;
import imix.SessionID;
import imix.engine.core.Session;
import imix.field.FreeMsgID;
import imix.field.FreeMsgType;
import imix.field.Text;
import imix.imix10.FreeFormatMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfets.server.util.StringUtil;

/**
 * 消息分派器
 * 
 * @author:Ryan
 * @date:2013-1-10
 */
public class MsgDispatcher implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MsgDispatcher.class);

	private ExecutorService threadPool;

	private MsgBlockingQueue queue;

	public MsgDispatcher(MsgBlockingQueue queue) {
		this.queue = queue;
	}

	public void startDispatch() {
		threadPool = Executors.newSingleThreadExecutor();
		threadPool.submit(this);
	}

	public void stopDispatch() throws InterruptedException {
		threadPool.shutdown();
	}

	@Override
	public void run() {
		while (threadPool.isShutdown() == false) {
			try {
				MessageInfo msgInfo = queue.getMsgInfo();
				Message message = msgInfo.getMsg();
				SessionID sID = msgInfo.getSessionID();
				
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
					
					Session session = Session.lookupSession(sID);
					boolean result = session.send(retMsg);
					if (result) {
						logger.info("向客户端:{}发送消息:{}成功.", sID, message.toString());	
					} else {
						logger.info("向客户端:{}发送消息:{}失败.", sID, message.toString());
					}
						
				}
				
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				logger.error("读取客户端消息异常,异常信息={}",StringUtil.getStackTrace(e));
				logger.error("消息分派线程被中断");
				break;
			} 
		}
	}

}
