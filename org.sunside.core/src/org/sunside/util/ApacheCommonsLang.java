package org.sunside.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Apache commion lang包使用
 * 
 * @author:Ryan
 * @date:2012-10-21
 */
public class ApacheCommonsLang {

	public static void main(String[] args) {
		
		System.out.println(StringUtils.isEmpty(""));
		
		System.out.println(StringUtils.isNumeric("123h"));
		
		System.out.println(StringUtils.countMatches("123123123123", "123"));
		
		System.out.println(StringEscapeUtils.escapeXml("root"));
		
		System.out.println(RandomStringUtils.randomAlphanumeric(10));
		
		System.out.println(WordUtils.capitalize("hello world!"));
	}

}
