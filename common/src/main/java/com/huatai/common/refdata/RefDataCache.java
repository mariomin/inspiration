package com.huatai.common.refdata;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RefDataCache {
	private final Map<String, RefData> refDatas = new ConcurrentHashMap<String, RefData>();
	
	public RefData getRefData(String symbol) {
		return refDatas.get(symbol);
	}
	
	public Collection<RefData> getRefDatas() {
		return refDatas.values();
	}
	
	public void updateRefDatas(Collection<RefData> newRefDatas) {
		for (RefData refData : newRefDatas) {
			refDatas.put(refData.getSymbol(), refData);
		}
	}
	
}
