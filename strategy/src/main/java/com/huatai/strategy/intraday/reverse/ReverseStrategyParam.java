package com.huatai.strategy.intraday.reverse;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.huatai.strategy.intraday.common.PortfolioRecord;

public class ReverseStrategyParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Universe")
	private List<PortfolioRecord> universe;
	
	@SerializedName("Reverse_BoundaryPointN1")
	private int boundaryPointN1;
	
	@SerializedName("Reverse_BoundaryPointN2")
	private int boundaryPointN2;
	
	@SerializedName("Reverse_WaveNum")
	private int waveNum;
	
	@SerializedName("Reverse_PressureRatePeriod")
	private int pressureRatePeriod;
	
	@SerializedName("Reverse_OrderPressureN")
	private int orderPressureN;
	
	@SerializedName("Reverse_OrderPressureLag")
	private int orderPressureLag;
	
	@SerializedName("Reverse_IndexCode")
	private String indexCode;
	
	@SerializedName("Reverse_ParaFast")
	private int paraFast;
	
	@SerializedName("Reverse_ParaSlow")
	private int paraSlow;
	
	@SerializedName("Reverse_SpeedLag")
	private int speedLag;
	
	@SerializedName("Reverse_IndexDropSpeedRate")
	private double indexDropSpeedRate;
	
	@SerializedName("Reverse_VolumePeriod")
	private int volumePeriod;
	
	@SerializedName("Reverse_AvgPriceLag")
	private int avgPriceLag;
	
	@SerializedName("Reverse_EMAPressureRatioLag")
	private int emaPressureRatioLag;
	
	@SerializedName("Reverse_EMAOrderPressureTriggerRate")
	private double emaOrderPressureTriggerRate;
	
	@SerializedName("Reverse_TriggerPressure")
	private double triggerPressure;
	
	@SerializedName("Reverse_MinSupportAmount")
	private double minSupportAmount;
	
	@SerializedName("Reverse_MinSupportRate")
	private double minSupportRate;
	
	@SerializedName("Reverse_MinPierceNum")
	private double minPierceNum;
	
	@SerializedName("Reverse_DyRatio")
	private double dyRatio;
	
	@SerializedName("Reverse_DyRatio2")
	private double dyRatio2;
	
	@SerializedName("Reverse_DyAmountRatio")
	private double dyAmountRatio;
	
	@SerializedName("Reverse_DropRate")
	private double dropRate;
	
	@SerializedName("Reverse_VolumeRate")
	private double volumeRate;
	
	@SerializedName("Reverse_SpeedRate")
	private double speedRate;
	
	@SerializedName("Reverse_RevLag")
	private int revLag;
	
	@SerializedName("Reverse_MinPrice")
	private double minPrice;
	
	@SerializedName("Reverse_CloseMaxDrop")
	private double closeMaxDrop;
	
	@SerializedName("BuyLevel")
	private int buyLevel;
	
	@SerializedName("BuyDeviation")
	private double buyDeviation;
	
	@SerializedName("SellLevel")
	private int sellLevel;
	
	@SerializedName("SellDeviation")
	private double sellDeviation;
	
	@SerializedName("CloseOpenParaP1")
	private double closeOpenParaP1;
	
	@SerializedName("CloseOpenParaP2")
	private double closeOpenParaP2;
	
	@SerializedName("CloseOpenParaP3")
	private double closeOpenParaP3;
	
	@SerializedName("CloseOpenParaP4")
	private int closeOpenParaP4;
	
	@SerializedName("CloseOpenParaP5")
	private int closeOpenParaP5;
	
	@SerializedName("AutoTrade")
	private boolean autoTrade = true;
	
	public List<PortfolioRecord> getUniverse() {
		return universe;
	}
	
	public void setUniverse(List<PortfolioRecord> universe) {
		this.universe = universe;
	}
	
	public int getBoundaryPointN1() {
		return boundaryPointN1;
	}
	
	public void setBoundaryPointN1(int boundaryPointN1) {
		this.boundaryPointN1 = boundaryPointN1;
	}
	
	public int getBoundaryPointN2() {
		return boundaryPointN2;
	}
	
	public void setBoundaryPointN2(int boundaryPointN2) {
		this.boundaryPointN2 = boundaryPointN2;
	}
	
	public int getWaveNum() {
		return waveNum;
	}
	
	public void setWaveNum(int waveNum) {
		this.waveNum = waveNum;
	}
	
	public int getPressureRatePeriod() {
		return pressureRatePeriod;
	}
	
	public void setPressureRatePeriod(int pressureRatePeriod) {
		this.pressureRatePeriod = pressureRatePeriod;
	}
	
	public int getOrderPressureN() {
		return orderPressureN;
	}
	
	public void setOrderPressureN(int orderPressureN) {
		this.orderPressureN = orderPressureN;
	}
	
	public int getOrderPressureLag() {
		return orderPressureLag;
	}
	
	public void setOrderPressureLag(int orderPressureLag) {
		this.orderPressureLag = orderPressureLag;
	}
	
	public String getIndexCode() {
		return indexCode;
	}
	
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	
	public int getParaFast() {
		return paraFast;
	}
	
	public void setParaFast(int paraFast) {
		this.paraFast = paraFast;
	}
	
	public int getParaSlow() {
		return paraSlow;
	}
	
	public void setParaSlow(int paraSlow) {
		this.paraSlow = paraSlow;
	}
	
	public int getSpeedLag() {
		return speedLag;
	}
	
	public void setSpeedLag(int speedLag) {
		this.speedLag = speedLag;
	}
	
	public double getIndexDropSpeedRate() {
		return indexDropSpeedRate;
	}
	
	public void setIndexDropSpeedRate(double indexDropSpeedRate) {
		this.indexDropSpeedRate = indexDropSpeedRate;
	}
	
	public int getVolumePeriod() {
		return volumePeriod;
	}
	
	public void setVolumePeriod(int volumePeriod) {
		this.volumePeriod = volumePeriod;
	}
	
	public double getEmaOrderPressureTriggerRate() {
		return emaOrderPressureTriggerRate;
	}
	
	public void setEmaOrderPressureTriggerRate(double emaOrderPressureTriggerRate) {
		this.emaOrderPressureTriggerRate = emaOrderPressureTriggerRate;
	}
	
	public double getTriggerPressure() {
		return triggerPressure;
	}
	
	public void setTriggerPressure(double triggerPressure) {
		this.triggerPressure = triggerPressure;
	}
	
	public double getMinSupportAmount() {
		return minSupportAmount;
	}
	
	public void setMinSupportAmount(double minSupportAmount) {
		this.minSupportAmount = minSupportAmount;
	}
	
	public double getMinSupportRate() {
		return minSupportRate;
	}
	
	public void setMinSupportRate(double minSupportRate) {
		this.minSupportRate = minSupportRate;
	}
	
	public double getMinPierceNum() {
		return minPierceNum;
	}
	
	public void setMinPierceNum(double minPierceNum) {
		this.minPierceNum = minPierceNum;
	}
	
	public double getDyRatio() {
		return dyRatio;
	}
	
	public void setDyRatio(double dyRatio) {
		this.dyRatio = dyRatio;
	}
	
	public double getDyRatio2() {
		return dyRatio2;
	}
	
	public void setDyRatio2(double dyRatio2) {
		this.dyRatio2 = dyRatio2;
	}
	
	public double getDyAmountRatio() {
		return dyAmountRatio;
	}
	
	public void setDyAmountRatio(double dyAmountRatio) {
		this.dyAmountRatio = dyAmountRatio;
	}
	
	public double getDropRate() {
		return dropRate;
	}
	
	public void setDropRate(double dropRate) {
		this.dropRate = dropRate;
	}
	
	public double getVolumeRate() {
		return volumeRate;
	}
	
	public void setVolumeRate(double volumeRate) {
		this.volumeRate = volumeRate;
	}
	
	public double getSpeedRate() {
		return speedRate;
	}
	
	public void setSpeedRate(double speedRate) {
		this.speedRate = speedRate;
	}
	
	public int getRevLag() {
		return revLag;
	}
	
	public void setRevLag(int revLag) {
		this.revLag = revLag;
	}
	
	public double getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	
	public int getEmaPressureRatioLag() {
		return emaPressureRatioLag;
	}
	
	public void setEmaPressureRatioLag(int emaPressureRatioLag) {
		this.emaPressureRatioLag = emaPressureRatioLag;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getBuyLevel() {
		return buyLevel;
	}
	
	public void setBuyLevel(int buyLevel) {
		this.buyLevel = buyLevel;
	}
	
	public double getBuyDeviation() {
		return buyDeviation;
	}
	
	public void setBuyDeviation(double buyDeviation) {
		this.buyDeviation = buyDeviation;
	}
	
	public int getSellLevel() {
		return sellLevel;
	}
	
	public void setSellLevel(int sellLevel) {
		this.sellLevel = sellLevel;
	}
	
	public double getSellDeviation() {
		return sellDeviation;
	}
	
	public void setSellDeviation(double sellDeviation) {
		this.sellDeviation = sellDeviation;
	}
	
	public double getCloseOpenParaP1() {
		return closeOpenParaP1;
	}
	
	public void setCloseOpenParaP1(double closeOpenParaP1) {
		this.closeOpenParaP1 = closeOpenParaP1;
	}
	
	public double getCloseOpenParaP2() {
		return closeOpenParaP2;
	}
	
	public void setCloseOpenParaP2(double closeOpenParaP2) {
		this.closeOpenParaP2 = closeOpenParaP2;
	}
	
	public double getCloseOpenParaP3() {
		return closeOpenParaP3;
	}
	
	public void setCloseOpenParaP3(double closeOpenParaP3) {
		this.closeOpenParaP3 = closeOpenParaP3;
	}
	
	public int getCloseOpenParaP4() {
		return closeOpenParaP4;
	}
	
	public void setCloseOpenParaP4(int closeOpenParaP4) {
		this.closeOpenParaP4 = closeOpenParaP4;
	}
	
	public int getCloseOpenParaP5() {
		return closeOpenParaP5;
	}
	
	public void setCloseOpenParaP5(int closeOpenParaP5) {
		this.closeOpenParaP5 = closeOpenParaP5;
	}
	
	public int getAvgPriceLag() {
		return avgPriceLag;
	}
	
	public void setAvgPriceLag(int avgPriceLag) {
		this.avgPriceLag = avgPriceLag;
	}
	
	public double getCloseMaxDrop() {
		return closeMaxDrop;
	}
	
	public void setCloseMaxDrop(double closeMaxDrop) {
		this.closeMaxDrop = closeMaxDrop;
	}
	
	public boolean isAutoTrade() {
		return autoTrade;
	}
	
	public void setAutoTrade(boolean autoTrade) {
		this.autoTrade = autoTrade;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReverseStrategyParam [universe=");
		builder.append(universe);
		builder.append(", boundaryPointN1=");
		builder.append(boundaryPointN1);
		builder.append(", boundaryPointN2=");
		builder.append(boundaryPointN2);
		builder.append(", waveNum=");
		builder.append(waveNum);
		builder.append(", pressureRatePeriod=");
		builder.append(pressureRatePeriod);
		builder.append(", orderPressureN=");
		builder.append(orderPressureN);
		builder.append(", orderPressureLag=");
		builder.append(orderPressureLag);
		builder.append(", indexCode=");
		builder.append(indexCode);
		builder.append(", paraFast=");
		builder.append(paraFast);
		builder.append(", paraSlow=");
		builder.append(paraSlow);
		builder.append(", speedLag=");
		builder.append(speedLag);
		builder.append(", indexDropSpeedRate=");
		builder.append(indexDropSpeedRate);
		builder.append(", volumePeriod=");
		builder.append(volumePeriod);
		builder.append(", avgPriceLag=");
		builder.append(avgPriceLag);
		builder.append(", emaPressureRatioLag=");
		builder.append(emaPressureRatioLag);
		builder.append(", emaOrderPressureTriggerRate=");
		builder.append(emaOrderPressureTriggerRate);
		builder.append(", triggerPressure=");
		builder.append(triggerPressure);
		builder.append(", minSupportAmount=");
		builder.append(minSupportAmount);
		builder.append(", minSupportRate=");
		builder.append(minSupportRate);
		builder.append(", minPierceNum=");
		builder.append(minPierceNum);
		builder.append(", dyRatio=");
		builder.append(dyRatio);
		builder.append(", dyRatio2=");
		builder.append(dyRatio2);
		builder.append(", dyAmountRatio=");
		builder.append(dyAmountRatio);
		builder.append(", dropRate=");
		builder.append(dropRate);
		builder.append(", volumeRate=");
		builder.append(volumeRate);
		builder.append(", speedRate=");
		builder.append(speedRate);
		builder.append(", revLag=");
		builder.append(revLag);
		builder.append(", minPrice=");
		builder.append(minPrice);
		builder.append(", closeMaxDrop=");
		builder.append(closeMaxDrop);
		builder.append(", buyLevel=");
		builder.append(buyLevel);
		builder.append(", buyDeviation=");
		builder.append(buyDeviation);
		builder.append(", sellLevel=");
		builder.append(sellLevel);
		builder.append(", sellDeviation=");
		builder.append(sellDeviation);
		builder.append(", closeSignalParaP1=");
		builder.append(closeOpenParaP1);
		builder.append(", closeSignalParaP2=");
		builder.append(closeOpenParaP2);
		builder.append(", closeSignalParaP3=");
		builder.append(closeOpenParaP3);
		builder.append(", closeSignalParaP4=");
		builder.append(closeOpenParaP4);
		builder.append(", closeSignalParaP5=");
		builder.append(closeOpenParaP5);
		builder.append(", autoTrade=");
		builder.append(autoTrade);
		builder.append("]");
		return builder.toString();
	}
	
}
