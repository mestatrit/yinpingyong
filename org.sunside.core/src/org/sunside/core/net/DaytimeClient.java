package org.sunside.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author:Ryan
 * @date:2012-12-14
 */
public class DaytimeClient {

	public static void main(String[] args) throws IOException {
		
		InetAddress address = InetAddress.getByName("time.nist.gov");
		
		//获取服务器时间
		Socket socket = new Socket(address , 13);
		
		InputStream input = socket.getInputStream();
		
		StringBuffer sb = new StringBuffer();
		
		int c;
		
		while ( (c = input.read()) != -1) {
			sb.append((char)c);
		}
		
		System.out.println(sb.toString());
		
	}
	

}
