package com.huatai.common.account;

import java.util.ArrayList;
import java.util.Collection;

public class ProductAccountEntity extends BaseAccountObject {
	private static final long serialVersionUID = 1L;
	private String investorAccount;
	private String productAccount;
	private final Collection<AssetAccountEntity> assetAccountEntities = new ArrayList<AssetAccountEntity>();
	
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
	
	public Collection<AssetAccountEntity> getAssetAccountEntities() {
		return assetAccountEntities;
	}
	
	public void addAssetAccountEntity(AssetAccountEntity assetAccountEntity) {
		assetAccountEntities.add(assetAccountEntity);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductAccountEntity [investorAccount=");
		builder.append(investorAccount);
		builder.append(", productAccount=");
		builder.append(productAccount);
		builder.append(", assetAccountEntities=");
		builder.append(assetAccountEntities);
		builder.append("]");
		return builder.toString();
	}
	
}
