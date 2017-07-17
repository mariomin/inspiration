package com.huatai.common.marketsession;

import java.util.HashMap;
import java.util.Map;

import com.huatai.common.type.ExchangeType;

public class MarketSessionCache {
	private final Map<ExchangeType, MarketSessionTime> marketSesTimes = new HashMap<ExchangeType, MarketSessionTime>();
	
	public void updateMarketSesTime(ExchangeType exchangeType, MarketSessionTime marketSesTime) {
		marketSesTimes.put(exchangeType, marketSesTime);
	}
	
	public MarketSessionTime getMarketSesTime(ExchangeType exchangeType) {
		return marketSesTimes.get(exchangeType);
	}
}
