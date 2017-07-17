package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class StopExecAlgoEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String orderId;
	
	public StopExecAlgoEvent(String key, String orderId) {
		super(key);
		this.orderId = orderId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StopExecutionAlgoEvent [orderId=");
		builder.append(orderId);
		builder.append("]");
		return builder.toString();
	}
}
