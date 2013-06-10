package org.sunside.encryption.certificate;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;

/**
 * 加载证书
 * 获取撤销证书列表
 * 获取撤销证书列表实体
 * 获取证书链
 * 
 * @author:Ryan
 * @date:2013-6-5
 */
public class CertificateTest {

	public static void main(String[] args) throws CertificateException, IOException, CRLException {
		
		// 创建证书工厂，并指明证书类型是X.509
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
		//获取证书输入流
		FileInputStream in = new FileInputStream("d:\\x.keystore");
		
		/**
		 * 获得证书
		 */
		Certificate certificate = cf.generateCertificate(in);
		
		/**
		 * 获取撤销证书列表
		 * 验证证书时，首先查询此列表，然后在考虑验证证书的合法性
		 */
		CRL crl = cf.generateCRL(in);
		
		/**
		 * 获取撤销证书列表实体:X509CRLEntry
		 */
		X509Certificate x509certificate = (X509Certificate)cf.generateCertificate(in);
		X509CRL x509crl = (X509CRL)cf.generateCRL(in);
		X509CRLEntry x509CRLEntry = x509crl.getRevokedCertificate(x509certificate);
		
		/**
		 * 获取证书链
		 */
		CertPath certPath = cf.generateCertPath(in);
		
		in.close();
	}
	
}
