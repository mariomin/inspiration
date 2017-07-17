package com.huatai.common.account;

import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.SecurityAccountType;

public class SecurityAccountEntity extends BaseAccountObject {
	private static final long serialVersionUID = 1L;
	private String securityAccount;
	private ExchangeType exchangeType;
	private SecurityAccountType securityAccountType;
	private boolean mainSecurityAccount;
	private String seatNo;
	private String securityAccountPermission;
	private boolean tradable;
	
	private String branchNo;
	private String pbu;
	
	public boolean allowTradeDelistedListing() {
		return (securityAccountPermission != null) && securityAccountPermission.contains("d");
	}
	
	public boolean allowTradeRiskWarningListing() {
		return (securityAccountPermission != null) && securityAccountPermission.contains("w");
	}
	
	public boolean allowTradeStartupBoardListing() {
		return (securityAccountPermission != null) && securityAccountPermission.contains("j");
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	public void setSecurityAccount(String securityAccount) {
		this.securityAccount = securityAccount;
	}
	
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	
	public SecurityAccountType getSecurityAccountType() {
		return securityAccountType;
	}
	
	public void setSecurityAccountType(SecurityAccountType securityAccountType) {
		this.securityAccountType = securityAccountType;
	}
	
	public boolean isMainSecurityAccount() {
		return mainSecurityAccount;
	}
	
	public void setMainSecurityAccount(boolean mainSecurityAccount) {
		this.mainSecurityAccount = mainSecurityAccount;
	}
	
	public String getSeatNo() {
		return seatNo;
	}
	
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
	public String getSecurityAccountPermission() {
		return securityAccountPermission;
	}
	
	public void setSecurityAccountPermission(String securityAccountPermission) {
		this.securityAccountPermission = securityAccountPermission;
	}
	
	public boolean isTradable() {
		return tradable;
	}
	
	public void setTradable(boolean tradable) {
		this.tradable = tradable;
	}
	
	public String getBranchNo() {
		return branchNo;
	}
	
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
	public String getPbu() {
		return pbu;
	}
	
	public void setPbu(String pbu) {
		this.pbu = pbu;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecurityAccountEntity [securityAccount=");
		builder.append(securityAccount);
		builder.append(", exchangeType=");
		builder.append(exchangeType);
		builder.append(", securityAccountType=");
		builder.append(securityAccountType);
		builder.append(", mainSecurityAccount=");
		builder.append(mainSecurityAccount);
		builder.append(", seatNo=");
		builder.append(seatNo);
		builder.append(", securityAccountPermission=");
		builder.append(securityAccountPermission);
		builder.append(", tradable=");
		builder.append(tradable);
		builder.append(", branchNo=");
		builder.append(branchNo);
		builder.append(", pbu=");
		builder.append(pbu);
		builder.append("]");
		return builder.toString();
	}
	
}
