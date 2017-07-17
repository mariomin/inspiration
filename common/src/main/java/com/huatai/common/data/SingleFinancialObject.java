package com.huatai.common.data;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.type.ExchangeType;

public abstract class SingleFinancialObject extends AccountableObject {
	private static final long serialVersionUID = 1L;
	protected String symbol;
	protected String securityId;
	protected ExchangeType exchangeType;
	
	protected SingleFinancialObject() {}
	
	protected SingleFinancialObject(AssetAccountIndex assetAccountIndex) {
		super(assetAccountIndex);
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
	
	@Override
	public boolean equals(Object anObject) {
		if (this == anObject) return true;
		if (anObject instanceof SingleFinancialObject) return ((SingleFinancialObject) anObject).getId().equals(id);
		return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public synchronized void update(PersistentObject persistentObject) {
		super.update(persistentObject);
		SingleFinancialObject fo = (SingleFinancialObject) persistentObject;
		symbol = fo.getSymbol();
		securityId = fo.getSecurityId();
		exchangeType = fo.getExchangeType();
	}
	
}
