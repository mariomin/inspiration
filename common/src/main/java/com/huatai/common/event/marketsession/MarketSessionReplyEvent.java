package com.huatai.common.event.marketsession;

import java.util.HashMap;
import java.util.Map;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketsession.MarketSessionTime;
import com.huatai.common.type.ExchangeType;

public class MarketSessionReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final Map<ExchangeType, MarketSessionTime> marketSessionTimes = new HashMap<ExchangeType, MarketSessionTime>();
	private final String requestId;
	
	public MarketSessionReplyEvent(String key, String requestId) {
		super(key);
		this.requestId = requestId;
	}
	
	public Map<ExchangeType, MarketSessionTime> getMarketSessionTimes() {
		return marketSessionTimes;
	}
	
	public void addMarketSessionTime(ExchangeType exchangeType, MarketSessionTime marketSessionTime) {
		marketSessionTimes.put(exchangeType, marketSessionTime);
	}
	
	public String getRequestId() {
		return requestId;
	}
}
