package org.sunside.encryption.other;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * @author:Ryan
 * @date:2013-6-13
 */
public class GeneratorKey {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {

		// 1、创建化密钥对生成器：DSA加密算法（非对称加密算法）
		KeyPairGenerator keypg = KeyPairGenerator.getInstance("RSA");

		// 初始化密钥对生成器
		keypg.initialize(2048);

		// 生成密钥对
		KeyPair keyPair = keypg.generateKeyPair();
		
		System.out.println(Base64.encode(keyPair.getPrivate().getEncoded())); 
		
		System.out.println(Base64.encode(keyPair.getPublic().getEncoded()));
	}

}
