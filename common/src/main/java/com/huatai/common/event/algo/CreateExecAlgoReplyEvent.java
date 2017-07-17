package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class CreateExecAlgoReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String orderId;
	private final boolean succeed;
	private final String text;
	
	public CreateExecAlgoReplyEvent(String key, String orderId, boolean succeed, String text) {
		super(key);
		this.orderId = orderId;
		this.succeed = succeed;
		this.text = text;
	}
	
	public boolean isSucceed() {
		return succeed;
	}
	
	public String getText() {
		return text;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CreateExecutionAlgoReplyEvent [orderId=");
		builder.append(orderId);
		builder.append(",succeed=");
		builder.append(succeed);
		builder.append(",text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
