package com.mt.common.gui.table;

import java.util.HashMap;
/**
 * 用于排序期限品种
 * @author csj
 *
 */
public class MCType {
	 static HashMap<String, Integer> libMap = new HashMap<String, Integer>();
	    
	    static{
	    	libMap.put("O/N", 0);
	    	libMap.put("TOD", 1);
	    	libMap.put("TOM", 2);
	    	libMap.put("01", 3);
	    	libMap.put("1D", 4);
	    	libMap.put("07", 5);
	    	libMap.put("1W", 6);
	    	libMap.put("14", 7);
	    	libMap.put("2W", 8);
	    	libMap.put("21", 9);
	    	libMap.put("3W", 10);
	    	libMap.put("1M", 11);
	    	libMap.put("2M", 12);
	    	libMap.put("3M", 13);
	    	libMap.put("4M", 14);
	    	libMap.put("5M", 15);
	    	libMap.put("6M", 16);
	    	libMap.put("9M", 17);
	    	libMap.put("1Y", 18);
	    	libMap.put("18M", 19);
	    	libMap.put("2Y", 20);
	    	libMap.put("3Y", 21);
	    	libMap.put("4Y", 22);
	    	libMap.put("5Y", 23);
	    	libMap.put("6Y", 24);
	    	libMap.put("7Y", 25);
	    	libMap.put("8Y", 26);
	    	libMap.put("9Y", 27);
	    	libMap.put("10Y", 28);
	    	libMap.put("T/N", 29);
	    	libMap.put("BROKEN DATE", 30);
	    }
}
