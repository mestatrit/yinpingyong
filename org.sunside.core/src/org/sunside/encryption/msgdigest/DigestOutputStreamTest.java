package org.sunside.encryption.msgdigest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 消息摘要输出流：输出流到文件的时候，完成消息摘要的生成。
 * 
 * @author:Ryan
 * @date:2013-6-2
 */
public class DigestOutputStreamTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		byte[] msg = "hello world!".getBytes();
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		
		//创建消息摘要输入流对象
		DigestOutputStream dos = new DigestOutputStream(new FileOutputStream(new File("DigestOutputStreamTest.out")) ,md5);
		
		//流输出(默认更新摘要信息)
		dos.write(msg, 0 , msg.length);
		
		//获取摘要信息
		byte[] outPut = dos.getMessageDigest().digest();
		
		System.out.println(new String(Base64.encode(outPut)));
		
	}

}
