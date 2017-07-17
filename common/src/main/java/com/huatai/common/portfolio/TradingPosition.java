package com.huatai.common.portfolio;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.index.TradingPositionIndex;

public class TradingPosition extends Position {
	private static final long serialVersionUID = 1L;
	private String tradingAccount;
	
	public TradingPosition() {}
	
	public TradingPosition(AssetAccountIndex assetAccountIndex, String tradingAccount, String securityAccount, String symbol) {
		super(assetAccountIndex, securityAccount, symbol);
		this.tradingAccount = tradingAccount;
	}
	
	public TradingPositionIndex extractTradingPositionIndex() {
		return new TradingPositionIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), tradingAccount,
				symbol, getSecurityAccount());
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
