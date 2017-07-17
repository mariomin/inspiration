package com.huatai.common.index;

import java.io.Serializable;

import com.huatai.common.type.AssetAccountType;

public class AssetAccountIndex implements Serializable {
	private static final long serialVersionUID = 1L;
	private String investorAccount;
	private String productAccount;
	private String assetAccount;
	private AssetAccountType assetAccountType;
	
	public AssetAccountIndex() {}
	
	public AssetAccountIndex(AssetAccountIndex assetAccountIndex) {
		investorAccount = assetAccountIndex.getInvestorAccount();
		productAccount = assetAccountIndex.getProductAccount();
		assetAccount = assetAccountIndex.getAssetAccount();
		assetAccountType = assetAccountIndex.getAssetAccountType();
	}
	
	public AssetAccountIndex(String investorAccount, String productAccount, String assetAccount, AssetAccountType assetAccountType) {
		this.investorAccount = investorAccount;
		this.productAccount = productAccount;
		this.assetAccount = assetAccount;
		this.assetAccountType = assetAccountType;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((assetAccount == null) ? 0 : assetAccount.hashCode());
		result = (prime * result) + ((assetAccountType == null) ? 0 : assetAccountType.hashCode());
		result = (prime * result) + ((investorAccount == null) ? 0 : investorAccount.hashCode());
		result = (prime * result) + ((productAccount == null) ? 0 : productAccount.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AssetAccountIndex other = (AssetAccountIndex) obj;
		if (assetAccount == null) {
			if (other.assetAccount != null) return false;
		} else if (!assetAccount.equals(other.assetAccount)) return false;
		if (assetAccountType != other.assetAccountType) return false;
		if (investorAccount == null) {
			if (other.investorAccount != null) return false;
		} else if (!investorAccount.equals(other.investorAccount)) return false;
		if (productAccount == null) {
			if (other.productAccount != null) return false;
		} else if (!productAccount.equals(other.productAccount)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssetAccountIndex [investorAccount=");
		builder.append(investorAccount);
		builder.append(", productAccount=");
		builder.append(productAccount);
		builder.append(", assetAccount=");
		builder.append(assetAccount);
		builder.append(", assetAccountType=");
		builder.append(assetAccountType);
		builder.append("]");
		return builder.toString();
	}
	
}
