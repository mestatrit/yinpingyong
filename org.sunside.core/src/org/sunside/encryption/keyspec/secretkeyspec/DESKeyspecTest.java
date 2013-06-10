package org.sunside.encryption.keyspec.secretkeyspec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * DESKeyspec指定了DES算法
 * 
 * @author:Ryan
 * @date:2013-6-5
 */
public class DESKeyspecTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {

		// 构建秘密密钥生成器
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

		// 获取秘密密钥
		SecretKey secretKey = keyGenerator.generateKey();

		// 获取秘密密钥对应的字节数组
		byte[] key = secretKey.getEncoded();

		/**
		 * 1、 基于SecretKeySpec秘密密钥规范，还原秘密密钥
		 */
		//SecretKey secretKey1 = new SecretKeySpec(key, "DES");
		
		/**
		 * 2、基于DESKeySpec秘密密钥规范，还原秘密密钥
		 */
		DESKeySpec dsk = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey1 = keyFactory.generateSecret(dsk);
		
		System.out.println("加密算法：" + new String(secretKey.getAlgorithm()));
		System.out.println("加密规范：" + new String(secretKey.getFormat()));
		System.out.println("秘密密钥1：" + new String(Base64.encode(key)));

		System.out.println("加密算法：" + new String(secretKey1.getAlgorithm()));
		System.out.println("加密规范：" + new String(secretKey1.getFormat()));
		System.out.println("秘密密钥2：" + new String(Base64.encode(secretKey1.getEncoded())));
	}

}
