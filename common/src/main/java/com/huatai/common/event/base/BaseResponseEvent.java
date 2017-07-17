package com.huatai.common.event.base;

public abstract class BaseResponseEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestID;
	private String text;
	
	public BaseResponseEvent(String key, String requestID) {
		super(key);
		this.requestID = requestID;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}
