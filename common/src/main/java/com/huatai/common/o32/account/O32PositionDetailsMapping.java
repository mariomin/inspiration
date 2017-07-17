package com.huatai.common.o32.account;

public class O32PositionDetailsMapping {
	private double currentAmount;
	private double enableAmount;
	private double avgCost;
	private String accountCode;
	private String assetNo;
	private String combiNo;
	private String stockCode;
	private String marketNo;
	private String stockHolderId;
	
	public String getStockHolderId() {
		return stockHolderId;
	}
	
	public void setStockHolderId(String stockHolderId) {
		this.stockHolderId = stockHolderId;
	}
	
	public double getCurrentAmount() {
		return currentAmount;
	}
	
	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}
	
	public double getEnableAmount() {
		return enableAmount;
	}
	
	public void setEnableAmount(double enableAmount) {
		this.enableAmount = enableAmount;
	}
	
	public double getAvgCost() {
		return avgCost;
	}
	
	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}
	
	public String getAccountCode() {
		return accountCode;
	}
	
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	public String getAssetNo() {
		return assetNo;
	}
	
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	
	public String getCombiNo() {
		return combiNo;
	}
	
	public void setCombiNo(String combiNo) {
		this.combiNo = combiNo;
	}
	
	public String getStockCode() {
		return stockCode;
	}
	
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	
	public String getMarketNo() {
		return marketNo;
	}
	
	public void setMarketNo(String marketNo) {
		this.marketNo = marketNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("O32PositionDetailsMapping [currentAmount=");
		builder.append(currentAmount);
		builder.append(", enableAmount=");
		builder.append(enableAmount);
		builder.append(", avgCost=");
		builder.append(avgCost);
		builder.append(", accountCode=");
		builder.append(accountCode);
		builder.append(", assetNo=");
		builder.append(assetNo);
		builder.append(", combiNo=");
		builder.append(combiNo);
		builder.append(", stockCode=");
		builder.append(stockCode);
		builder.append(", marketNo=");
		builder.append(marketNo);
		builder.append(", stockHolderId=");
		builder.append(stockHolderId);
		builder.append("]");
		return builder.toString();
	}
	
}
