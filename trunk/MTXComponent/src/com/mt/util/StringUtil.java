package com.mt.util;

import com.mt.log.MTLog;

public class StringUtil {

    /**
     * 从字串中分离出指定值
     * 形如 Name(Id)
     * @return Id
     */
    public static String destructorString(String content) {
        try {
            return content.substring(content.lastIndexOf("(") + 1, content.lastIndexOf(")"));
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * 从字串中分离出指定值
     * 形如 Name(Id)
     * @return Name
     */
    public static String destructorString_out(String content) {
        try {
            return content.substring(0, content.lastIndexOf("("));
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * Append toFill to Str till length=length
     *
     * @param str
     * @param tofill
     * @param length
     * @return
     */
    public static String fill(String str, String tofill, int length) {
        if (str.getBytes().length >= length) {
            return new String(str.getBytes(), 0, length);
        }

        String tmp = new String(str);
        for (int i = str.getBytes().length; i < length; i++) {
            tmp += tofill;
        }

        return tmp;
    }

    /**
     * Preppend toFill to Str till length=length
     *
     * @param str
     * @param tofill
     * @param length
     * @return
     */
    public static String fillInv(String str, String tofill, int length) {
        str = str.trim();
        if (str.getBytes().length >= length) {
            return new String(str.getBytes(), 0, length);
        }

        String tmp = new String(str);
        for (int i = str.getBytes().length; i < length; i++) {
            tmp = tofill + tmp;
        }

        return tmp;
    }

    public static String remChar(String string, char c) {
        char[] temp = string.toCharArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != c) {
                buffer.append(temp[i]);
            }
        }
        return buffer.toString();
    }

    /**
     * 通常服务器端让客户端检查某个栏位的长度,服务器人员只告诉你字节长度
     * 但是如果你不知道字符串的charset你是难以精确计算这个字符串长度的
     * 这个函数提供了检查ComStarServer字符串的长度，目前ComStar的charset是GBK
     * @param str
     * @return
     */
    public static int getComStarServerStringByteLength(String str) {
        try {
            return str.getBytes("GBK").length;
        } catch (Exception ex) {
            MTLog.error(ex);
            return str.getBytes().length;
        }
    }
    
    /**
     * 得到该字符串出现第一个数字的位置
     * @param str
     * @return
     */
    public static int getFirstNumberIndex(String str){
    	for (int i = 0; i < str.length(); i++){
    		char c = str.charAt(i);
    		if(Character.isDigit(c))return i;
    	}
    	return str.length();
    }

    public static boolean isDigitStr(String str){
        for(int i = 0 ; i <str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static boolean isInt(String n){
        try{
            Integer.parseInt(n);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    /**
     * 移除文件名中不允许的字符
     * @param str
     * @return
     */
    public static String rmFChar(String str){
        str = str.trim();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0;i < str.length();i++){
            char c = str.charAt(i);
           if(c =='\\' || c=='/' || c=='?' || c=='*' || c=='|' ||
                   c==']' || c=='[' || c==':' || c=='<' || c=='>'){
                continue;
            }else{
               stringBuilder.append(c);                
            }
        }
        return stringBuilder.toString();
    }

}
