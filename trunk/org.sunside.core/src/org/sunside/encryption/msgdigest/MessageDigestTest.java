package org.sunside.encryption.msgdigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * JDK6支持的6中消息认证加密算法：
 * SHA(SHA-1)、SHA-256、SHA-384、SHA-512、MD2、MD5
 * 
 * SHA（安全安全散列算法），生成消息摘要
 * 
 * 属于消息认证技术引擎类
 * 
 * @author:Ryan
 * @date:2013-6-1
 */
public class MessageDigestTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		String msg = "hello world!";
		
		// SHA(SHA-1)、SHA-256、SHA-384、SHA-512、MD2、MD5
		MessageDigest sha = MessageDigest.getInstance("MD5");
		
		//更新摘要信息
		sha.update(msg.getBytes());
		
		//获取摘要信息
		byte[] outPut = sha.digest();
		
		//得到的是个二进制byte数组，有可能某些byte是不可打印的字符。所以用Base64.encode把它转化成可打印字符
		System.out.println(new String(Base64.encode(outPut)));
	}

}
