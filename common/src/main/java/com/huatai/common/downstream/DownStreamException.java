package com.huatai.common.downstream;

public class DownStreamException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DownStreamException(String text) {
		super(text);
	}
	
	public DownStreamException(String message, Throwable t) {
		super(message, t);
	}
}
