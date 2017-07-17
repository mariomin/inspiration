package com.huatai.common.marketdata.event.quote;

import java.util.Set;

import com.huatai.common.marketdata.AbstractMarketDataEvent;
import com.huatai.common.marketdata.Level;

public class QuoteSubscribeEvent extends AbstractMarketDataEvent {
	private static final long serialVersionUID = 1L;
	
	public QuoteSubscribeEvent(String key, String symbol) {
		super(key, symbol);
	}
	
	public QuoteSubscribeEvent(String key, Set<String> symbols, Level level) {
		super(key, symbols, level);
	}
	
}
