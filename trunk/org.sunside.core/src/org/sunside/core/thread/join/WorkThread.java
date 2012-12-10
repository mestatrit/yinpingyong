package org.sunside.core.thread.join;

/**
 * @author:Ryan
 * @date:2012-12-11
 */
public class WorkThread implements Runnable {

	@Override
	public void run() {
		try {
			System.out.println("running....");
			
			Thread.sleep(5000);
			
			System.out.println("end!!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
