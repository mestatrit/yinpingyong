package org.sunside.encryption.cipher.des;

/**
 * @author:Ryan
 * @date:2013-6-12
 */
public class DESedeTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		String data = "hello world!";
		
		byte[] key = DESedeCoder.initKey();
		
		byte[] encryptDate = DESedeCoder.encrypt(data.getBytes(), key);
		
		byte[] decryptDate = DESedeCoder.decrypt(encryptDate, key);
		
		System.out.println("解密后的数据：" + new String(decryptDate));
	}

}
