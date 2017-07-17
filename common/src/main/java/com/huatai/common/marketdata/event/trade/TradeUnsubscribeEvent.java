package com.huatai.common.marketdata.event.trade;

import java.util.Set;

import com.huatai.common.marketdata.AbstractMarketDataEvent;

public class TradeUnsubscribeEvent extends AbstractMarketDataEvent {
	private static final long serialVersionUID = 1L;
	
	public TradeUnsubscribeEvent(String key, Set<String> symbols) {
		super(key, symbols);
	}
}
