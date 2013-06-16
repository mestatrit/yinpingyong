package org.sunside.encryption.cipher.des;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * 
 * DESede安全编码组件
 * 
 * @author:Ryan
 * @date:2013-6-12
 */
public abstract class DESedeCoder {

	/**
	 * 密钥算法
	 * java6支持112和168位
	 * bouncy castle支持128和192位
	 */
	public static final String KEY_ALGORITHM = "DESede";
	
	/**
	 * 加密/解密算法 / 分组编码的工作模式 / 填充方式
	 */
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	
	/**
	 * 生成秘密密钥
	 * @return 秘密密钥的二进制内容
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException {
		
		/**
		 * 初始化密钥生成器
		 * 如果使用bouncy castle，使用：KeyGenerator.getInstance(KEY_ALGORITHM，"BC")
		 */
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		
		/**
		 * 初始化密钥长度
		 */
		kg.init(168);
		
		// 生成秘密密钥
		SecretKey secretKey = kg.generateKey();
		
		// 返回密钥的二进制编码形式内容
		return secretKey.getEncoded();
	}
	
	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return 密钥
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static Key toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		// 实例化3DES密钥材料
		DESedeKeySpec dks = new DESedeKeySpec(key);
		
		// 实例化密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		
		return keyFactory.generateSecret(dks);
	}
	
	/**
	 * 加密
	 * @param data 明文
	 * @param key 二进制的密钥
	 * @return 密文
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		//还原密钥
		Key k = toKey(key);
		
		//实例化Cipher对象，设置分组加密模式、填充方式等
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		
		//初始化
		cipher.init(Cipher.ENCRYPT_MODE, k);
		
		// 返回加密结果
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @param data 密文
	 * @param key 二进制密钥
	 * @return 明文
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		//还原密钥
		Key k = toKey(key);
		
		//实例化Cipher对象，设置分组加密模式、填充方式等
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		
		//初始化
		cipher.init(Cipher.DECRYPT_MODE, k);
		
		// 返回加密结果
		return cipher.doFinal(data);
		
	}
	
}
