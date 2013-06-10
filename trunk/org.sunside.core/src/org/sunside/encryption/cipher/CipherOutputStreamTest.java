package org.sunside.encryption.cipher;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * 密钥输出流
 * 
 * 加密数据，然后向文件中写入
 * 
 * @author:Ryan
 * @date:2013-6-4
 */
public class CipherOutputStreamTest {

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
		
		// 初始化cipher读写，用于加密
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		// 实例化CipherOutputStream对象
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(new File("secret.txt")),cipher);
		
		// 包装CipherOutputStream对象
		DataOutputStream dos = new DataOutputStream(cos);
		
		// 读取按照UTF-8编码的字符串
		dos.writeUTF("12345678");
		
		dos.flush();
		dos.close();
		
		System.out.println("秘密密钥：" + new String(Base64.encode(secretKey.getEncoded())));
	}

}
