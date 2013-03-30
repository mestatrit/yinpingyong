package org.sunside.core.thread.threadpool;

import java.util.concurrent.CountDownLatch;

/**
 * 二元闭锁测试（包括二个状态，开启门和结束门）
 * 
 * @author:Ryan
 * @date:2013-3-26
 */
public class CountDownLatchTest {

	private void timeTasks(int nThreads, final Runnable command) throws InterruptedException {
		
		//开启门
		final CountDownLatch startGate = new CountDownLatch(1);
		//结束门
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		
		for (int i=0;i<nThreads;i++ ) {
			Thread t = new Thread() {

				@Override
				public void run() {
					try {
						// 等待所有线程初始化完毕
						startGate.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try{
						command.run();
					} finally {
						endGate.countDown();
					}
					
				}
				
			};
			
			t.start();
		}
		
		long start = System.nanoTime();
		startGate.countDown();
		// 等待所有线程执行结束
		endGate.await();
		long end = System.nanoTime();
		System.out.println(start - end);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
