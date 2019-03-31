package com.kh.spring.common;

import org.apache.log4j.Logger;

public class Log4jTest {
	
	private Logger logger = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {

		new Log4jTest().logTest();
	
	}
	
	
	//log를 뭘로 쓸진 회사에서 정해준다.
	public void logTest() {
		logger.fatal("FATAL");
		logger.error("ERROR");
		logger.warn("WARN");
		logger.info("INFO");
		logger.debug("DEBUG");
	}
	
}
