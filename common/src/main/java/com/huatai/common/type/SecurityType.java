package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum SecurityType {
	// refer the following link for product details
	// http://www.fixtradingcommunity.org/FIXimate/FIXimate3.0/en/FIX.5.0SP2/tag167.html
	CS("CS"), PS("PS"), FUT("FUT"), OPT("OPT"), CORP("CORP"), CB("CB"), WAR("WAR"), CASH("CASH");
	private String value;
	private static Map<String, SecurityType> map = new HashMap<String, SecurityType>();
	
	static {
		for (SecurityType type : SecurityType.values()) {
			map.put(type.value(), type);
		}
	}
	
	SecurityType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static SecurityType getType(String value) {
		return map.get(value);
	}
	
}
