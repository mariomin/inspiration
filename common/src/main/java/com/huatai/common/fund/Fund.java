package com.huatai.common.fund;

import com.huatai.common.data.AccountableObject;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.FundIndex;
import com.huatai.common.type.Currency;

public class Fund extends AccountableObject {
	private static final long serialVersionUID = 1L;
	private Currency currency;
	
	private double yesterdaySettledCash;
	private double tradableCash;
	private double freezedCash;
	private double holdingCash;
	
	private double cumBuyTradeAmount;
	private double cumSellTradeAmount;
	
	public Fund() {
		super();
	}
	
	public Fund(AssetAccountIndex assetAccountIndex, Currency currency) {
		super(assetAccountIndex);
		this.currency = currency;
	}
	
	public FundIndex extractFundIndex() {
		return new FundIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), currency);
	}
	
	public void updateTradableCash() {
		tradableCash = (holdingCash - freezedCash) + cumSellTradeAmount;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public double getYesterdaySettledCash() {
		return yesterdaySettledCash;
	}
	
	public void setYesterdaySettledCash(double yesterdaySettledCash) {
		this.yesterdaySettledCash = yesterdaySettledCash;
	}
	
	public double getTradableCash() {
		return tradableCash;
	}
	
	public void setTradableCash(double tradableCash) {
		this.tradableCash = tradableCash;
	}
	
	public double getFreezedCash() {
		return freezedCash;
	}
	
	public void setFreezedCash(double freezedCash) {
		this.freezedCash = freezedCash;
	}
	
	public double getHoldingCash() {
		return holdingCash;
	}
	
	public void setHoldingCash(double holdingCash) {
		this.holdingCash = holdingCash;
	}
	
	public double getCumBuyTradeAmount() {
		return cumBuyTradeAmount;
	}
	
	public void setCumBuyTradeAmount(double cumBuyTradeAmount) {
		this.cumBuyTradeAmount = cumBuyTradeAmount;
	}
	
	public double getCumSellTradeAmount() {
		return cumSellTradeAmount;
	}
	
	public void setCumSellTradeAmount(double cumSellTradeAmount) {
		this.cumSellTradeAmount = cumSellTradeAmount;
	}
	
}
