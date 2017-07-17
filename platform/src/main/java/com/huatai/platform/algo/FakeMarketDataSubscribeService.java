package com.huatai.platform.algo;

import java.util.Set;

import com.huatai.common.marketdata.AbstractMarketDataEvent;

public class FakeMarketDataSubscribeService extends MarketDataSubscribeService {
	
	@Override
	public void subscribeMarketDateEvent(Class<? extends AbstractMarketDataEvent> clazz, Set<String> symbols, String containerId) throws Exception {}
	
	@Override
	public void unsubscribeMarketDataEvent(String containerId) {}
}
