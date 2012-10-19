package org.sunside.core.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 死锁
 * @author:Ryan
 * @date:2012-10-20
 */
public class DeadLock {

	private final static Object o1 = new Object();
	
	private final static Object o2 = new Object();
	
	private void method1() {
		
		synchronized (o1) {
			System.out.println(Thread.currentThread().getName()+"：进入方法1");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName() + "：准备进入方法2...");
			
			method2();
		}
	}
	
	private void method2() {
		
		synchronized (o2) {
			System.out.println(Thread.currentThread().getName()+"：进入方法2");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName() + "：准备进入方法1...");
			
			method1();
		}
	}
	
	public static void main(String[] args) {
		
		final DeadLock dLock = new DeadLock();
		
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				dLock.method1();
			}
		});
		
		threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				dLock.method2();
			}
		});
		
	}

}
