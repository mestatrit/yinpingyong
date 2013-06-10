package org.sunside.encryption.msgdigest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 消息摘要输入流:读取文件流的方式，生成MD5值（消息摘要）
 *
 * 基于字节或是字节数组，完成摘要的操作，不是很方便；
 * 所以，有了摘要消息流（包含，消息摘要输入流、消息摘要输出流）
 * 
 * @author:Ryan
 * @date:2013-6-2
 */
public class DigestInputStreamTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		
		byte[] msg = "hello world!".getBytes();
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		
		//创建消息摘要输入流对象
		DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(msg) ,md5);
		
		//流输入(默认更新摘要信息)
		dis.read(msg, 0 , msg.length);
		
		//获取摘要信息
		byte[] outPut = dis.getMessageDigest().digest();
		
		System.out.println(new String(Base64.encode(outPut)));
	}

}
