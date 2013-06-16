package org.sunside.encryption.cipher.des;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * @author:Ryan
 * @date:2013-6-12
 */
public class KeyTester {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		byte[] key = DESedeCoder.initKey();
		
		System.out.println(new String(Base64.encodeBase64(key)));
		
		byte[] key1 = DESedeCoder.initKey();
		
		System.out.println(new String(Base64.encodeBase64(key1)));
	}

}
