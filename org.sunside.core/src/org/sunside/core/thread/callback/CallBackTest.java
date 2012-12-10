package org.sunside.core.thread.callback;

/**
 * @author:Ryan
 * @date:2012-12-11
 */
public class CallBackTest {

	public static void main(String[] args) {
		
		for (int count = 0; count< 10; count ++) {
			WorkerThread workerThread = new WorkerThread(new BusiInterface() {
				
				@Override
				public void printMsg(String msg) {
					System.out.println(msg);
				}
			});
			
			Thread t = new Thread(workerThread);
			t.start();
		}
		
	}

}
