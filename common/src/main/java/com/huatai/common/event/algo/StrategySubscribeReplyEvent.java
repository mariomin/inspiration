package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class StrategySubscribeReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestID;
	private boolean success;
	
	public StrategySubscribeReplyEvent(String key, String requestID) {
		super(key);
		this.requestID = requestID;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StrategySubscribeReplyEvent [requestID=");
		builder.append(requestID);
		builder.append(", success=");
		builder.append(success);
		builder.append("]");
		return builder.toString();
	}
	
}
