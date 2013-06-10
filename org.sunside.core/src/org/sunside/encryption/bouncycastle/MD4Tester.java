package org.sunside.encryption.bouncycastle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 基于BouncyCastle实现的MD4摘要加密算法
 * 
 * 基于配置方式，加载Bouncy Castle加密包，实现了调用者透明访问的目的
 * 
 * 配置方式：
 * （1）下载相关jar包
 * （2）%JAVA_HOME%/jre/lib/security/java.security中，
 *     配置：security.provider.10=org.bouncycastle.jce.provider.BouncyCastleProvider
 * （3）%JAVA_HOME%/jre/lib/ext中，
 *     放入：bcprov-ext-jdk15on-149.jar
 *
 * @author:Ryan
 * @date:2013-6-10
 */
public class MD4Tester {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws Base64DecodingException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, Base64DecodingException {
		
		MessageDigest md = MessageDigest.getInstance("MD4");
		
		md.update("hello world!".getBytes());
		
		System.out.println(new String(Base64.encode( md.digest())));
		
	}

}
