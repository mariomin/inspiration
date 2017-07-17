package com.huatai.common.marketdata.event.trade;

import java.util.Set;

import com.huatai.common.marketdata.AbstractMarketDataEvent;

public final class TradeSubscribeEvent extends AbstractMarketDataEvent {
	private static final long serialVersionUID = 1L;
	
	public TradeSubscribeEvent(String key, String symbol) {
		super(key, symbol);
	}
	
	public TradeSubscribeEvent(String key, Set<String> symbols) {
		super(key, symbols);
	}
}
