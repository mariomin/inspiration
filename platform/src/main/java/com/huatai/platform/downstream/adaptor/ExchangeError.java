package com.huatai.platform.downstream.adaptor;

public class ExchangeError {
	
	//SHSE Code
	public static final int CODE_NEWREJECT_EXCHANGE_BACKEND_ERROR = 99999;
	public static final int CODE_CANCELREJECT_EXCHANGE_BACKEND_ERROR = 99998;
	public static final int CODE_CONNECTION_ERROR = 99997;
	public static final int CODE_CANCEL_TOO_LATE = 99996;
	
	//Message
	public static final String MSG_NEWREJECT_EXCHANGE_BACKEND_ERROR = "New order is rejected by exchange backend error.";
	public static final String MSG_CANCELREJECT_EXCHANGE_BACKEND_ERROR = "Cancel order is rejected by exchange backend error.";
	public static final String MSG_CONNECTION_ERROR = "Connection error.";
	public static final String MSG_CANCEL_TOO_LATE = "Too late to cancel";
	
	//SZSE
	
}
