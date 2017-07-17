package com.huatai.platform;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.IPlugin;

public class ReadyList {
	private static final Logger log = LoggerFactory.getLogger(ReadyList.class);
	private final Map<String, Boolean> plugins = new ConcurrentHashMap<String, Boolean>();
	
	public ReadyList(List<IPlugin> plugins) {
		for (IPlugin plugin : plugins) {
			this.plugins.put(plugin.getBeanName(), false);
		}
	}
	
	public void update(String key, boolean value) {
		plugins.put(key, value);
	}
	
	/**
	 *
	 * @param timeout unit: s
	 * @throws TimeoutException
	 */
	public void checkPluginsReady(long timeout) throws TimeoutException {
		long startTime = System.currentTimeMillis();
		while (!allUp()) {
			try {
				Thread.sleep(0, 10);
			} catch (InterruptedException e) {
				log.error("", e);
			}
			
			if ((System.currentTimeMillis() - startTime) > (timeout * 1000)) throw new TimeoutException("Wait plugin init timeout");
		}
	}
	
	private boolean allUp() {
		for (Entry<String, Boolean> entry : plugins.entrySet()) {
			if (!entry.getValue()) return false;
		}
		return true;
	}
	
}