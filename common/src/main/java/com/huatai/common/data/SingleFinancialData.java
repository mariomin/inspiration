package com.huatai.common.data;

import java.io.Serializable;

import com.huatai.common.type.ExchangeType;

public abstract class SingleFinancialData implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String symbol;
	protected String securityId;
	protected ExchangeType exchangeType;
	
	public SingleFinancialData() {}
	
	public SingleFinancialData(String symbol) {
		this.symbol = symbol;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSecurityId() {
		return securityId;
	}
	
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	
}
