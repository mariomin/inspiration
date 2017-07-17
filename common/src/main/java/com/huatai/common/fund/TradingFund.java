package com.huatai.common.fund;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.index.TradingFundIndex;
import com.huatai.common.type.Currency;

public class TradingFund extends Fund {
	private static final long serialVersionUID = 1L;
	private String tradingAccount;
	
	public TradingFund() {
		super();
	}
	
	public TradingFund(TradingAccountIndex tradingAccountIndex, Currency currency) {
		super(tradingAccountIndex, currency);
		tradingAccount = tradingAccountIndex.getTradingAccount();
	}
	
	public TradingFundIndex extractTradingFundIndex() {
		return new TradingFundIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), tradingAccount,
				getCurrency());
	}
	
	public TradingAccountIndex extractTradingAccountIndex() {
		return new TradingAccountIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), tradingAccount);
	}
	
	public String getTradingAccount() {
		return tradingAccount;
	}
	
	public void setTradingAccount(String tradingAccount) {
		this.tradingAccount = tradingAccount;
	}
	
}
