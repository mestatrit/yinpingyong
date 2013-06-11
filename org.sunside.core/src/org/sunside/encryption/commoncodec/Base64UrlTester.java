package org.sunside.encryption.commoncodec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * 使用common code实现base64url编码
 * 
 * @author:Ryan
 * @date:2013-6-10
 */
public class Base64UrlTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String data = "hello world!";

		System.out.println("原文：" + data);

		/**
		 * 编码
		 */
		byte[] encode = Base64.encodeBase64URLSafe(data.getBytes());

		System.out.println("编码后：" + new String(encode));

		/**
		 * 解码
		 */
		byte[] decode = Base64.decodeBase64(encode);

		System.out.println("解码后：" + new String(decode));

	}

}
