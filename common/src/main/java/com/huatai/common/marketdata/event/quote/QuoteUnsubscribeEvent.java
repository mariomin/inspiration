package com.huatai.common.marketdata.event.quote;

import java.util.Set;

import com.huatai.common.marketdata.AbstractMarketDataEvent;

public class QuoteUnsubscribeEvent extends AbstractMarketDataEvent {
	private static final long serialVersionUID = 1L;
	
	public QuoteUnsubscribeEvent(String key, Set<String> symbols) {
		super(key, symbols, null);
	}
}
