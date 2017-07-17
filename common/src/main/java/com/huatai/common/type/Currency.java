package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum Currency {
	CNY("CNY"), CNH("CNH"), HKD("HKD"), USD("USD"), JPY("JPY"), EUR("EUR");
	private static Map<String, Currency> map = new HashMap<String, Currency>();
	private String value;
	
	static {
		for (Currency currency : Currency.values()) {
			map.put(currency.value(), currency);
		}
	}
	
	Currency(String value) {
		this.value = value;
	}
	
	public static Currency getType(String key) {
		return map.get(key);
	}
	
	public String value() {
		return value;
	}
	
}
