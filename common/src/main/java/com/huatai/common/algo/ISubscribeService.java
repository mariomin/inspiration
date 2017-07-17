package com.huatai.common.algo;

import java.util.Date;
import java.util.Set;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.MarketData;

public interface ISubscribeService {
	
	void subscribeMarketDataEvent(Class<? extends MarketData> clazz, Set<String> symbols) throws Exception;
	
	void unsubscribeMarketDataEvent();
	
	void subscribeTimerEvent(long time, AsyncEvent event);
	
	void subscribeTimerEvent(Date time, AsyncEvent event);
	
	void subscribeRepeatTimerEvent(long time, AsyncEvent event);
	
	void unsubscribeTimerEvent(AsyncEvent event);
	
}
