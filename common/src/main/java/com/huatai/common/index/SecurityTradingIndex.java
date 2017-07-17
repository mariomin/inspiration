package com.huatai.common.index;

public class SecurityTradingIndex extends TradingAccountIndex {
	private static final long serialVersionUID = 1L;
	private final String securityAccount;
	
	public SecurityTradingIndex(AssetAccountIndex assetAccountIndex, String tradingAccount, String securityAccount) {
		super(assetAccountIndex, tradingAccount);
		this.securityAccount = securityAccount;
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((securityAccount == null) ? 0 : securityAccount.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		SecurityTradingIndex other = (SecurityTradingIndex) obj;
		if (securityAccount == null) {
			if (other.securityAccount != null) return false;
		} else if (!securityAccount.equals(other.securityAccount)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecurityTradingIndex [securityAccount=");
		builder.append(securityAccount);
		builder.append("]");
		return builder.toString();
	}
	
}
