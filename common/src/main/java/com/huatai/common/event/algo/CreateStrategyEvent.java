package com.huatai.common.event.algo;

import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.base.AsyncEvent;

public class CreateStrategyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final StrategyAlterCommand strategyAlterCommand;
	private String requestID;
	private boolean loadAccount = true;
	private boolean loadPosition = true;
	
	public CreateStrategyEvent(String key, String requestID, StrategyAlterCommand strategyAlterCommand) {
		super(key);
		this.requestID = requestID;
		this.strategyAlterCommand = strategyAlterCommand;
	}
	
	public StrategyAlterCommand getStrategyAlterCommand() {
		return strategyAlterCommand;
	}
	
	public String getRequestID() {
		return requestID;
	}
	
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	
	public boolean isLoadAccount() {
		return loadAccount;
	}
	
	public void setLoadAccount(boolean loadAccount) {
		this.loadAccount = loadAccount;
	}
	
	public boolean isLoadPosition() {
		return loadPosition;
	}
	
	public void setLoadPosition(boolean loadPosition) {
		this.loadPosition = loadPosition;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CreateStrategyEvent [strategyAlterCommand=");
		builder.append(strategyAlterCommand);
		builder.append(", requestID=");
		builder.append(requestID);
		builder.append("]");
		return builder.toString();
	}
	
}
