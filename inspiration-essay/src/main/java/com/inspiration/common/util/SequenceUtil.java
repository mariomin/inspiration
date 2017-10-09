package com.inspiration.common.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inspiration.common.service.KeyInfoService;

public class SequenceUtil {
	private final Log log = LogFactory.getLog("SequenceUtil.class");
	private static SequenceUtil instance = new SequenceUtil();
	private Map<String, KeyInfoService> keyMap = new HashMap<String, KeyInfoService>(20); // Sequence载体容器

	/**
	 * 禁止外部实例化
	 */
	private SequenceUtil() {
	}

	/**
	 * 获取SequenceUtils的单例对象
	 * 
	 * @return SequenceUtils的单例对象
	 */
	public static SequenceUtil getInstance() {
		return instance;
	}

	/**
	 * 获取下一个Sequence键值
	 * 
	 * @param keyName
	 *            Sequence名称
	 * @return 下一个Sequence键值
	 */
	public synchronized Long getNextKeyValue(String keyName) {
		KeyInfoService keyInfo = null;
		Long keyObject = null;
		try {
			if (keyMap.containsKey(keyName)) {
				keyInfo = keyMap.get(keyName);
			} else {
				keyInfo = new KeyInfoService(keyName, ReadProperties.getPOOL_SIZE());
				keyMap.put(keyName, keyInfo);
			}
			keyObject = keyInfo.getNextKey();
		} catch (SQLException e) {
			log.error("获取下一个seq key失败", e);
			return null;
		}
		return keyObject;
	}

}