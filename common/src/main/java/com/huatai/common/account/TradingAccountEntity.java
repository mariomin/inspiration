package com.huatai.common.account;

import java.util.HashMap;
import java.util.Map;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.type.AssetAccountType;

public class TradingAccountEntity extends BaseAccountObject {
	private static final long serialVersionUID = 1L;
	private String investorAccount;
	private String productAccount;
	private String assetAccount;
	private AssetAccountType assetAccountType;
	private String tradingAccount;
	private final Map<String, SecurityAccountEntity> securityAccountEntities = new HashMap<String, SecurityAccountEntity>();
	
	private String clientPermissions;
	private String allowedEntrusts;
	private String restriction;
	
	public AssetAccountIndex extractAssetAccountIndex() {
		return new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType);
	}
	
	public boolean allowNetEntrusts() {
		return (allowedEntrusts != null) && allowedEntrusts.contains("7");
	}
	
	public boolean isBuyOrderRestricted() {
		return (restriction != null) && restriction.contains("1");
	}
	
	public boolean isSellOrderRestricted() {
		return (restriction != null) && restriction.contains("2");
	}
	
	public String getInvestorAccount() {
		return investorAccount;
	}
	
	public void setInvestorAccount(String investorAccount) {
		this.investorAccount = investorAccount;
	}
	
	public String getProductAccount() {
		return productAccount;
	}
	
	public void setProductAccount(String productAccount) {
		this.productAccount = productAccount;
	}
	
	public String getAssetAccount() {
		return assetAccount;
	}
	
	public void setAssetAccount(String assetAccount) {
		this.assetAccount = assetAccount;
	}
	
	public AssetAccountType getAssetAccountType() {
		return assetAccountType;
	}
	
	public void setAssetAccountType(AssetAccountType assetAccountType) {
		this.assetAccountType = assetAccountType;
	}
	
	public String getTradingAccount() {
		return tradingAccount;
	}
	
	public void setTradingAccount(String tradingAccount) {
		this.tradingAccount = tradingAccount;
	}
	
	public String getClientPermissions() {
		return clientPermissions;
	}
	
	public void setClientPermissions(String clientPermissions) {
		this.clientPermissions = clientPermissions;
	}
	
	public String getAllowedEntrusts() {
		return allowedEntrusts;
	}
	
	public void setAllowedEntrusts(String allowedEntrusts) {
		this.allowedEntrusts = allowedEntrusts;
	}
	
	public String getRestriction() {
		return restriction;
	}
	
	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}
	
	public SecurityAccountEntity getSecurityAccountEntity(String securityAccount) {
		return securityAccountEntities.get(securityAccount);
	}
	
	public void addSecurityAccountEntity(SecurityAccountEntity securityAccountEntity) {
		securityAccountEntities.put(securityAccountEntity.getSecurityAccount(), securityAccountEntity);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradingAccountEntity [investorAccount=");
		builder.append(investorAccount);
		builder.append(", productAccount=");
		builder.append(productAccount);
		builder.append(", assetAccount=");
		builder.append(assetAccount);
		builder.append(", assetAccountType=");
		builder.append(assetAccountType);
		builder.append(", tradingAccount=");
		builder.append(tradingAccount);
		builder.append(", securityAccountEntities=");
		builder.append(securityAccountEntities);
		builder.append(", clientPermissions=");
		builder.append(clientPermissions);
		builder.append(", allowedEntrusts=");
		builder.append(allowedEntrusts);
		builder.append(", restriction=");
		builder.append(restriction);
		builder.append("]");
		return builder.toString();
	}
	
}
