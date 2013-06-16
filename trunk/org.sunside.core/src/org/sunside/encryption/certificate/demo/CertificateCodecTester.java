package org.sunside.encryption.certificate.demo;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 数字证书包含：
 * （1）加密、解密算法，例如RSA（保证数字的机密性）
 * （2）数字签名算法：例如SHA1withRSA（保证数据的完整性和抗否认性）
 * （3）对数字证书的签名算法：SHA1（保证数字证书的完整性）
 * 
 * @author:Ryan
 * @date:2013-6-16
 */
public class CertificateCodecTester {

	// 密钥库密码
	private static final String password = "123456";
	
	// 数字证书别名
	private static final String alias = "www.zlex.org";
	
	// 数字证书路径
	private static final String certificatePath = "src/org/sunside/encryption/certificate/demo/zlex.cer";
	
	// 密钥库的存放路径
	private static final String keyStorePath = "src/org/sunside/encryption/certificate/demo/zlex.keystore";
	
	/**
	 * 公钥加密、私钥解密-客户端上传文件
	 * 
	 * （1）数字证书颁发给客户，客户基于数字证书中的公钥加密数据
	 * （2）服务端根据，数字证书别名，从密钥库中获取私钥，在根据私钥解密数据
	 * 
	 * @throws Exception
	 */
	private void test1() throws Exception {
		
		String data = "hello world!";
		
		// 公钥加密
		byte[] encrypt = CertificateCoder.encryptByPublicKey(data.getBytes(), certificatePath);
			
		// 私钥解密
		byte[] decrypt = CertificateCoder.decryptByPrivareKey(encrypt, keyStorePath, alias, password);
		
		System.out.println("密文 ： " + new String(Base64.encode(encrypt)));
		
		System.out.println("明文：" + new String(decrypt));
	}
	
	/**
	 * 私钥加密、公钥解密-客户端下载文件
	 * 
	 * （1）服务端使用私钥加密数据
	 * （2）客户端在使用公钥解密数据
	 * 
	 * @throws Exception
	 */
	private void test2() throws Exception {
		
		String data = "hello world!";
		
		// 私钥加密
		byte[] encrypt = CertificateCoder.encryptByPrivateKey(data.getBytes(), keyStorePath, alias, password);
		
		// 公钥解密
		byte[] decrypt = CertificateCoder.decryptByPublicKey(encrypt, certificatePath);
		
		System.out.println("密文 ： " + new String(Base64.encode(encrypt)));
		
		System.out.println("明文：" + new String(decrypt));
	}
	
	/**
	 * 签名验证
	 * 私钥加签、公钥解签
	 * @throws Exception
	 */
	private void testSign() throws Exception {
		
		String data = "hello world!";
		
		byte[] sign = CertificateCoder.sign(data.getBytes(), keyStorePath, alias, password);
		
		boolean status = CertificateCoder.verify(data.getBytes(), sign, certificatePath);
		
		System.out.println(status);
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		CertificateCodecTester test = new CertificateCodecTester();
		
		test.testSign();
	}

}
