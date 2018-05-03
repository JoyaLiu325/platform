package com.hust.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogbackTest {
	private static Logger logger = LoggerFactory.getLogger(LogbackTest.class); 
	
	@Test
	public void logback() {
		logger.trace("logback的--trace日志--输出了");
		logger.debug("logback的--debug日志--输出了");
		logger.info("logback的--info日志--输出了");
		logger.warn("logback的--warn日志--输出了");
		logger.error("logback的--error日志--输出了");
	}
}
