package com.huatai.common.algo;

import java.io.Serializable;

import com.huatai.common.type.Currency;
import com.huatai.common.type.ExchangeType;

public class SecurityAccountDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String securityAccount;
	private final ExchangeType exchangeType;
	private final Currency currency;
	
	public SecurityAccountDetail(String securityAccount, ExchangeType exchangeType, Currency currency) {
		this.securityAccount = securityAccount;
		this.exchangeType = exchangeType;
		this.currency = currency;
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
}
