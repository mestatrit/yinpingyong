package org.sunside.encryption.mac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.utils.Base64;


/**
 * 安全信息摘要测试类
 * 
 * 必须基于保密密钥，生成对应的消息摘要，所以他是安全信息摘要；
 * 
 * MAC：message authentication code（消息认证（鉴别）码）
 * 
 * @author:Ryan
 * @date:2013-6-3
 */
public class MacTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		
		byte[] input = "MAC".getBytes();
		
		// Jdk6支持的算法：HmacMD5、HmacSHA1、HmacSHA256、HmacSHA384、HmacSHA512
		//秘密密钥生成器，可以依赖于具体的算法；或是于算法无关；
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
		
		//生成保密密钥
		SecretKey secretKey = keyGenerator.generateKey();
		
		// 构建Mac对象
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		
		// 初始化Mac对象
		mac.init(secretKey);
		
		//获得安全信息摘要信息
		byte[] outPut = mac.doFinal(input);
		
		System.out.println("加密算法：" + secretKey.getAlgorithm());
		System.out.println("保密密钥:" + new String(Base64.encode(secretKey.getEncoded())));
		System.out.println("摘要信息:" + new String(Base64.encode(outPut)));
	}

}
