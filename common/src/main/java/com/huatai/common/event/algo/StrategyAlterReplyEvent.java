package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.type.StrategyState;

public class StrategyAlterReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final String requestID;
	private final String strategyID;
	private String clOrdId;
	private StrategyState strategyState;
	private boolean success;
	private String text;
	
	public StrategyAlterReplyEvent(String key, String requestID, String strategyID, StrategyState strategyState, boolean success) {
		super(key);
		this.requestID = requestID;
		this.strategyID = strategyID;
		this.strategyState = strategyState;
		this.success = success;
	}
	
	public void setClOrdId(String clOrdId) {
		this.clOrdId = clOrdId;
	}
	
	public String getClOrdId() {
		return clOrdId;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public String getStrategyID() {
		return strategyID;
	}
	
	public void setStrategyState(StrategyState strategyState) {
		this.strategyState = strategyState;
	}
	
	public StrategyState getStrategyState() {
		return strategyState;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StrategyAlterReplyEvent [requestID=");
		builder.append(requestID);
		builder.append(", strategyID=");
		builder.append(strategyID);
		builder.append(", clOrdId=");
		builder.append(clOrdId);
		builder.append(", strategyState=");
		builder.append(strategyState);
		builder.append(", success=");
		builder.append(success);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
	
}
