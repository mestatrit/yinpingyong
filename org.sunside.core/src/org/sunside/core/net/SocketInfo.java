package org.sunside.core.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author:Ryan
 * @date:2012-12-14
 */
public class SocketInfo {

	public static void main(String[] args) throws IOException {
		
		InetAddress address = InetAddress.getByName("www.baidu.com");
		
		Socket socket = new Socket(address, 80);
		
		System.out.println(socket.getInetAddress().getHostName());
		System.out.println(socket.getPort());
		System.out.println(socket.getLocalAddress().getHostName());
		System.out.println(socket.getLocalPort());
	}

}
