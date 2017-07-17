package com.huatai.strategy.intraday.breakout;

import com.google.gson.annotations.SerializedName;
import com.huatai.strategy.intraday.common.IndexRecord;

public class BreakoutOpenSignal {
	@SerializedName("BreakPrice")
	private double breakPrice;
	
	@SerializedName("PressurePrice")
	private double pressurePrice;
	
	@SerializedName("DistPriceMidToBreakPoint")
	private double distPriceMidToBreakPoint;
	
	@SerializedName("PressureVolume")
	private IndexRecord pressureVolume;
	
	@SerializedName("EmaPressureRatio")
	private IndexRecord emaPressureRatio;
	
	@SerializedName("SumStandardAskVolume")
	private IndexRecord sumStandardAskVolume;
	
	@SerializedName("EmaAccAmountBuy")
	private IndexRecord emaAccAmountBuy;
	
	@SerializedName("LowBoundaryChangePct")
	private IndexRecord lowBoundaryChangePct;
	
	@SerializedName("VolumeAmp")
	private IndexRecord volumeAmp;
	
	@SerializedName("AvgVariation")
	private IndexRecord avgVariation;
	
	@SerializedName("AvgTradeValue")
	private IndexRecord avgTradeValue;
	
	@SerializedName("AvgVariationAbs")
	private IndexRecord avgVariationAbs;
	
	@SerializedName("ChangePct")
	private IndexRecord changePct;
	
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
	
	public boolean isOpenOrderSignal() {
		return pressureVolume.getBoolValue() && emaPressureRatio.getBoolValue() && sumStandardAskVolume.getBoolValue()
				&& emaAccAmountBuy.getBoolValue() && lowBoundaryChangePct.getBoolValue() && volumeAmp.getBoolValue() && avgVariation.getBoolValue()
				&& avgTradeValue.getBoolValue() && avgVariationAbs.getBoolValue() && changePct.getBoolValue();
	}
	
	public double getBreakPrice() {
		return breakPrice;
	}
	
	public void setBreakPrice(double breakPrice) {
		this.breakPrice = breakPrice;
	}
	
	public double getPressurePrice() {
		return pressurePrice;
	}
	
	public void setPressurePrice(double pressurePrice) {
		this.pressurePrice = pressurePrice;
	}
	
	public double getDistPriceMidToBreakPoint() {
		return distPriceMidToBreakPoint;
	}
	
	public void setDistPriceMidToBreakPoint(double distPriceMidToBreakPoint) {
		this.distPriceMidToBreakPoint = distPriceMidToBreakPoint;
	}
	
	public IndexRecord getPressureVolume() {
		return pressureVolume;
	}
	
	public void setPressureVolume(IndexRecord pressureVolume) {
		this.pressureVolume = pressureVolume;
	}
	
	public IndexRecord getEmaPressureRatio() {
		return emaPressureRatio;
	}
	
	public void setEmaPressureRatio(IndexRecord emaPressureRatio) {
		this.emaPressureRatio = emaPressureRatio;
	}
	
	public IndexRecord getSumStandardAskVolume() {
		return sumStandardAskVolume;
	}
	
	public void setSumStandardAskVolume(IndexRecord sumStandardAskVolume) {
		this.sumStandardAskVolume = sumStandardAskVolume;
	}
	
	public IndexRecord getEmaAccAmountBuy() {
		return emaAccAmountBuy;
	}
	
	public void setEmaAccAmountBuy(IndexRecord emaAccAmountBuy) {
		this.emaAccAmountBuy = emaAccAmountBuy;
	}
	
	public IndexRecord getLowBoundaryChangePct() {
		return lowBoundaryChangePct;
	}
	
	public void setLowBoundaryChangePct(IndexRecord lowBoundaryChangePct) {
		this.lowBoundaryChangePct = lowBoundaryChangePct;
	}
	
	public IndexRecord getVolumeAmp() {
		return volumeAmp;
	}
	
	public void setVolumeAmp(IndexRecord volumeAmp) {
		this.volumeAmp = volumeAmp;
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
	
	public IndexRecord getChangePct() {
		return changePct;
	}
	
	public void setChangePct(IndexRecord changePct) {
		this.changePct = changePct;
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
	
	@Override
	public String toString() {
		return "DTStrategyReport [breakPrice=" + breakPrice + ", pressurePrice=" + pressurePrice + ", distPriceMidToBreakPoint="
				+ distPriceMidToBreakPoint + ", pressureVolume=" + pressureVolume + ", emaPressureRatio=" + emaPressureRatio
				+ ", sumStandardAskVolume=" + sumStandardAskVolume + ", emaAccAmountBuy=" + emaAccAmountBuy + ", lowBoundaryChangePct="
				+ lowBoundaryChangePct + ", volumeAmp=" + volumeAmp + ", avgVariation=" + avgVariation + ", avgTradeValue=" + avgTradeValue
				+ ", avgVariationAbs=" + avgVariationAbs + ", changePct=" + changePct + ", symbol=" + symbol + ", orderPrice=" + orderPrice
				+ ", orderQty=" + orderQty + "]";
	}
}
