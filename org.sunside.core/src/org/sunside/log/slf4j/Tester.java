package org.sunside.log.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tester {

	private static final Logger logger = LoggerFactory.getLogger(Tester.class);

	public static void main(String[] args) {
		for (int index = 0; index < 100; index++) {
			logger.info("111");
			logger.warn("222");
			logger.error("333");
		}
	}

}
