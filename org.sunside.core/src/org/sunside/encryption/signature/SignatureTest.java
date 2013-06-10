package org.sunside.encryption.signature;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 数字签名例子
 * 私钥加签、公钥解签
 * 
 * 步骤：
 * 1、基于密钥对生成器，生成密钥对
 * 2、私钥，生成数字签名
 * 3、公钥，验签
 * 
 * @author:Ryan
 * @date:2013-6-2
 */
public class SignatureTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * @throws Base64DecodingException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, Base64DecodingException {
		
		//原始数据
		byte[] data = "hello world!".getBytes();
		
		//1、创建化密钥对生成器：DSA加密算法（非对称加密算法）
		KeyPairGenerator keypg = KeyPairGenerator.getInstance("DSA");
		
		//初始化密钥对生成器
		keypg.initialize(1024);
		
		//生成密钥对
		KeyPair keyPair = keypg.generateKeyPair();
		
		//2、创建数字签名(指定算法)对象
		Signature signature = Signature.getInstance(keypg.getAlgorithm());
		
		//使用私钥初始化签名，准备加签，获取数字签名
		signature.initSign(keyPair.getPrivate());
		
		//更新
		signature.update(data);
		
		//获取签名
		byte[] sign = signature.sign();
				
		//3、使用公钥，初始化解签，准备解签,获取验证结果
		signature.initVerify(keyPair.getPublic());
		
		//更新
		signature.update(data);
		
		//使用数字签名，获取验证结果
		boolean result = signature.verify(sign);
		
		System.out.println("加密算法：" + keypg.getAlgorithm());
		System.out.println("公钥:" + new String(Base64.encode(keyPair.getPublic().getEncoded())));
		System.out.println("私钥:" + new String(Base64.encode(keyPair.getPrivate().getEncoded())));
		System.out.println("数字签名:" + new String(Base64.encode(sign)));
		System.out.println("验签结果:" + result);
	}

}
