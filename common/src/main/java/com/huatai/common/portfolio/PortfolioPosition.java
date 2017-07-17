package com.huatai.common.portfolio;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.PortfolioPositionIndex;

public class PortfolioPosition extends TradingPosition {
	private static final long serialVersionUID = 1L;
	private String portfolioNo;
	private String portfolioName;
	private String portfolioType;
	
	public PortfolioPosition() {}
	
	public PortfolioPosition(AssetAccountIndex assetAccountIndex, String tradingAccount, String securityAccount, String symbol, String portfolioNo) {
		super(assetAccountIndex, tradingAccount, securityAccount, symbol);
		this.portfolioNo = portfolioNo;
	}
	
	public PortfolioPositionIndex extractPortfolioPositionIndex() {
		return new PortfolioPositionIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), getTradingAccount(),
				symbol, getSecurityAccount(), portfolioNo);
	}
	
	public String getPortfolioNo() {
		return portfolioNo;
	}
	
	public void setPortfolioNo(String portfolioNo) {
		this.portfolioNo = portfolioNo;
	}
	
	public String getPortfolioName() {
		return portfolioName;
	}
	
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}
	
	public String getPortfolioType() {
		return portfolioType;
	}
	
	public void setPortfolioType(String portfolioType) {
		this.portfolioType = portfolioType;
	}
	
}
