package com.huatai.common.strategy;

import java.io.Serializable;

public class StrategyQueryRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String clOrdId;
	private final String strategyId;
	private final String strategy;
	private final String strategyState;
	
	public StrategyQueryRecord(String clOrdId, String strategyId, String strategy, String strategyState) {
		this.clOrdId = clOrdId;
		this.strategyId = strategyId;
		this.strategy = strategy;
		this.strategyState = strategyState;
	}
	
	public String getClOrdId() {
		return clOrdId;
	}
	
	public String getStrategy() {
		return strategy;
	}
	
	public String getStrategyState() {
		return strategyState;
	}
	
	public String getStrategyId() {
		return strategyId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StrategyQueryRecord [clOrdId=");
		builder.append(clOrdId);
		builder.append(", strategyId=");
		builder.append(strategyId);
		builder.append(", strategy=");
		builder.append(strategy);
		builder.append(", strategyState=");
		builder.append(strategyState);
		builder.append("]");
		return builder.toString();
	}
	
}
