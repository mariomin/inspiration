package com.huatai.common.index;

import com.huatai.common.type.Currency;

public class TradingFundIndex extends FundIndex {
	private static final long serialVersionUID = 1L;
	private final String tradingAccount;
	
	public TradingFundIndex(AssetAccountIndex assetAccountIndex, String tradingAccount, Currency currency) {
		super(assetAccountIndex, currency);
		this.tradingAccount = tradingAccount;
	}
	
	public String getTradingAccount() {
		return tradingAccount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((tradingAccount == null) ? 0 : tradingAccount.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		TradingFundIndex other = (TradingFundIndex) obj;
		if (tradingAccount == null) {
			if (other.tradingAccount != null) return false;
		} else if (!tradingAccount.equals(other.tradingAccount)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradingFundIndex [tradingAccount=");
		builder.append(tradingAccount);
		builder.append("]");
		return builder.toString();
	}
	
}
