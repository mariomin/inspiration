package com.huatai.platform.upstream.common;

public class UpStreamException extends Exception {
	private static final long serialVersionUID = 1L;
	private String msgType;
	private int rejectCode;
	
	public UpStreamException(String text) {
		super(text);
	}
	
	public String getMsgType() {
		return msgType;
	}
	
	public int getRejectCode() {
		return rejectCode;
	}
	
}
