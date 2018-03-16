package com.inspiration.common.dto;

import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inspiration.common.util.ServerConfig;
import com.inspiration.common.util.StaticFieldUtils;

public class DataConfig {
	private static Logger logger = LoggerFactory.getLogger(DataConfig.class);
	private static Charset relayDataReadCharset = null;

	public static void toView() {
		try {
			logger.info("DataConfig is:{}", StaticFieldUtils.getClassStaticFieldString(DataConfig.class));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("Get static fields error:" + e.getMessage(), e);
		}
	}

	public static void readConfig() {
		relayDataReadCharset = Charset.forName(ServerConfig.getString("relay.data.read.charset"));
		logger.info("read config file {}", ServerConfig.getConfigUrl());
		toView();
	}

	public static Charset getRelayDataReadCharset() {
		return relayDataReadCharset;
	}

}