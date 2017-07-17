package com.huatai.common.strategy;

import java.util.HashMap;
import java.util.Map;

public enum StrategySubscriptionType {
	Subscribe("Subscribe"), Unsubscribe("Unsubscribe");
	
	private static Map<String, StrategySubscriptionType> map = new HashMap<String, StrategySubscriptionType>();
	private String value;
	
	static {
		for (StrategySubscriptionType field : StrategySubscriptionType.values()) {
			map.put(field.value(), field);
		}
	}
	
	StrategySubscriptionType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static String[] displayNames() {
		return map.keySet().toArray(new String[] {});
	}
	
	public static StrategySubscriptionType getStrategySubscriptionTypeByValue(String value) {
		return map.get(value);
	}
}
