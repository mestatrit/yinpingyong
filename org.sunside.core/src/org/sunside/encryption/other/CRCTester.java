package org.sunside.encryption.other;

import java.util.zip.CRC32;

/**
 * 
 * 用于差错控制的CRC（cyclic redundancy check：循环冗余校验）算法
 * 
 * 是根据数据产生简短固定位数的一种散列函数，主要用于检测或校验数据阐述/保存后的错误
 * 
 * @author:Ryan
 * @date:2013-6-12
 */
public class CRCTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String data = "hello world!";
		
		CRC32 crc32 = new CRC32();
		
		crc32.update(data.getBytes());
		
		String hex = Long.toHexString(crc32.getValue());
		
		System.out.println("原文：" + data);
		
		System.out.println("编码后：" + hex);
		
	}

}
