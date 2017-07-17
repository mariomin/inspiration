package com.huatai.platform.downstream.adaptor.o32.processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author 010350
 *
 */
public class O32DataCache {
	private O32DataCache() {
		
	}
	
	private static final O32DataCache instance = new O32DataCache();
	
	private final Map<String, String> extsystemId2entrustNo = new ConcurrentHashMap<String, String>(500000);
	
	public static final O32DataCache getInstance() {
		return instance;
	}
	
	public String getEntrustNoByExtsystemId(String extsystemId) {
		return extsystemId2entrustNo.get(extsystemId);
	}
	
	public void putEntrustNo(String extsystemId, String entrustNo) {
		extsystemId2entrustNo.put(extsystemId, entrustNo);
	}
}
