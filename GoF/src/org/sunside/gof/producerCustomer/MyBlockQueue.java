package org.sunside.gof.producerCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:Ryan
 * @date:2012-10-11
 */
public class MyBlockQueue {

	private Lock lock = new ReentrantLock();
	private Condition emptyCondition = lock.newCondition();
	private Condition fullCondition = lock.newCondition();

	private static final int length = 3;
	private List<Object> list = new ArrayList<Object>();

	private void put(Object obj) throws InterruptedException {
		try {
			lock.lock();
			if (list.size() == length) {
				System.out.println("队列已满...");
				fullCondition.await();
			} 
			
			list.add(obj);
			System.out.println("新增"+obj.toString()+"成功！");
			emptyCondition.signalAll();
			
		} finally {
			lock.unlock();
		}
	}

	private void get() throws InterruptedException {
		try {
			lock.lock();
			if (list.size() == 0) {
				System.out.println("队列已空...");
				emptyCondition.await();
			} 
			
			System.out.println("取出:" + list.remove(0).toString());
			fullCondition.signalAll();
			
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 使用多线程模拟生成者和消费者 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		final ExecutorService threadPool = Executors.newCachedThreadPool(); 
		final MyBlockQueue queue = new MyBlockQueue();
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				while(!threadPool.isShutdown()) {
					try {
						queue.get();
						Thread.currentThread().sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				while(!threadPool.isShutdown()) {
					try {
						queue.put("1");
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}

}
