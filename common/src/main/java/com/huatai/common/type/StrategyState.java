package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum StrategyState {
	Initializing("Initializing"), Running("Running"), Paused("Paused"),
	
	Stopping("Stopping"), Stopped("Stopped"), Terminated("Terminated"),
	
	Uninitializing("Uninitializing"), Error("Error"), Undefined("Undefined");
	
	private static Map<String, StrategyState> map = new HashMap<String, StrategyState>();
	private String value;
	
	static {
		for (StrategyState status : StrategyState.values()) {
			map.put(status.value(), status);
		}
	}
	
	StrategyState(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static StrategyState getStrategyState(String strategyState) {
		return map.get(strategyState);
	}
}
