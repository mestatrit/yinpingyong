package org.sunside.core.thread.lock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 可重入锁-提高了读取的效率（可用于集合的操作中）
 * 写入时，阻塞所有的线程
 * 读取时，可以并发
 * @author:Ryan
 * @date:2012-10-20
 */
public class ReadWriteLockDemo {
	
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	private Lock readLock = rwLock.readLock();
	
	private Lock writeLock = rwLock.writeLock();
	
	private List<String> list = new LinkedList<String>();
	
	private String get(int index) {
		try {
			readLock.lock();
			
			return list.get(index);
		} finally {
			readLock.unlock();
		}
	}
	
	private void put(String name) {
		try {
			writeLock.lock();
			
			Thread.sleep(5000);
			
			list.add(name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			writeLock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final ReadWriteLockDemo rwld = new ReadWriteLockDemo();
		
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				rwld.put("1");
				
				return null;
			}
			
		});
		
		threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println(rwld.get(0));
				
				return null;
			}
			
		});
	}

}
