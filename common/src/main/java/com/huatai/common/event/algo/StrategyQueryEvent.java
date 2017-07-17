package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.index.SecurityTradingIndex;

public class StrategyQueryEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestID;
	private final SecurityTradingIndex securityTradingIndex;
	
	public StrategyQueryEvent(String key, String requestID, SecurityTradingIndex securityTradingIndex) {
		super(key);
		this.securityTradingIndex = securityTradingIndex;
		this.requestID = requestID;
	}
	
	public SecurityTradingIndex getSecurityTradingIndex() {
		return securityTradingIndex;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
}
