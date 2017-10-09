package com.inspiration.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReadProperties {
	private final static Log log = LogFactory.getLog("ReadProperties.class");
	private static Integer POOL_SIZE = null;

	private static Integer REDIS_POOL_MAXACTIVE = null;

	private static Integer REDIS_POOL_MAXIDLE = null;

	private static Integer REDIS_POOL_MAXWAIT = null;

	private static Boolean REDIS_POOL_TESTONBORROW = true;

	private static String REDIS_IP = null;

	private static Integer REDIS_PORT = null;

	private static double VERSION = 1.0;

	static {
		loads();
	}

	synchronized static public void loads() {
		if (null == POOL_SIZE) {
			InputStreamReader is;
			try {
				// 以utf-8读取文件,默认为ios
				is = new InputStreamReader(
						ReadProperties.class.getClassLoader().getResourceAsStream("/config.properties"),
						CommonConstant.encodeUTF);
				Properties dbproperties = new Properties();
				dbproperties.load(is);
				POOL_SIZE = Integer.parseInt(dbproperties.getProperty("POOL_SIZE"));
				REDIS_POOL_MAXACTIVE = Integer.parseInt(dbproperties.getProperty("POOL_MAXACTIVE"));
				REDIS_POOL_MAXIDLE = Integer.parseInt(dbproperties.getProperty("POOL_MAXIDLE"));
				REDIS_POOL_MAXWAIT = Integer.parseInt(dbproperties.getProperty("POOL_MAXWAIT"));
				REDIS_POOL_TESTONBORROW = Boolean.valueOf(dbproperties.getProperty("POOL_TESTONBORROW"));
				REDIS_IP = dbproperties.getProperty("REDIS_IP");
				REDIS_PORT = Integer.parseInt(dbproperties.getProperty("REDIS_PORT"));
				VERSION = Double.parseDouble(dbproperties.getProperty("VERSION"));
			} catch (IOException e) {
				log.error("文件读取失败", e);
			} catch (NumberFormatException e1) {
				log.error("数据类型转换错误", e1);
			}
		}
	}

	public static Integer getPOOL_SIZE() {
		return POOL_SIZE;
	}

	public static Integer getREDIS_POOL_MAXACTIVE() {
		return REDIS_POOL_MAXACTIVE;
	}

	public static Integer getREDIS_POOL_MAXIDLE() {
		return REDIS_POOL_MAXIDLE;
	}

	public static Integer getREDIS_POOL_MAXWAIT() {
		return REDIS_POOL_MAXWAIT;
	}

	public static Boolean getREDIS_POOL_TESTONBORROW() {
		return REDIS_POOL_TESTONBORROW;
	}

	public static String getREDIS_IP() {
		return REDIS_IP;
	}

	public static Integer getREDIS_PORT() {
		return REDIS_PORT;
	}

	public static double getVERSION() {
		return VERSION;
	}
}
