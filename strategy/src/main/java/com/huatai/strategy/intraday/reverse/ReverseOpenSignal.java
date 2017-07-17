package com.huatai.strategy.intraday.reverse;

import com.google.gson.annotations.SerializedName;
import com.huatai.strategy.intraday.common.IndexRecord;

public class ReverseOpenSignal {
	@SerializedName("IndexStatus")
	private boolean indexStatus;
	
	@SerializedName("PierceNum")
	private IndexRecord pierceNum;
	
	@SerializedName("PriceMid")
	private IndexRecord priceMid;
	
	@SerializedName("EMAAvgPrice")
	private IndexRecord emaAvgPrice;
	
	@SerializedName("emaOrderPressure")
	private IndexRecord emaOrderPressure;
	
	@SerializedName("emaPressureRatio")
	private IndexRecord emaPressureRatio;
	
	@SerializedName("Drop")
	private IndexRecord drop;
	
	@SerializedName("VolumeAmp")
	private IndexRecord volumeAmp;
	
	@SerializedName("Speed")
	private IndexRecord speed;
	
	@SerializedName("AvgVariation")
	private IndexRecord avgVariation;
	
	@SerializedName("AvgTradeValue")
	private IndexRecord avgTradeValue;
	
	@SerializedName("AvgVariationAbs")
	private IndexRecord avgVariationAbs;
	
	@SerializedName("Symbol")
	private String symbol;
	
	@SerializedName("OrderQty")
	private double orderQty;
	
	@SerializedName("OrderPrice")
	private double orderPrice;
	
	@SerializedName("PortfolioNo")
	private String portfolioNo;
	
	@SerializedName("AutoTrade")
	private boolean autoTrade;
	
	@SerializedName("TimeStamp")
	private long timeStamp;
	
	public boolean isIndexStatus() {
		return indexStatus;
	}
	
	public void setIndexStatus(boolean indexStatus) {
		this.indexStatus = indexStatus;
	}
	
	public IndexRecord getPierceNum() {
		return pierceNum;
	}
	
	public void setPierceNum(IndexRecord pierceNum) {
		this.pierceNum = pierceNum;
	}
	
	public IndexRecord getPriceMid() {
		return priceMid;
	}
	
	public void setPriceMid(IndexRecord priceMid) {
		this.priceMid = priceMid;
	}
	
	public IndexRecord getEmaAvgPrice() {
		return emaAvgPrice;
	}
	
	public void setEmaAvgPrice(IndexRecord emaAvgPrice) {
		this.emaAvgPrice = emaAvgPrice;
	}
	
	public IndexRecord getEmaOrderPressure() {
		return emaOrderPressure;
	}
	
	public void setEmaOrderPressure(IndexRecord emaOrderPressure) {
		this.emaOrderPressure = emaOrderPressure;
	}
	
	public IndexRecord getEmaPressureRatio() {
		return emaPressureRatio;
	}
	
	public void setEmaPressureRatio(IndexRecord emaPressureRatio) {
		this.emaPressureRatio = emaPressureRatio;
	}
	
	public IndexRecord getDrop() {
		return drop;
	}
	
	public void setDrop(IndexRecord drop) {
		this.drop = drop;
	}
	
	public IndexRecord getVolumeAmp() {
		return volumeAmp;
	}
	
	public void setVolumeAmp(IndexRecord volumeAmp) {
		this.volumeAmp = volumeAmp;
	}
	
	public IndexRecord getSpeed() {
		return speed;
	}
	
	public void setSpeed(IndexRecord speed) {
		this.speed = speed;
	}
	
	public IndexRecord getAvgVariation() {
		return avgVariation;
	}
	
	public void setAvgVariation(IndexRecord avgVariation) {
		this.avgVariation = avgVariation;
	}
	
	public IndexRecord getAvgTradeValue() {
		return avgTradeValue;
	}
	
	public void setAvgTradeValue(IndexRecord avgTradeValue) {
		this.avgTradeValue = avgTradeValue;
	}
	
	public IndexRecord getAvgVariationAbs() {
		return avgVariationAbs;
	}
	
	public void setAvgVariationAbs(IndexRecord avgVariationAbs) {
		this.avgVariationAbs = avgVariationAbs;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public double getOrderQty() {
		return orderQty;
	}
	
	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}
	
	public double getOrderPrice() {
		return orderPrice;
	}
	
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	public String getPortfolioNo() {
		return portfolioNo;
	}
	
	public void setPortfolioNo(String portfolioNo) {
		this.portfolioNo = portfolioNo;
	}
	
	public boolean isAutoTrade() {
		return autoTrade;
	}
	
	public void setAutoTrade(boolean autoTrade) {
		this.autoTrade = autoTrade;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public boolean isOpenOrderSignal() {
		return indexStatus && pierceNum.getBoolValue() && priceMid.getBoolValue() && emaAvgPrice.getBoolValue() && emaOrderPressure.getBoolValue()
				&& emaPressureRatio.getBoolValue() && drop.getBoolValue() && volumeAmp.getBoolValue() && speed.getBoolValue()
				&& avgVariation.getBoolValue() && avgTradeValue.getBoolValue() && avgVariationAbs.getBoolValue();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IntradayOpenSignal [indexStatus=");
		builder.append(indexStatus);
		builder.append(", pierceNum=");
		builder.append(pierceNum);
		builder.append(", priceMid=");
		builder.append(priceMid);
		builder.append(", emaAvgPrice=");
		builder.append(emaAvgPrice);
		builder.append(", emaOrderPressure=");
		builder.append(emaOrderPressure);
		builder.append(", emaPressureRatio=");
		builder.append(emaPressureRatio);
		builder.append(", drop=");
		builder.append(drop);
		builder.append(", volumeAmp=");
		builder.append(volumeAmp);
		builder.append(", speed=");
		builder.append(speed);
		builder.append(", avgVariation=");
		builder.append(avgVariation);
		builder.append(", avgTradeValue=");
		builder.append(avgTradeValue);
		builder.append(", avgVariationAbs=");
		builder.append(avgVariationAbs);
		builder.append(", symbol=");
		builder.append(symbol);
		builder.append(", orderQty=");
		builder.append(orderQty);
		builder.append(", orderPrice=");
		builder.append(orderPrice);
		builder.append(", portfolioNo=");
		builder.append(portfolioNo);
		builder.append(", autoTrade=");
		builder.append(autoTrade);
		builder.append(", timeStamp=");
		builder.append(timeStamp);
		builder.append("]");
		return builder.toString();
	}
	
}
