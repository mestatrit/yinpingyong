package org.sunside.core.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author:Ryan
 * @date:2012-12-14
 */
public class HighPortScanner {

	public static void main(String[] args) {
		
		InetAddress address = null;
		try {
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e1) {
		}
		
		for (int port = 1024; port < 65535; port ++) {
			Socket socket = null;
			try {
				socket = new Socket(address, port);
				System.out.println("端口：" + port + "正提供服务...");
			} catch (IOException e) {
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
		
	}

}
