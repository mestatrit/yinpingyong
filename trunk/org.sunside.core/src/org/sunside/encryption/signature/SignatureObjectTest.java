package org.sunside.encryption.signature;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 基于签名对象的签名例子
 * 
 * @author:Ryan
 * @date:2013-6-2
 */
public class SignatureObjectTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {

		// 原始数据
		byte[] data = "hello world!".getBytes();

		// 1、创建化密钥对生成器：DSA加密算法（非对称加密算法）
		KeyPairGenerator keypg = KeyPairGenerator.getInstance("DSA");

		// 初始化密钥对生成器
		keypg.initialize(1024);

		// 生成密钥对
		KeyPair keyPair = keypg.generateKeyPair();

		// 2、创建数字签名(指定算法)对象
		Signature signature = Signature.getInstance(keypg.getAlgorithm());

		//基于序列化对象（原始数据），私钥、数字签名获取签名对象
		SignedObject s = new SignedObject(data, keyPair.getPrivate(), signature);
		
		// 获取签名
		byte[] sign = s.getSignature();

		//根据公钥和数字签名对象，解签
		boolean result = s.verify(keyPair.getPublic(), signature);

		System.out.println("公钥:"
				+ new String(Base64.encode(keyPair.getPublic().getEncoded())));
		System.out.println("私钥:"
				+ new String(Base64.encode(keyPair.getPrivate().getEncoded())));
		System.out.println("数字签名:" + new String(Base64.encode(sign)));
		System.out.println("验签结果:" + result);

	}

}
