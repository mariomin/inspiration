package com.huatai.common.marketdata;

import java.util.List;
import java.util.Set;

public class RequestMarketData {
	private final String requestId;
	private final char subscriptionRequestType;
	private final int marketDepth;
	private final Set<String> symbols;
	private final List<Character> mDEntryTypes;
	
	public RequestMarketData(String requestId, char subscriptionRequestType, int marketDepth, Set<String> symbols, List<Character> mDEntryTypes) {
		this.requestId = requestId;
		this.subscriptionRequestType = subscriptionRequestType;
		this.marketDepth = marketDepth;
		this.symbols = symbols;
		this.mDEntryTypes = mDEntryTypes;
	}
	
	public Set<String> getSymbols() {
		return symbols;
	}
	
	public List<Character> getMDEntryTypes() {
		return mDEntryTypes;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	public char getSubscriptionRequestType() {
		return subscriptionRequestType;
	}
	
	public List<Character> getmDEntryTypes() {
		return mDEntryTypes;
	}
	
	public int getMarketDepth() {
		return marketDepth;
	}
	
}
