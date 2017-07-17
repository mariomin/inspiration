package com.huatai.common.strategy;

public class OrderMsg {
	private final boolean successed;
	private final String clOrdId;
	private final String message;
	
	public OrderMsg(boolean sucessed, String clOrdId, String message) {
		successed = sucessed;
		this.clOrdId = clOrdId;
		this.message = message;
	}
	
	public boolean isSuccessed() {
		return successed;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getClOrdId() {
		return clOrdId;
	}
	
}
