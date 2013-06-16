package org.sunside.encryption.certificate.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;

/**
 * 
 * 认证组建
 * 1、根据密钥库路径，获取密钥库信息
 * 2、根据数字证书路径，获取数字证书
 * 3、根据数字证书别名，从密钥库中获取数字证书
 * 
 * 4、从密钥库中，获取私钥
 * 5、从数字证书中，获取公钥
 * 
 * 6、公钥、私钥加密和解密
 * 7、签名和验签
 * 
 * @author:Ryan
 * @date:2013-6-16
 */
public abstract class CertificateCoder {

	public static final String CERT_TYPE = "x.509";
	
	/**
	 * 根据密钥库路径，获取密钥库：KeyStore
	 * @param keyStorePath 密钥库路径
	 * @param password 密钥库密码
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static KeyStore getKeyStore(String keyStorePath, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		
		// 获取密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		
		// 加载密钥库
		ks.load(is, password.toCharArray());
		
		is.close();
		
		return ks;
	}
	
	/**
	 * 根据别名，从密钥库中获取证书
	 * @param keyStorePath 密钥库路径
	 * @param alias 数字证书在密钥库中的别名
	 * @param password 密钥库密码
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static Certificate getCertificate(String keyStorePath, String alias, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		KeyStore ks = getKeyStore(keyStorePath, password);
		
		// 从密钥库中，根据别名，获取证书
		return ks.getCertificate(alias);
		
	}
	
	/**
	 * 根据证书路径，获取证书信息
	 * @param certificatePath 数字证书路径
	 * @return
	 * @throws Exception
	 */
	private static Certificate getCertificate(String certificatePath) throws Exception{
		
		// 实例化证书工厂
		CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
		
		// 获取证书文件流
		FileInputStream in = new FileInputStream(certificatePath);
		
		// 生成证书
		Certificate certificate = certificateFactory.generateCertificate(in);
		
		in.close();
		
		return certificate;
		
	}
	
	/**
	 * 从密钥库中，获取私钥
	 * @param keyStorePath 密钥库路径
	 * @param alias 数字证书在密钥库中的别名
	 * @param password 密钥库密码
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	private static PrivateKey getPrivateKeyByKeyStore(String keyStorePath, String alias, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		
		KeyStore ks = getKeyStore(keyStorePath, password);
		
		return (PrivateKey)ks.getKey(alias, password.toCharArray());
	}
	
	/**
	 * 从数字证书中，获取公钥
	 * @param certificatePath 数字证书路径
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKeyByCertificate(String certificatePath) throws Exception {
		
		Certificate certificate = getCertificate(certificatePath);
		
		return certificate.getPublicKey();
		
	}
	
	/**
	 * 私钥加密数据
	 * @param data 明文
	 * @param keyStorePath 密钥库路径
	 * @param alias 数字签名在密钥库中的别名
	 * @param password 密钥库的密码
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath,String alias, String password) throws Exception{
		
		// 从密钥库中，获取私钥
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, password);
		
		// 使用私钥，加密数据
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥解密
	 * @param data 密文
	 * @param keyStorePath 密钥库存储路径
	 * @param alias 别名
	 * @param password 密钥库密码
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decryptByPrivareKey(byte[] data, String keyStorePath, String alias, String password) throws Exception {
		
		// 从密钥库中，获取私钥
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, password);
		
		// 使用私钥，加密数据
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥加密
	 * @param data 明文
	 * @param certificatePath 数字证书路径
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String certificatePath) throws Exception {
		
		// 从数字证书中，获取公钥
		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		
		// 使用公钥，加密数据
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * @param data 密文
	 * @param certificatePath 数字证书路径
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String certificatePath) throws Exception {
		// 从数字证书中，获取公钥
		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		
		// 使用公钥，加密数据
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 签名
	 * @param data 明文
	 * @param keyStorePath 密钥库路径
	 * @param alias 别名
	 * @param password 密钥库密码
	 * @return 签名
	 */
	public static byte[] sign(byte[] data, String keyStorePath, String alias, String password) throws Exception{
		
		// 获取证书
		X509Certificate x509Certificate = (X509Certificate)getCertificate(keyStorePath, alias, password);
		
		// 构建签名
		Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
		
		// 获取私钥
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, password);
		
		// 初始化签名，由私钥构建
		signature.initSign(privateKey);
		
		signature.update(data);
		
		return signature.sign();
	}
	
	/**
	 * 验签
	 * @param data 消息
	 * @param sign 数字签名
	 * @param certificatePath 数字证书路径
	 * @return 验签结果
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] sign, String certificatePath) throws Exception {
	
		// 获取证书
		X509Certificate x509Certificate = (X509Certificate)getCertificate(certificatePath);
		
		// 由证书构建签名
		Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
		
		// 由证书初始化签名，实际上使用的是证书中的公钥
		signature.initVerify(x509Certificate);
		
		signature.update(data);
		
		return signature.verify(sign);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
