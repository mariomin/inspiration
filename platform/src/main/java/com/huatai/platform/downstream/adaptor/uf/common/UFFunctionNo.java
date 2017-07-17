package com.huatai.platform.downstream.adaptor.uf.common;

public enum UFFunctionNo {
	ClientLoginRequest("331100"), NewOrderRequest("333002"), CancelOrderRequest("333017"), SearchOrderRequest("333101"), SearchDealRequest("333102");
	
	private String functionNo;
	
	UFFunctionNo(String functionNo) {
		this.functionNo = functionNo;
	}
	
	public String getFunctionNo() {
		return functionNo;
	}
}
