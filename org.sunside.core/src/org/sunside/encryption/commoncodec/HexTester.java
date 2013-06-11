package org.sunside.encryption.commoncodec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * 
 * Hex编码的实现
 * 
 * @author:Ryan
 * @date:2013-6-10
 */
public class HexTester {

	/**
	 * @param args
	 * @throws DecoderException 
	 */
	public static void main(String[] args) throws DecoderException {
		
		String data = "hello world!";
		
		System.out.println("原文：" + data);
		
		String encode = Hex.encodeHexString(data.getBytes());
		
		System.out.println("编码后：" + encode); 
		
		byte[] decode = Hex.decodeHex(encode.toCharArray());
		
		System.out.println("解码后：" + new String(decode)); 
	}

}
