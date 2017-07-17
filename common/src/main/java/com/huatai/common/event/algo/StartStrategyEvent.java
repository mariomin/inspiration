package com.huatai.common.event.algo;

import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.base.AsyncEvent;

public class StartStrategyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final StrategyAlterCommand strategyAlterCommand;
	
	public StartStrategyEvent(String key, StrategyAlterCommand strategyAlterCommand) {
		super(key);
		this.strategyAlterCommand = strategyAlterCommand;
	}
	
	public StrategyAlterCommand getStrategyAlterCommand() {
		return strategyAlterCommand;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StartStrategyEvent [strategyAlterCommand=");
		builder.append(strategyAlterCommand);
		builder.append("]");
		return builder.toString();
	}
	
}
