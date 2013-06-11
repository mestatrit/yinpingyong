package org.sunside.encryption.commoncodec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author:Ryan
 * @date:2013-6-10
 */
public class DigestUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String data = "hello world!";
		
		System.out.println("原文：" + data);
		
		String md5Hex = DigestUtils.md2Hex(data);
		
		System.out.println("加密后密文：" + md5Hex);
	}

}
