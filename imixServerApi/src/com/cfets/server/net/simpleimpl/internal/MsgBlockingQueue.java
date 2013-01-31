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
	
	/**
	 * 链表结构的阻塞式队列
	 */
	private BlockingQueue<ImixMessage> bq = new LinkedBlockingQueue<ImixMessage>();

	/**
	 * 阻塞式保存消息
	 * 
	 * @param msg
	 * @throws InterruptedException
	 */
	public void putImixMessage(ImixMessage msg) throws InterruptedException {
		bq.put(msg);
	}
	
	/**
	 * 非阻塞式保存消息
	 * 
	 * @param msg
	 * @return 保存成功或是失败
	 */
	public boolean offerImixMessage(ImixMessage msg) {
		return bq.offer(msg);
	}
	
	/**
	 * 阻塞式读取消息
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public ImixMessage takeImixMessage() throws InterruptedException {
		return bq.take();
	}
	
	/**
	 * 非阻塞式读取消息
	 * 
	 * @return 如果队列为空，返回null
	 */
	public ImixMessage pollImixMessage() {
		return bq.poll();
	}
	
	/**
	 * 队列当前深度
	 * 
	 * @return
	 */
	public int size() {
		return bq.size();
	}

}
