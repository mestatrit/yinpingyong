package org.sunside.core.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于Condition实现的阻塞式队列;
 * @author:Ryan
 * @date:2012-10-20
 */
public class MyBlockingQueue {

	private final int MAX_SIZE = 10;
	
	private final List<String> vector = new ArrayList<String>();
	
	private Lock lock = new ReentrantLock();
	
	private Condition notEmptySignal = lock.newCondition();
	
	private Condition notFullSignal = lock.newCondition();
	
	public void put(String value) {
		try {
			lock.lock();
			
			if (vector.size() == MAX_SIZE) {
				notFullSignal.await();
			}
			
			vector.add(value);
			
			notEmptySignal.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			throw new RuntimeException("写入发生异常");
		} finally {
			lock.unlock();
		}
	}
	
	public String get() {
		try {
			lock.lock();
			
			if (vector.size() == 0) {
				notEmptySignal.await();
			}
			
			notFullSignal.signalAll();
			
			return vector.remove(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			throw new RuntimeException("读取发生异常");
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final MyBlockingQueue blockingQueue = new MyBlockingQueue();
		
		final ExecutorService threadPool = Executors.newCachedThreadPool();
		
		threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				
				while (!threadPool.isShutdown()) {
					Thread.sleep(5000);
					blockingQueue.put(new Date().toString());
				}
				
				return null;
			}
			
		});
		
		threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				while (!threadPool.isShutdown()) {
					Thread.sleep(1000);
					System.out.println(blockingQueue.get());
				}
				return null;
			}
			
		});
	}

}
