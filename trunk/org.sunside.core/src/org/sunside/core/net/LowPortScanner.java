package org.sunside.core.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author:Ryan
 * @date:2012-12-14
 */
public class LowPortScanner {

	public static void main(String[] args) {
		
		for (int port = 1; port < 1024; port ++) {
			try {
				new Socket("localhost", port);
				System.out.println("端口：" + port + "正提供服务...");
			} catch (UnknownHostException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
		
	}

}
