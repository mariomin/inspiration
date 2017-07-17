package com.huatai.common.marketdata.event;

import com.huatai.common.event.base.AsyncEvent;

public class MarketDataRequestRejectEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	public static final int SubscribeQuote = 1;
	public static final int SubscribeTrade = 2;
	public static final int SubscribeDepth = 3;
	
	private final String invalidSymbol;
	private final int marketDataType;
	private final String mdReqID;
	
	public MarketDataRequestRejectEvent(String key, String invalidSymbol, int marketDataType, String mdReqID) {
		super(key);
		this.invalidSymbol = invalidSymbol;
		this.marketDataType = marketDataType;
		this.mdReqID = mdReqID;
	}
	
	public String getInvalidSymbol() {
		return invalidSymbol;
	}
	
	public int getMarketDataType() {
		return marketDataType;
	}
	
	public String getMdReqID() {
		return mdReqID;
	}
	
}
