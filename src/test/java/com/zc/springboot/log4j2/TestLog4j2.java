package com.zc.springboot.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog4j2 {

	/**
	 * 导包为：org.apache.logging.log4j，不能到错了
	 */
	private static final Logger LOGGER = LogManager.getLogger(TestLog4j2.class.getName());

	public static void main(String[] args) {
		LOGGER.debug("woshi dbug");
		LOGGER.info("woshi dbug");
		LOGGER.error("woshi dbug");
	}
}
