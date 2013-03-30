package org.sunside.core.thread.threadpool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 栅栏测试
 * 测试为容器添加边界
 * 
 * @author:Ryan
 * @date:2013-3-26
 */
public class SemaphoreTest<T> {

	private final Set<T> set;
	private final Semaphore sem;
	
	public SemaphoreTest(int bond) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		this.sem = new Semaphore(10);
	}
	
	public boolean add(T o) throws InterruptedException {
		// 先获取准入许可，否则一直阻塞
		sem.acquire();
		boolean wasAdd = true;
		try {
			wasAdd = set.add(o);
		} finally {
			// 加载失败的时候，也释放资源，提供在此添加的机会
			if (wasAdd == false) sem.release(); 
		}
		return wasAdd;
	}
	
	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if (wasRemoved) {
			// 删除成功，释放资源
			sem.release();
		}
		return wasRemoved;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
