package org.sunside.encryption.cipher;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 密钥输入流
 * 
 * 从文件中，读取数据，然后解密
 * 
 * @author:Ryan
 * @date:2013-6-4
 */
public class CipherInputStreamTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
		
		// 实例化KeyGenerator（密钥生成器对象）对象，并指定DES算法
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

		// 生成秘密密钥
		SecretKey secretKey = keyGenerator.generateKey();

		// 创建Cipher对象
		Cipher cipher = Cipher.getInstance("DES");
		
		// 初始化cipher读写，用于解密
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		// 实例化CipherInputStream对象
		CipherInputStream cis = new CipherInputStream(new FileInputStream(new File("secret.txt")),cipher);
		
		// 包装CipherInputStream对象
		DataInputStream dis = new DataInputStream(cis);
		
		// 读取按照UTF-8编码的字符串
		String outPut = dis.readUTF();
		
		dis.close();
		
		System.out.println("秘密密钥：" + new String(Base64.encode(secretKey.getEncoded())));
		System.out.println("读取的文件内容：" + outPut);
	}

}
