package com.huatai.common.marketdata;

import java.util.HashMap;
import java.util.Map;

public enum MarketDataServiceField {
	Quote('y'), Trade('2'), Depth('z');
	
	private static final Map<Character, MarketDataServiceField> map = new HashMap<Character, MarketDataServiceField>();
	private char value;
	
	static {
		for (MarketDataServiceField field : MarketDataServiceField.values()) {
			map.put(field.value(), field);
		}
	}
	
	MarketDataServiceField(char value) {
		this.value = value;
	}
	
	public char value() {
		return value;
	}
	
	public static MarketDataServiceField getValue(char str) {
		return map.get(str);
	}
	
}
