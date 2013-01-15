package com.cfets.server.net.simpleimpl.internal;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞式消息队列
 * 
 * @author:Ryan
 * @date:2013-1-10
 */
public class MsgBlockingQueue {

	private BlockingQueue<MessageInfo> bq = new LinkedBlockingQueue<MessageInfo>();

	public void putMsgInfo(MessageInfo msg) throws InterruptedException {
		bq.put(msg);
	}

	public MessageInfo getMsgInfo() throws InterruptedException {
		return bq.take();
	}

	public int size() {
		return bq.size();
	}

}
