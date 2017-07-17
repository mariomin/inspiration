package com.huatai.common.marketdata.event;

import com.huatai.common.event.base.BaseRequestEvent;

public class RequestMarketDataDetailEvent extends BaseRequestEvent {
	private static final long serialVersionUID = 1L;
	
	private final String symbol;
	
	public RequestMarketDataDetailEvent(String key, String requestID, String symbol) {
		super(key, requestID);
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestMarketDataDetailEvent [symbol=");
		builder.append(symbol);
		builder.append(", BaseRequest=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
