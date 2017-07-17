package com.huatai.common.event.base;

public abstract class BaseRequestEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestId;
	
	public BaseRequestEvent(String key, String requestId) {
		super(key);
		this.requestId = requestId;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseRequestEvent [requestId=");
		builder.append(requestId);
		builder.append("]");
		return builder.toString();
	}
	
}
