package org.sunside.encryption.keyspec.encodedkeyspec;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * EncodedKeyspec用编码格式类表示公钥和私钥，我们称为编码密钥规范
 * 
 * X509EncodedKeyspec用于转换公钥编码密钥
 * PKCS8EncodedKeyspec用于私钥编码密钥
 * 
 * 默认：KeyPairGenerator密钥对生成器，就是采用上述规范，来编码公钥和私钥
 * 
 * @author:Ryan
 * @date:2013-6-4
 */
public class EncodedKeyspecTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		//创建KeyPairGenerator对象，并指定DAS算法
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
		
		//初始化KeyPairGenerator对象
		keygen.initialize(1024);
		
		//生成KeyPair对象
		KeyPair keys = keygen.generateKeyPair();
		
		/**
		 * 基于X.509标准，作为密钥规范的编码格式
		 */
		//获得公钥密钥字节数组
		byte[] publicKeyBytes = keys.getPublic().getEncoded();
				
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		
		/**
		 * 基于PKCS8标准，作为密钥规范的编码格式
		 */
		//获取私钥密钥字节数组
		byte[] privateKeyBytes = keys.getPrivate().getEncoded();
				
		PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(privateKeyBytes);
		
		KeyFactory keyFactory1 = KeyFactory.getInstance("DSA");
		
		PrivateKey privateKey = keyFactory1.generatePrivate(keySpec1);

		System.out.println("公钥加密算法：" + new String(keys.getPublic().getAlgorithm()));
		System.out.println("公钥加密编码：" + new String(keys.getPublic().getFormat()));
		System.out.println("私钥加密算法：" + new String(keys.getPrivate().getAlgorithm()));
		System.out.println("私钥加密编码：" + new String(keys.getPrivate().getFormat()));
		System.out.println("公钥：" + new String(Base64.encode(publicKeyBytes)));
		System.out.println("私钥：" + new String(Base64.encode(privateKeyBytes)));
		
		System.out.println("公钥加密算法：" + new String(publicKey.getAlgorithm()));
		System.out.println("公钥加密编码：" + new String(publicKey.getFormat()));
		System.out.println("私钥加密算法：" + new String(privateKey.getAlgorithm()));
		System.out.println("私钥加密编码：" + new String(privateKey.getFormat()));
		System.out.println("公钥：" + new String(Base64.encode(publicKey.getEncoded())));
		System.out.println("私钥：" + new String(Base64.encode(privateKey.getEncoded())));
		
	}

}
