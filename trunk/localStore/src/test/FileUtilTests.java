package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author:Ryan
 * @date:2013-1-17
 */
public class FileUtilTests {

	public static void main(String[] args) throws IOException {
		
		String msg = "111111111111";
		
		FileUtils.writeStringToFile(new File("C://1.xml"),msg,"UTF-8");
		
		System.out.println(FileUtils.readFileToString(new File("C://1.xml"), "UTF-8"));
	}

}
