package com.huatai.common.marketdata;

import java.util.Set;

public interface IMarketDataAdaptor {
	void init(IMarketDataListener listener) throws Exception;
	
	void uninit();
	
	boolean getState();
	
	boolean isPrimary();
	
	void setPrimary(boolean primary);
	
	void subscribeMarketData(String instrument);
	
	void subscribeMarketData(Set<String> instruments);
	
	void unsubscribeMarketData(String instrument);
}
