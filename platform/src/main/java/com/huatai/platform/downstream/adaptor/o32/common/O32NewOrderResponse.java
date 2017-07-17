package com.huatai.platform.downstream.adaptor.o32.common;

public class O32NewOrderResponse {
	private int errorCode = 0;
	private String errorMsg;
	private String entrustNo;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getEntrustNo() {
		return entrustNo;
	}
	
	public void setEntrustNo(String entrustNo) {
		this.entrustNo = entrustNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("O32NewOrderResponse [errorCode=");
		builder.append(errorCode);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append(", entrustNo=");
		builder.append(entrustNo);
		builder.append("]");
		return builder.toString();
	}
	
}
