package org.sunside.core.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author:Ryan
 * @date:2013-4-14
 */
public class FutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		/**
		 * Callable带有返回值，Runnable是没有的
		 */
		Future<Integer> future = es.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				TimeUnit.MILLISECONDS.sleep(5000);
				return 123456;
			}
			
		});
		
		while (future.isDone() == false) {
			TimeUnit.MILLISECONDS.sleep(1000);
			System.out.print("#");
		}
		
		System.out.println("\n计算完成，返回结果:" + future.get());
		
		es.shutdown();
	}

}
