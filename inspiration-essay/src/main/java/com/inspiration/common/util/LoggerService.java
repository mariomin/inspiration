package com.inspiration.common.util;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerService {
	private static Logger logger = LoggerFactory.getLogger(LoggerService.class);

	public void startLog() {
		String confURL = "log4j.properties";
		PropertyConfigurator.configure(confURL);
		logger.info("Open the Logging Successfully");
	}
}