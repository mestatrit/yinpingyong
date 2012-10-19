package org.sunside.core.thread.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尝试锁
 * @author:Ryan
 * @date:2012-10-20
 */
public class TryLock {

	private Lock lock1 = new ReentrantLock();
	
	private Lock lock2 = new ReentrantLock();
	
	private void method1() {
		try{
			lock1.tryLock();
			
			System.out.println(Thread.currentThread().getName() + ":进入方法1");
			
			Thread.sleep(1000);
			
			System.out.println(Thread.currentThread().getName() + ":准备进入方法2...");
			
			method2();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock1.unlock();
		}
	}
	
	private void method2() {
		try{
			lock2.tryLock();
			
			System.out.println(Thread.currentThread().getName() + ":进入方法2");
			
			Thread.sleep(1000);
			
			System.out.println(Thread.currentThread().getName() + ":准备进入方法1...");
			
			method1();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock2.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final TryLock tryLock = new TryLock();
		
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				tryLock.method1();
				
				return null;
			}
			
		});
		
		threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				tryLock.method2();
				
				return null;
			}
			
		});
	}

}
