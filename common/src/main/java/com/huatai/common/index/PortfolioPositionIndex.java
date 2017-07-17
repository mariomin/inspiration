package com.huatai.common.index;

public class PortfolioPositionIndex extends TradingPositionIndex {
	private static final long serialVersionUID = 1L;
	private final String portfolioNo;
	
	public PortfolioPositionIndex(AssetAccountIndex assetAccountIndex, String tradingAccount, String symbol, String securityAccount,
			String portfolioNo) {
		super(assetAccountIndex, tradingAccount, symbol, securityAccount);
		this.portfolioNo = portfolioNo;
	}
	
	public String getPortfolioNo() {
		return portfolioNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((portfolioNo == null) ? 0 : portfolioNo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		PortfolioPositionIndex other = (PortfolioPositionIndex) obj;
		if (portfolioNo == null) {
			if (other.portfolioNo != null) return false;
		} else if (!portfolioNo.equals(other.portfolioNo)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PortfolioPositionIndex [portfolioNo=");
		builder.append(portfolioNo);
		builder.append("]");
		return builder.toString();
	}
	
}
