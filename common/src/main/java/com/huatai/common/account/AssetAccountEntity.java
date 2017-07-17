package com.huatai.common.account;

import java.util.ArrayList;
import java.util.Collection;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.type.AssetAccountType;

public class AssetAccountEntity extends BaseAccountObject {
	private static final long serialVersionUID = 1L;
	private String investorAccount;
	private String productAccount;
	private String assetAccount;
	private AssetAccountType assetAccountType;
	private final Collection<TradingAccountEntity> tradingAccountEntities = new ArrayList<TradingAccountEntity>();
	private boolean shared;
	
	public AssetAccountIndex extractAssetAccountIndex() {
		return new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType);
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
	
	public boolean isShared() {
		return shared;
	}
	
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	
	public Collection<TradingAccountEntity> getTradingAccountEntities() {
		return tradingAccountEntities;
	}
	
	public void addTradingAccountEntity(TradingAccountEntity tradingAccountEntity) {
		tradingAccountEntities.add(tradingAccountEntity);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssetAccountEntity [investorAccount=");
		builder.append(investorAccount);
		builder.append(", productAccount=");
		builder.append(productAccount);
		builder.append(", assetAccount=");
		builder.append(assetAccount);
		builder.append(", assetAccountType=");
		builder.append(assetAccountType);
		builder.append(", tradingAccountEntities=");
		builder.append(tradingAccountEntities);
		builder.append(", shared=");
		builder.append(shared);
		builder.append("]");
		return builder.toString();
	}
	
}
