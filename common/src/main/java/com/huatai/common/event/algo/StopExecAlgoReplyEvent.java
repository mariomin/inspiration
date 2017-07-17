package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class StopExecAlgoReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String orderId;
	private final boolean successed;
	private final String text;
	
	public StopExecAlgoReplyEvent(String key, String orderId, boolean successed, String text) {
		super(key);
		this.orderId = orderId;
		this.successed = successed;
		this.text = text;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public boolean isSuccessed() {
		return successed;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StopExecutionAlgoReplyEvent [orderId=");
		builder.append(orderId);
		builder.append(", successed=");
		builder.append(successed);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
