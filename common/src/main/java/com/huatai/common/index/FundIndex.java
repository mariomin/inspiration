package com.huatai.common.index;

import com.huatai.common.type.Currency;

public class FundIndex extends AssetAccountIndex {
	private static final long serialVersionUID = 1L;
	private final Currency currency;
	
	public FundIndex(AssetAccountIndex assetAccountIndex, Currency currency) {
		super(assetAccountIndex);
		this.currency = currency;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((currency == null) ? 0 : currency.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		FundIndex other = (FundIndex) obj;
		if (currency != other.currency) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FundIndex [currency=");
		builder.append(currency);
		builder.append("]");
		return builder.toString();
	}
	
}
