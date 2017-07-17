package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.strategy.StrategySubscriptionType;

public class StrategySubscribeEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestID;
	private final SecurityTradingIndex securityTradingIndex;
	private final String subscribeID;
	private final String strategyID;
	private final StrategySubscriptionType type;
	
	public StrategySubscribeEvent(String key, String requestID, SecurityTradingIndex securityTradingIndex, String subscribeID, String strategyID,
			StrategySubscriptionType type) {
		super(key);
		this.requestID = requestID;
		this.securityTradingIndex = securityTradingIndex;
		this.subscribeID = subscribeID;
		this.strategyID = strategyID;
		this.type = type;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public SecurityTradingIndex getSecurityTradingIndex() {
		return securityTradingIndex;
	}
	
	public String getSubscribeID() {
		return subscribeID;
	}
	
	public String getStrategyID() {
		return strategyID;
	}
	
	public StrategySubscriptionType getType() {
		return type;
	}
	
}
