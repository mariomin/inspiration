package com.huatai.platform.downstream.adaptor.o32.common;

public class O32CancelOrderResponse {
	private int errorCode = 0;
	private String errorMsg;
	private String successFlag;
	private String failCause;
	
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
	
	public String getSuccessFlag() {
		return successFlag;
	}
	
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	
	public String getFailCause() {
		return failCause;
	}
	
	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("O32CancelOrderResponse [errorCode=");
		builder.append(errorCode);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append(", successFlag=");
		builder.append(successFlag);
		builder.append(", failCause=");
		builder.append(failCause);
		builder.append("]");
		return builder.toString();
	}
	
}
