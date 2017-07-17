package com.huatai.strategy.intraday.common;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class PortfolioRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Symbol")
	private String symbol;
	
	@SerializedName("InitPosition")
	private double initPosition;
	
	@SerializedName("BuySecurityAccount")
	private String buySecurityAccount;
	
	@SerializedName("SellSecurityAccount")
	private String sellSecurityAccount;
	
	@SerializedName("BuyTradingAccount")
	private String buyTradingAccount;
	
	@SerializedName("SellTradingAccount")
	private String sellTradingAccount;
	
	@SerializedName("PortfolioNo")
	private String portfolioNo;
	
	@SerializedName("PortfolioType")
	private String protfolioType;
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getBuySecurityAccount() {
		return buySecurityAccount;
	}
	
	public void setBuySecurityAccount(String buySecurityAccount) {
		this.buySecurityAccount = buySecurityAccount;
	}
	
	public String getSellSecurityAccount() {
		return sellSecurityAccount;
	}
	
	public void setSellSecurityAccount(String sellSecurityAccount) {
		this.sellSecurityAccount = sellSecurityAccount;
	}
	
	public String getBuyTradingAccount() {
		return buyTradingAccount;
	}
	
	public void setBuyTradingAccount(String buyTradingAccount) {
		this.buyTradingAccount = buyTradingAccount;
	}
	
	public String getSellTradingAccount() {
		return sellTradingAccount;
	}
	
	public void setSellTradingAccount(String sellTradingAccount) {
		this.sellTradingAccount = sellTradingAccount;
	}
	
	public String getPortfolioNo() {
		return portfolioNo;
	}
	
	public void setPortfolioNo(String portfolioNo) {
		this.portfolioNo = portfolioNo;
	}
	
	public double getInitPosition() {
		return initPosition;
	}
	
	public void setInitPosition(double initPosition) {
		this.initPosition = initPosition;
	}
	
	public String getProtfolioType() {
		return protfolioType;
	}
	
	public void setProtfolioType(String protfolioType) {
		this.protfolioType = protfolioType;
	}
	
}
