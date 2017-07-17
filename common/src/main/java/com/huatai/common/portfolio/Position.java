package com.huatai.common.portfolio;

import com.huatai.common.data.SingleFinancialObject;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.PositionIndex;

public class Position extends SingleFinancialObject {
	private static final long serialVersionUID = 1L;
	private String securityAccount;
	
	private double tradableQty;
	private double avgCost;
	private double holdingQty;
	private double freezedQty;
	
	private double yesterdaySettledBoughtAmount;
	private double yesterdaySettledBoughtQty;
	private double yesterdaySettledSoldAmount;
	private double yesterdaySettledSoldQty;
	
	private double intradayBoughtAmount;
	private double intradayBoughtQty;
	private double intradaySoldAmount;
	private double intradaySoldQty;
	
	public Position() {
		super();
	}
	
	public Position(AssetAccountIndex assetAccountIndex, String securityAccount, String symbol) {
		super(assetAccountIndex);
		this.securityAccount = securityAccount;
		this.symbol = symbol;
	}
	
	public PositionIndex extractPositionIndex() {
		return new PositionIndex(new AssetAccountIndex(investorAccount, productAccount, assetAccount, assetAccountType), securityAccount, symbol);
	}
	
	public void updateTradableQty() {
		tradableQty = holdingQty - freezedQty;
	}
	
	public String getSecurityAccount() {
		return securityAccount;
	}
	
	public void setSecurityAccount(String securityAccount) {
		this.securityAccount = securityAccount;
	}
	
	public double getTradableQty() {
		return tradableQty;
	}
	
	public void setTradableQty(double tradableQty) {
		this.tradableQty = tradableQty;
	}
	
	public double getAvgCost() {
		return avgCost;
	}
	
	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}
	
	public double getHoldingQty() {
		return holdingQty;
	}
	
	public void setHoldingQty(double holdingQty) {
		this.holdingQty = holdingQty;
	}
	
	public double getFreezedQty() {
		return freezedQty;
	}
	
	public void setFreezedQty(double freezedQty) {
		this.freezedQty = freezedQty;
	}
	
	public double getYesterdaySettledBoughtAmount() {
		return yesterdaySettledBoughtAmount;
	}
	
	public void setYesterdaySettledBoughtAmount(double yesterdaySettledBoughtAmount) {
		this.yesterdaySettledBoughtAmount = yesterdaySettledBoughtAmount;
	}
	
	public double getYesterdaySettledBoughtQty() {
		return yesterdaySettledBoughtQty;
	}
	
	public void setYesterdaySettledBoughtQty(double yesterdaySettledBoughtQty) {
		this.yesterdaySettledBoughtQty = yesterdaySettledBoughtQty;
	}
	
	public double getYesterdaySettledSoldAmount() {
		return yesterdaySettledSoldAmount;
	}
	
	public void setYesterdaySettledSoldAmount(double yesterdaySettledSoldAmount) {
		this.yesterdaySettledSoldAmount = yesterdaySettledSoldAmount;
	}
	
	public double getYesterdaySettledSoldQty() {
		return yesterdaySettledSoldQty;
	}
	
	public void setYesterdaySettledSoldQty(double yesterdaySettledSoldQty) {
		this.yesterdaySettledSoldQty = yesterdaySettledSoldQty;
	}
	
	public double getIntradayBoughtAmount() {
		return intradayBoughtAmount;
	}
	
	public void setIntradayBoughtAmount(double intradayBoughtAmount) {
		this.intradayBoughtAmount = intradayBoughtAmount;
	}
	
	public double getIntradayBoughtQty() {
		return intradayBoughtQty;
	}
	
	public void setIntradayBoughtQty(double intradayBoughtQty) {
		this.intradayBoughtQty = intradayBoughtQty;
	}
	
	public double getIntradaySoldAmount() {
		return intradaySoldAmount;
	}
	
	public void setIntradaySoldAmount(double intradaySoldAmount) {
		this.intradaySoldAmount = intradaySoldAmount;
	}
	
	public double getIntradaySoldQty() {
		return intradaySoldQty;
	}
	
	public void setIntradaySoldQty(double intradaySoldQty) {
		this.intradaySoldQty = intradaySoldQty;
	}
	
}
