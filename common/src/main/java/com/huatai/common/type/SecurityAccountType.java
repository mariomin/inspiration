package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum SecurityAccountType {
	Ordinary("Ordinary", "普通账户"), Fund("Fund", "基金账户"), Corporate("Corporate", "法人账户"), BuyBack("BuyBack", "回购账户");
	private String englishValue;
	private String chineseValue;
	
	private static Map<String, SecurityAccountType> englishMap = new HashMap<String, SecurityAccountType>();
	private static Map<String, SecurityAccountType> chineseMap = new HashMap<String, SecurityAccountType>();
	
	static {
		for (SecurityAccountType field : SecurityAccountType.values()) {
			englishMap.put(field.getEnglishValue(), field);
			chineseMap.put(field.getChineseValue(), field);
		}
	}
	
	SecurityAccountType(String englishValue, String chineseValue) {
		this.englishValue = englishValue;
		this.chineseValue = chineseValue;
	}
	
	public String getEnglishValue() {
		return englishValue;
	}
	
	public String getChineseValue() {
		return chineseValue;
	}
	
	public static SecurityAccountType getSecurityAccountTypeByEnglishValue(String value) {
		return englishMap.get(value);
	}
	
	public static SecurityAccountType getSecurityAccountTypeByChineseValue(String value) {
		return chineseMap.get(value);
	}
	
}
