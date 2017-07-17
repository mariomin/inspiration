package com.huatai.common.type;

public enum PortfolioExtensionDescription {
	Intraday("Intraday");
	
	private String value;
	
	PortfolioExtensionDescription(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
