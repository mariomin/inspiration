package com.huatai.common.data;

public class ErrorCode {
	public static final int DuplicatedClientOrderId = 1;
	public static final int OrderValidationNotPassed = 2;
	public static final int ExchangeConnectionError = 3;
	public static final int StrategyRuntimeError = 4;
	public static final int NoEnoughCashError = 5;
	public static final int NoEnoughSecurityError = 6;
	public static final int StrategyNotInitializedError = 7;
	public static final int OtherError = 8;
	public static final int NoCorrespondingOrderError = 9;
	public static final int OrderUnableCancelError = 10;
	public static final int UnsupportedMessageTypeError = 11;
	public static final int NewOrderSingleParsingError = 12;
	public static final int NewOrderListParsingError = 13;
	public static final int OrderCancelReplaceRequestParsingError = 14;
	
	public static String assemblyErrorInfo(int errorCode, String text) {
		return errorCode + ":" + text;
	}
}
