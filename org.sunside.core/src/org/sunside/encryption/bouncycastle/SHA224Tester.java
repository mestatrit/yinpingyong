package org.sunside.encryption.bouncycastle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 基于调用方式，实现bouncy castle的调用
 * 
 * 前提引入：bcprov-jdk15on-149.jar
 * 
 * 比较于配置式，缺点是：需要改动代码；优点：明确加密、解密的实现方
 * 
 * @author:Ryan
 * @date:2013-6-10
 */
public class SHA224Tester {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchProviderException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		
		/**
		 * 方式1：手动引入Bouncy Csatle的实现类
		 */
		/*Security.addProvider(new BouncyCastleProvider());
		
		MessageDigest md = MessageDigest.getInstance("SHA-224");*/
		
		/**
		 * 方式二：指定加密实现类
		 */
		MessageDigest md = MessageDigest.getInstance("SHA-224", "BC");
		
		md.update("hello world!".getBytes());
		
		System.out.println( new String(Base64.encode(md.digest())));
	}

}
