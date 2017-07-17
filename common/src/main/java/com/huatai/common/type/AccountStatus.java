package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {
	Good("Good", "正常"), Freezing("Freezing", "冻结"), Missing("Missing", "丢失"), Closing("Closing", "销户"), NoSuchAccount("NoSuchAccount", "没有相关账户");
	private String englishValue;
	private String chineseValue;
	
	private static Map<String, AccountStatus> englishMap = new HashMap<String, AccountStatus>();
	private static Map<String, AccountStatus> chineseMap = new HashMap<String, AccountStatus>();
	
	static {
		for (AccountStatus field : AccountStatus.values()) {
			englishMap.put(field.getEnglishValue(), field);
			chineseMap.put(field.getChineseValue(), field);
		}
	}
	
	AccountStatus(String englishValue, String chineseValue) {
		this.englishValue = englishValue;
		this.chineseValue = chineseValue;
	}
	
	public String getEnglishValue() {
		return englishValue;
	}
	
	public String getChineseValue() {
		return chineseValue;
	}
	
	public static AccountStatus getAccountStatusByEnglishValue(String value) {
		return englishMap.get(value);
	}
	
	public static AccountStatus getAccountStatusByChineseValue(String value) {
		return chineseMap.get(value);
	}
	
}
