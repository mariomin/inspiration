package com.huatai.common.backtest.report;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class TradeRecord {
	@SerializedName("CreateDate")
	private Date createDate;
	
	@SerializedName("FilledDate")
	private Date filledDate;
	
	@SerializedName("Symbol")
	private String symbol;
	
	@SerializedName("Side")
	private String side;
	
	@SerializedName("Quantity")
	private double quantity;
	
	@SerializedName("Price")
	private double price;
	
	@SerializedName("CommissionCost")
	private double commisionCost;
	
	@SerializedName("StampDutyCost")
	private double stampDutyCost;
	
	public TradeRecord(Date createDate, Date filledDate, String symbol, String side, Double quantity, Double price, Double commisionCost,
			Double stampDutyCost) {
		this.createDate = createDate;
		this.filledDate = filledDate;
		this.symbol = symbol;
		this.side = side;
		this.quantity = quantity != null ? quantity : 0;
		this.price = price != null ? price : 0;
		this.commisionCost = commisionCost != null ? commisionCost : 0;
		this.stampDutyCost = stampDutyCost != null ? stampDutyCost : 0;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getFilledDate() {
		return filledDate;
	}
	
	public void setFilledDate(Date filledDate) {
		this.filledDate = filledDate;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSide() {
		return side;
	}
	
	public void setSide(String side) {
		this.side = side;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getCommisionCost() {
		return commisionCost;
	}
	
	public void setCommisionCost(double commisionCost) {
		this.commisionCost = commisionCost;
	}
	
	public double getStampDutyCost() {
		return stampDutyCost;
	}
	
	public void setStampDutyCost(double stampDutyCost) {
		this.stampDutyCost = stampDutyCost;
	}
	
}
