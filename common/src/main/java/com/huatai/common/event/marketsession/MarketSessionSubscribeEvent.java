package com.huatai.common.event.marketsession;

import com.huatai.common.event.base.BaseRequestEvent;
import com.huatai.common.type.ExchangeType;

public class MarketSessionSubscribeEvent extends BaseRequestEvent {
	private static final long serialVersionUID = 1L;
	private final ExchangeType exchangeType;
	
	public MarketSessionSubscribeEvent(String key, ExchangeType exchangeType, String requestId) {
		super(key, requestId);
		this.exchangeType = exchangeType;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
}
