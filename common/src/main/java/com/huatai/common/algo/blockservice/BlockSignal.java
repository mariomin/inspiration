package com.huatai.common.algo.blockservice;

public enum BlockSignal {
	Account("account"), Position("position");
	private String value;
	
	BlockSignal(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
