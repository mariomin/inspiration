package com.huatai.common.event.algo;

import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.base.AsyncEvent;

public class StopStrategyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final StrategyAlterCommand strategyAlterCommand;
	
	public StopStrategyEvent(String key, StrategyAlterCommand strategyAlterCommand) {
		super(key);
		this.strategyAlterCommand = strategyAlterCommand;
	}
	
	public StrategyAlterCommand getStrategyAlterCommand() {
		return strategyAlterCommand;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StopStrategyEvent [strategyAlterCommand=");
		builder.append(strategyAlterCommand);
		builder.append("]");
		return builder.toString();
	}
	
}
