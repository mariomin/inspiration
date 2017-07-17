package com.huatai.common.event.algo;

import java.util.Set;

import com.huatai.common.event.base.AsyncEvent;

public class RegisterredStrategyReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestId;
	private final Set<String> strategyNames;
	
	public RegisterredStrategyReplyEvent(String key, String requestId, Set<String> strategyNames) {
		super(key);
		this.requestId = requestId;
		this.strategyNames = strategyNames;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	public Set<String> getStrategyNames() {
		return strategyNames;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterredStrategyReplyEvent:[requestId=");
		builder.append(requestId);
		builder.append(", strategyNames=");
		builder.append(strategyNames);
		builder.append("]");
		return builder.toString();
	}
}
