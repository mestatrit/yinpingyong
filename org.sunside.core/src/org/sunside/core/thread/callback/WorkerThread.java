package org.sunside.core.thread.callback;

/**
 * 工作线程
 * 
 * @author:Ryan
 * @date:2012-12-11
 */
public class WorkerThread implements Runnable {

	private BusiInterface busiInterface;
	
	public WorkerThread(BusiInterface busiInterface) {
		this.busiInterface = busiInterface;
	}
	
	@Override
	public void run() {
		busiInterface.printMsg(Thread.currentThread().toString());
	}

}
