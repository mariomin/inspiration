package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class RegisterredStrategyListRequestEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestId;
	
	public RegisterredStrategyListRequestEvent(String key, String requestId) {
		super(key);
		this.requestId = requestId;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterredStrategyListRequestEvent:[requestId=");
		builder.append(requestId);
		builder.append("]");
		return builder.toString();
	}
}
