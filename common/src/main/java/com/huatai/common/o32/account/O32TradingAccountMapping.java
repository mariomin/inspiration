package com.huatai.common.o32.account;

import com.huatai.common.data.PersistentObject;

public class O32TradingAccountMapping extends PersistentObject {
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String operatorNo;
	private String fundAccount;
	private String securityAccount;
	private String accountCode;
	private String assetNo;
	private String combinationNo;
	private String combinationName;
	private String authorizedMarket;
	private String authorizedFuture;
	private String authorizedTradeSide;
	
	public O32TradingAccountMapping() {}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getOperatorNo() {
		return operatorNo;
	}
	
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	
	public String getFundAccount() {
		return fundAccount;
	}
	
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	public void setSecurityAccount(String securityAccount) {
		this.securityAccount = securityAccount;
	}
	
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
	
	public String getCombinationNo() {
		return combinationNo;
	}
	
	public void setCombinationNo(String combinationNo) {
		this.combinationNo = combinationNo;
	}
	
	public String getCombinationName() {
		return combinationName;
	}
	
	public void setCombinationName(String combinationName) {
		this.combinationName = combinationName;
	}
	
	public String getAuthorizedMarket() {
		return authorizedMarket;
	}
	
	public void setAuthorizedMarket(String authorizedMarket) {
		this.authorizedMarket = authorizedMarket;
	}
	
	public String getAuthorizedFuture() {
		return authorizedFuture;
	}
	
	public void setAuthorizedFuture(String authorizedFuture) {
		this.authorizedFuture = authorizedFuture;
	}
	
	public String getAuthorizedTradeSide() {
		return authorizedTradeSide;
	}
	
	public void setAuthorizedTradeSide(String authorizedTradeSide) {
		this.authorizedTradeSide = authorizedTradeSide;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("O32TradingAccountMapping [clientId=");
		builder.append(clientId);
		builder.append(", fundAccount=");
		builder.append(fundAccount);
		builder.append(", securityAccount=");
		builder.append(securityAccount);
		builder.append(", accountCode=");
		builder.append(accountCode);
		builder.append(", assetNo=");
		builder.append(assetNo);
		builder.append(", combinationNo=");
		builder.append(combinationNo);
		builder.append(", combinationName=");
		builder.append(combinationName);
		builder.append(", authorizedMarket=");
		builder.append(authorizedMarket);
		builder.append(", authorizedFuture=");
		builder.append(authorizedFuture);
		builder.append(", authorizedTradeSide=");
		builder.append(authorizedTradeSide);
		builder.append("]");
		return builder.toString();
	}
	
}
