package com.cfets.server.util;

public class StringUtil {
	
	public static String getStackTrace(Throwable t) {
		
		StackTraceElement[] info = t.getStackTrace();
		
		if(info == null) {
			return "";
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(t.getClass().toString() + "\n" +"CallBackTrace:\n\t" + info[0].toString());
		
		for(int index = 1; index < info.length; index ++) {
			stringBuffer.append("\n\t" + info[index].toString());
		}
		
		return stringBuffer.toString();
	}
	
}
