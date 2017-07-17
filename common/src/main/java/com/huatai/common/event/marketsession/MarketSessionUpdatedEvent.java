package com.huatai.common.event.marketsession;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketsession.MarketSessionTime;
import com.huatai.common.type.ExchangeType;

public class MarketSessionUpdatedEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final ExchangeType exchangeType;
	private final MarketSessionTime currentMarketSessionTime;
	private MarketSessionTime previousMarketSessionTime;
	
	public MarketSessionUpdatedEvent(ExchangeType exchangeType, MarketSessionTime currentMarketSessionTime) {
		super();
		this.exchangeType = exchangeType;
		this.currentMarketSessionTime = currentMarketSessionTime;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public MarketSessionTime getPreviousMarketSessionTime() {
		return previousMarketSessionTime;
	}
	
	public void setPreviousMarketSessionTime(MarketSessionTime previousMarketSessionTime) {
		this.previousMarketSessionTime = previousMarketSessionTime;
	}
	
	public MarketSessionTime getCurrentMarketSessionTime() {
		return currentMarketSessionTime;
	}
	
}
