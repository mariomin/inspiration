package com.huatai.common.o32.account;

import com.huatai.common.type.Currency;

public class O32FundDetailsMapping {
	private String accountCode;
	private String assetNo;
	private Currency currency;
	private double currentBalanece;
	
	public O32FundDetailsMapping() {}
	
	public String getAccountCode() {
		return accountCode;
	}
	
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	public String getAssetNo() {
		return assetNo;
	}
	
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public double getCurrentBalance() {
		return currentBalanece;
	}
	
	public void setCurrentBalance(double currentBalanece) {
		this.currentBalanece = currentBalanece;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("O32FundDetailsMapping [accountCode=");
		builder.append(accountCode);
		builder.append(", assetNo=");
		builder.append(assetNo);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", currentBalanece=");
		builder.append(currentBalanece);
		builder.append("]");
		return builder.toString();
	}
	
}
