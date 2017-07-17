package com.huatai.common.type;

public enum CommissionTemplateType {
	Normal("Normal");
	
	private String value;
	
	CommissionTemplateType(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
