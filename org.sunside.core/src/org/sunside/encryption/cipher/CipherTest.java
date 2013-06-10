package org.sunside.encryption.cipher;

import java.security.InvalidKeyException;
import java.security.Key;
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
 * 本例：对称加密实例
 * 
 * Cipher为加密、解密提供密码功能，完成JCE（Java Cryptographic Extension Java加密扩展体系）框架的核心
 * 
 * Cipher功能：
 * （1）提供对密钥的管理（密钥的封装），密钥的生成还是要依赖于密钥生成器
 * （2）提供加密和解密功能
 * 
 * @author:Ryan
 * @date:2013-6-4
 */
public class CipherTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws IllegalBlockSizeException 
	 * @throws BadPaddingException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		// 实例化KeyGenerator（密钥生成器对象）对象，并指定DES算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		
		// 生成秘密密钥
		SecretKey secretKey = keyGenerator.generateKey();
		
		// 创建Cipher对象
		Cipher cipher = Cipher.getInstance("DES");
		
		/**
		 * 1、秘密密钥的封装
		 */
		// 初始化cipher对象，用于封装密钥
		cipher.init(Cipher.WRAP_MODE, secretKey);
		
		// 包装秘密密钥、
		byte[] k = cipher.wrap(secretKey);
		
		/**
		 * 2、秘密密钥的解包
		 */
		// 初始化cipher对象，用于解包
		cipher.init(Cipher.UNWRAP_MODE, secretKey);
		
		// 解包秘密密钥
		Key key = cipher.unwrap(k, "DES", Cipher.SECRET_KEY);
		
		/**
		 * 3、加密数据
		 */
		// 初始化cipher对象，用于加密数据
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		//加密
		byte[] input = cipher.doFinal("hello world!".getBytes());
		
		/**
		 * 4、解密数据
		 */
		// 初始化cipher对象，用于解密数据
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		//解密
		byte[] outPut = cipher.doFinal(input);
		
		System.out.println("加密算法：" + secretKey.getAlgorithm());
		System.out.println("加密格式：" + secretKey.getFormat());
		System.out.println("秘密密钥：" + new String(Base64.encode(secretKey.getEncoded())));
		
		System.out.println("封装之后的密钥：" + new String(Base64.encode(k)));
		System.out.println("解包之后的密钥：" + new String(Base64.encode(key.getEncoded())));
		
		System.out.println("加密之后的数据：" + new String(Base64.encode(input)));
		System.out.println("解密之后的数据：" + new String(outPut));
	}

}
