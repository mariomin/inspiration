package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum ExchangeType {
	HKEX("HKEX", "联交所"), SH("SH", "上交所"), SZ("SZ", "深交所"), SIM("SIM", "模拟交易所"), SHF("SHF", "上期所");
	private String exchangeCode;
	private String chineseName;
	
	private static Map<String, ExchangeType> englishMap = new HashMap<String, ExchangeType>();
	private static Map<String, ExchangeType> chineseMap = new HashMap<String, ExchangeType>();
	
	static {
		for (ExchangeType field : ExchangeType.values()) {
			englishMap.put(field.value(), field);
			chineseMap.put(field.getChineseValue(), field);
		}
	}
	
	ExchangeType(String exchangeCode, String chineseName) {
		this.exchangeCode = exchangeCode;
		this.chineseName = chineseName;
	}
	
	public String value() {
		return exchangeCode;
	}
	
	public String getChineseValue() {
		return chineseName;
	}
	
	public static ExchangeType getExchangeTypeByValue(String value) {
		return englishMap.get(value);
	}
	
	public static ExchangeType getExchangeTypeByChineseValue(String value) {
		return chineseMap.get(value);
	}
	
}
