package com.huatai.common.strategy;

import java.util.HashMap;
import java.util.Map;

public enum StrategyInstruction {
	Create("Create"), Start("Start"), Suspend("Suspend"), Resume("Resume"), Stop("Stop");
	
	private static Map<String, StrategyInstruction> map = new HashMap<String, StrategyInstruction>();
	private String value;
	
	static {
		for (StrategyInstruction field : StrategyInstruction.values()) {
			map.put(field.value(), field);
		}
	}
	
	StrategyInstruction(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static String[] displayNames() {
		return map.keySet().toArray(new String[] {});
	}
	
	public static StrategyInstruction getStrategyInstructionByValue(String value) {
		return map.get(value);
	}
}
