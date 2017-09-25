package com.inspiration.commonUtil;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Property工具类
 * 
 * @author Administrator
 *
 */
public class PropertyUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	public static Properties properties;

	/**
	 * 加载配置
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadConfig(String fileName) {

		properties = new Properties();
		
		try {
			InputStream inputStream = new FileInputStream(new File(getCurrentConfPath()+fileName));
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("properties load configration failed : properties=" + properties, e);
		}
		return properties;

	}

	/**
	 * 根据key获取value属性值
	 * 
	 * @param key
	 * @param fileName
	 * @return
	 */
	public static String getValueByKey(String key) {
		String value = properties.getProperty(key);
		return value;
	}
	public static String getCurrentConfPath() {
		String c_path = PropertyUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String os_name = System.getProperty("os.name").toLowerCase();
		c_path = os_name.startsWith("win") ? c_path.substring(1, c_path.lastIndexOf("/") + 1) : c_path.substring(0, c_path.lastIndexOf("/") + 1);
		return c_path + "conf/";
	}
	public static String getProperty(String key, String filePath) {
		String retVal = "";
		try {
			String fileName = filePath;
			Properties p = new Properties();
			InputStream in = new FileInputStream(new File(getCurrentConfPath()+fileName));
			p.load(in);
			in.close();
			if (p.containsKey(key)) {
				String propertyVal = p.getProperty(key);
				if (StringUtils.isNotBlank(propertyVal)) {
					retVal = propertyVal;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
