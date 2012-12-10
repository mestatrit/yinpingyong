package org.sunside.core.thread.join;

/**
 * @author:Ryan
 * @date:2012-12-11
 */
public class JoinTest {

	public static void main(String[] args) throws InterruptedException {
		for (int count = 0; count <10;count ++) {
			System.out.println(count);
			Thread t = new Thread(new WorkThread());
			t.start();
			//join会强制等待此线程执行完毕,改变线程的原有优势...
			t.join();
		}
		System.out.println("main end...");
	}

}
