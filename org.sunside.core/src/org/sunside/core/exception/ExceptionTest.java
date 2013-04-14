package org.sunside.core.exception;

import java.io.IOException;

/**
 * @author:Ryan
 * @date:2013-4-14
 */
public class ExceptionTest {

	/**
	 * throws可单独使用
	 */
	public void m1() throws IOException{
		
	}
	
	/**
	 * throw可单独使用:仅限制抛出非受检异常
	 */
	public void m2() {
		throw new RuntimeException();
	}
	
	/**
	 * 受检异常抛出，throw、throws都需要；
	 * 且throws的范围必须涵盖throw
	 */
	public void m3() throws IOException {
		throw new IOException();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
