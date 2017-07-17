package com.huatai.common.o32.account;

public class O32NewOrderRequest {
	private String accountCode;
	private String assetNo;
	private String combiNo;
	private String marketNo;
	private String stockCode;
	private String entrustDirection;
	private String priceType;
	private String entrustPrice;
	private String entrustAmount;
	private String userToken;
	
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
	
	public String getMarketNo() {
		return marketNo;
	}
	
	public void setMarketNo(String marketNo) {
		this.marketNo = marketNo;
	}
	
	public String getStockCode() {
		return stockCode;
	}
	
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	
	public String getEntrustDirection() {
		return entrustDirection;
	}
	
	public void setEntrustDirection(String entrustDirection) {
		this.entrustDirection = entrustDirection;
	}
	
	public String getPriceType() {
		return priceType;
	}
	
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	public String getEntrustPrice() {
		return entrustPrice;
	}
	
	public void setEntrustPrice(String entrustPrice) {
		this.entrustPrice = entrustPrice;
	}
	
	public String getEntrustAmount() {
		return entrustAmount;
	}
	
	public void setEntrustAmount(String entrustAmount) {
		this.entrustAmount = entrustAmount;
	}
	
	public String getUserToken() {
		return userToken;
	}
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("O32NewOrderRequest [accountCode=");
		builder.append(accountCode);
		builder.append(", assetNo=");
		builder.append(assetNo);
		builder.append(", combiNo=");
		builder.append(combiNo);
		builder.append(", marketNo=");
		builder.append(marketNo);
		builder.append(", stockCode=");
		builder.append(stockCode);
		builder.append(", entrustDirection=");
		builder.append(entrustDirection);
		builder.append(", priceType=");
		builder.append(priceType);
		builder.append(", entrustPrice=");
		builder.append(entrustPrice);
		builder.append(", entrustAmount=");
		builder.append(entrustAmount);
		builder.append("]");
		return builder.toString();
	}
	
}
