package com.inspiration.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConfig {
	private static Logger logger = LoggerFactory.getLogger(ServerConfig.class);
	public static final String LINE_SEP = System.getProperty("line.separator");
	private static Map<String, Object> CONFIG_MAP = new ConcurrentHashMap<String, Object>();
	private static final String configFile = "config.properties";

	public static String getConfigUrl() {
		return configFile;
	}

	public static String getString(String property) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		String value = config.getString(property);
		return value;
	}

	public static PropertiesConfiguration getConfig() {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		return config;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getList(String property, char delimiter) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		config.setListDelimiter(delimiter);
		List<Object> listValue = config.getList(property);
		List<String> retList = new ArrayList<String>();
		for (Object ob : listValue) {
			retList.add(String.valueOf(ob).trim());
		}
		return retList;
	}

	public static boolean setString(String property, String value) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		config.setProperty(property, value);
		try {
			config.save();
			return true;
		} catch (ConfigurationException e) {
			logger.error("save file error");
		}
		return false;
	}

	public static long getLong(String property) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		long value = config.getLong(property);
		return value;
	}

	public static int getInt(String property) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		int value = config.getInt(property);
		return value;
	}

	public static boolean getBoolean(String property) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		boolean value = config.getBoolean(property);
		return value;
	}

	public static double getDouble(String property) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		double value = config.getDouble(property);
		return value;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getKeysList() {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		List<String> keyList = new ArrayList<String>();
		Iterator<String> keys = config.getKeys();
		if ((keys != null) && (keys.hasNext())) {
			while (keys.hasNext()) {
				String key = keys.next();
				keyList.add(key);
			}
		}
		return keyList;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getProperties(String configFile) {
		if (!CONFIG_MAP.containsKey(configFile)) {
			initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) CONFIG_MAP.get(configFile);
		Map<String, String> keyList = new HashMap<String, String>();
		Iterator<String> keys = config.getKeys();
		if ((keys != null) && (keys.hasNext())) {
			while (keys.hasNext()) {
				String key = keys.next();
				String value = config.getString(key);
				keyList.put(key, value);
			}
		}
		return keyList;
	}

	public static synchronized void initConfig(String configFile) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(configFile);
			CONFIG_MAP.put(configFile, config);
		} catch (ConfigurationException e) {
			logger.error("read config error", e);
		}
	}
}
