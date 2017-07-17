package com.huatai.common.index;

public class PositionIndex extends AssetAccountIndex {
	private static final long serialVersionUID = 1L;
	private final String symbol;
	private final String securityAccount;
	
	public PositionIndex(AssetAccountIndex assetAccountIndex, String securityAccount, String symbol) {
		super(assetAccountIndex);
		this.symbol = symbol;
		this.securityAccount = securityAccount;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((securityAccount == null) ? 0 : securityAccount.hashCode());
		result = (prime * result) + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		PositionIndex other = (PositionIndex) obj;
		if (securityAccount == null) {
			if (other.securityAccount != null) return false;
		} else if (!securityAccount.equals(other.securityAccount)) return false;
		if (symbol == null) {
			if (other.symbol != null) return false;
		} else if (!symbol.equals(other.symbol)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PositionIndex [symbol=");
		builder.append(symbol);
		builder.append(", securityAccount=");
		builder.append(securityAccount);
		builder.append("]");
		return builder.toString();
	}
	
}
