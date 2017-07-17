package com.huatai.common.refdata.event;

import com.huatai.common.event.base.BaseRequestEvent;
import com.huatai.common.type.ExchangeType;

public class RequestRefDataEvent extends BaseRequestEvent {
	private static final long serialVersionUID = 1L;
	private final String symbol;
	private ExchangeType exchangeType;
	private int pageSize;
	private int offset;
	private int securityListRequestType;
	
	public RequestRefDataEvent(String key, String requestID, String symbol) {
		super(key, requestID);
		this.symbol = symbol;
	}
	
	public RequestRefDataEvent(String key, String requestID) {
		this(key, requestID, null);
	}
	
	public RequestRefDataEvent(String key, String requestID, int securityListRequestType) {
		this(key, requestID, null);
		this.securityListRequestType = securityListRequestType;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getSecurityRequestType() {
		return securityListRequestType;
	}
	
	public void setSecurityListRequestType(int securityListRequestType) {
		this.securityListRequestType = securityListRequestType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestRefDataEvent [symbol=");
		builder.append(symbol);
		builder.append(", exchangeType=");
		builder.append(exchangeType);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", securityListRequestType=");
		builder.append(securityListRequestType);
		builder.append("]");
		return builder.toString();
	}
	
}
