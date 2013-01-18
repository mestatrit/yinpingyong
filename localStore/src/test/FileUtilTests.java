package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author:Ryan
 * @date:2013-1-17
 */
public class FileUtilTests {

	 /**  
     * 判断一个文件是否存在  
     *  
     * @param filePath 文件路径  
     * @return 存在返回true，否则返回false  
     */   
    public static boolean isExist(String filePath) {   
            return new File(filePath).exists();   
    }  
	
	public static void main(String[] args) throws IOException {
		
		String msg = "111111111111";
		
		FileUtils.writeStringToFile(new File("C://1.xml"),msg,"UTF-8");
		
		System.out.println(FileUtils.readFileToString(new File("C://1.xml"), "UTF-8"));
		
		System.out.println(isExist(""));
	}

}
