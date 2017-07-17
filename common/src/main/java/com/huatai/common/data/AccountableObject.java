package com.huatai.common.data;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.type.AssetAccountType;

public abstract class AccountableObject extends IdentifiableObject {
	private static final long serialVersionUID = 1L;
	protected String investorAccount;
	protected String productAccount;
	protected String assetAccount;
	protected AssetAccountType assetAccountType;
	
	protected AccountableObject() {
		super();
	}
	
	protected AccountableObject(AssetAccountIndex assetAccountIndex) {
		super();
		investorAccount = assetAccountIndex.getInvestorAccount();
		productAccount = assetAccountIndex.getProductAccount();
		assetAccount = assetAccountIndex.getAssetAccount();
		assetAccountType = assetAccountIndex.getAssetAccountType();
	}
	
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
	
}
