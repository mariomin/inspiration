package com.huatai.common.event.account;

import com.huatai.common.event.base.BaseRequestEvent;
import com.huatai.common.index.TradingAccountIndex;

public class RequestPositionEvent extends BaseRequestEvent {
	private static final long serialVersionUID = 1L;
	private final int posReqType;
	private final TradingAccountIndex tradingAccountIndex;
	
	public RequestPositionEvent(String key, String posReqID, int posReqType, TradingAccountIndex tradingAccountIndex) {
		super(key, posReqID);
		this.posReqType = posReqType;
		this.tradingAccountIndex = tradingAccountIndex;
	}
	
	public int getPosReqType() {
		return posReqType;
	}
	
	public TradingAccountIndex getTradingAccountIndex() {
		return tradingAccountIndex;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestPositionEvent [posReqType=");
		builder.append(posReqType);
		builder.append("]");
		return builder.toString();
	}
	
}
