package org.sunside.encryption.cipher;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 本例：非对称加密实例
 * 
 * @author:Ryan
 * @date:2013-6-4
 */
public class CipherTest2 {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws IllegalBlockSizeException 
	 * @throws BadPaddingException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		//1、创建化密钥对生成器：DSA加密算法（非对称加密算法）
		KeyPairGenerator keypg = KeyPairGenerator.getInstance("RSA");
		
		//初始化密钥对生成器
		keypg.initialize(1024);
		
		//生成密钥对
		KeyPair keyPair = keypg.generateKeyPair();
		
		// 创建Cipher对象
		Cipher cipher = Cipher.getInstance("RSA");
		
		// 初始化cipher对象，用于加密数据
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
		
		//加密
		byte[] input = cipher.doFinal("hello world!".getBytes());
		
		// 初始化cipher对象，用于解密数据
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
		
		//解密
		byte[] outPut = cipher.doFinal(input);
		
		System.out.println("加密之后的数据：" + new String(Base64.encode(input)));
		System.out.println("解密之后的数据：" + new String(outPut));
	}

}
