package com.huatai.strategy.intraday.common;

import com.huatai.common.event.base.AsyncEvent;

public class IntradayTimeEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String symbol;
	private final String clOrderId;
	private final int timeout;
	
	public IntradayTimeEvent(String symbol, String clOrderId, int timeout) {
		this.symbol = symbol;
		this.clOrderId = clOrderId;
		this.timeout = timeout;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getClOrderId() {
		return clOrderId;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
}
