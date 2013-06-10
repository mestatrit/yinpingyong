package org.sunside.encryption.keyspec.secretkeyspec;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 构建秘密密钥规范
 * 
 * SecretKeySpec兼容所有对称加密算法，表框DES、AES、RC2等
 * 
 * @author:Ryan
 * @date:2013-6-4
 */
public class SecretKeyspecTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		// 构建秘密密钥生成器
		KeyGenerator keyGenerator = KeyGenerator.getInstance("RC2");
	
		//获取秘密密钥
		SecretKey secretKey = keyGenerator.generateKey();
		
		//获取秘密密钥对应的字节数组
		byte[] key = secretKey.getEncoded();
		
		//基于SecretKeySpec秘密密钥规范，还原秘密密钥
		SecretKey secretKey1 = new SecretKeySpec(key, "RC2");
		
		System.out.println("加密算法：" + new String(secretKey.getAlgorithm()));
		System.out.println("加密规范：" + new String(secretKey.getFormat()));
		System.out.println("秘密密钥1：" + new String(Base64.encode(key)));
		
		System.out.println("加密算法：" + new String(secretKey1.getAlgorithm()));
		System.out.println("加密规范：" + new String(secretKey1.getFormat()));
		System.out.println("秘密密钥2：" + new String(Base64.encode(secretKey1.getEncoded())));
	}

}
