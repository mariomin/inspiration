package com.huatai.common.account;

import java.util.ArrayList;
import java.util.Collection;

public class InvestorAccountEntity extends BaseAccountObject {
	private static final long serialVersionUID = 1L;
	private String investorAccount;
	private final Collection<ProductAccountEntity> productAccountEntities = new ArrayList<ProductAccountEntity>();
	
	public String getInvestorAccount() {
		return investorAccount;
	}
	
	public void setInvestorAccount(String investorAccount) {
		this.investorAccount = investorAccount;
	}
	
	public void addProductAccountEntity(ProductAccountEntity productAccountEntity) {
		productAccountEntities.add(productAccountEntity);
	}
	
	public Collection<ProductAccountEntity> getProductAccountEntities() {
		return productAccountEntities;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvestorAccountEntity [investorAccount=");
		builder.append(investorAccount);
		builder.append(", productAccountEntities=");
		builder.append(productAccountEntities);
		builder.append("]");
		return builder.toString();
	}
	
}
