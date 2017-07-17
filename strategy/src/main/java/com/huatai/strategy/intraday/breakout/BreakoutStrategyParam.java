package com.huatai.strategy.intraday.breakout;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.huatai.strategy.intraday.common.PortfolioRecord;

public class BreakoutStrategyParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Universe")
	private List<PortfolioRecord> universe;
	
	@SerializedName("Breakout_PressureRatePeriod")
	private int pressureRatePeriod;
	
	@SerializedName("Breakout_BoundaryPointN1")
	private int boundaryPointN1;
	
	@SerializedName("Breakout_BoundaryPointN2")
	private int boundaryPointN2;
	
	@SerializedName("Breakout_BreakPointR")
	private double breakPointR;
	
	@SerializedName("Breakout_BreakPointP")
	private double breakPointP;
	
	@SerializedName("Breakout_WaveNum")
	private int waveNum;
	
	@SerializedName("Breakout_LowBoundary1")
	private double lowBoundary1;
	
	@SerializedName("Breakout_QtyStandardThreshold")
	private double qtyStandardThreshold;
	
	@SerializedName("Breakout_ParaRate")
	private double paraRate;
	
	@SerializedName("Breakout_MinPressureRate")
	private double minPressureRate;
	
	@SerializedName("Breakout_AskValueThreshold")
	private double askValueThreshold;
	
	@SerializedName("Breakout_LowBoundary2")
	private double lowBoundary2;
	
	@SerializedName("Breakout_PressureUpdatePara")
	private double pressureUpdatePara;
	
	@SerializedName("Breakout_SignalParaP1")
	private int signalParaP1;
	
	@SerializedName("Breakout_SignalParaP2")
	private double signalParaP2;
	
	@SerializedName("Breakout_SignalParaP3")
	private int signalParaP3;
	
	@SerializedName("Breakout_SignalParaP4")
	private double signalParaP4;
	
	@SerializedName("Breakout_SignalParaP5")
	private double signalParaP5;
	
	@SerializedName("Breakout_SignalParaP6")
	private double signalParaP6;
	
	@SerializedName("Breakout_SignalParaP7")
	private double signalParaP7;
	
	@SerializedName("Breakout_SignalParaP8")
	private double signalParaP8;
	
	@SerializedName("Breakout_SignalParaP9")
	private double signalParaP9;
	
	@SerializedName("Breakout_SignalParaP10")
	private double signalParaP10;
	
	@SerializedName("Breakout_AvgPriceLag")
	private int avgPriceLag;
	
	@SerializedName("Breakout_EMAPressureRatioLag")
	private int emaPressureRatioLag;
	
	@SerializedName("Breakout_CloseMaxDrop")
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
	
	public int getPressureRatePeriod() {
		return pressureRatePeriod;
	}
	
	public void setPressureRatePeriod(int pressureRatePeriod) {
		this.pressureRatePeriod = pressureRatePeriod;
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
	
	public double getBreakPointR() {
		return breakPointR;
	}
	
	public void setBreakPointR(double breakPointR) {
		this.breakPointR = breakPointR;
	}
	
	public double getBreakPointP() {
		return breakPointP;
	}
	
	public void setBreakPointP(double breakPointP) {
		this.breakPointP = breakPointP;
	}
	
	public int getWaveNum() {
		return waveNum;
	}
	
	public void setWaveNum(int waveNum) {
		this.waveNum = waveNum;
	}
	
	public double getLowBoundary1() {
		return lowBoundary1;
	}
	
	public void setLowBoundary1(double lowBoundary1) {
		this.lowBoundary1 = lowBoundary1;
	}
	
	public double getQtyStandardThreshold() {
		return qtyStandardThreshold;
	}
	
	public void setQtyStandardThreshold(double qtyStandardThreshold) {
		this.qtyStandardThreshold = qtyStandardThreshold;
	}
	
	public double getParaRate() {
		return paraRate;
	}
	
	public void setParaRate(double paraRate) {
		this.paraRate = paraRate;
	}
	
	public double getMinPressureRate() {
		return minPressureRate;
	}
	
	public void setMinPressureRate(double minPressureRate) {
		this.minPressureRate = minPressureRate;
	}
	
	public double getAskValueThreshold() {
		return askValueThreshold;
	}
	
	public void setAskValueThreshold(double askValueThreshold) {
		this.askValueThreshold = askValueThreshold;
	}
	
	public double getLowBoundary2() {
		return lowBoundary2;
	}
	
	public void setLowBoundary2(double lowBoundary2) {
		this.lowBoundary2 = lowBoundary2;
	}
	
	public double getPressureUpdatePara() {
		return pressureUpdatePara;
	}
	
	public void setPressureUpdatePara(double pressureUpdatePara) {
		this.pressureUpdatePara = pressureUpdatePara;
	}
	
	public int getSignalParaP1() {
		return signalParaP1;
	}
	
	public void setSignalParaP1(int signalParaP1) {
		this.signalParaP1 = signalParaP1;
	}
	
	public double getSignalParaP2() {
		return signalParaP2;
	}
	
	public void setSignalParaP2(double signalParaP2) {
		this.signalParaP2 = signalParaP2;
	}
	
	public int getSignalParaP3() {
		return signalParaP3;
	}
	
	public void setSignalParaP3(int signalParaP3) {
		this.signalParaP3 = signalParaP3;
	}
	
	public double getSignalParaP4() {
		return signalParaP4;
	}
	
	public void setSignalParaP4(double signalParaP4) {
		this.signalParaP4 = signalParaP4;
	}
	
	public double getSignalParaP5() {
		return signalParaP5;
	}
	
	public void setSignalParaP5(double signalParaP5) {
		this.signalParaP5 = signalParaP5;
	}
	
	public double getSignalParaP6() {
		return signalParaP6;
	}
	
	public void setSignalParaP6(double signalParaP6) {
		this.signalParaP6 = signalParaP6;
	}
	
	public double getSignalParaP7() {
		return signalParaP7;
	}
	
	public void setSignalParaP7(double signalParaP7) {
		this.signalParaP7 = signalParaP7;
	}
	
	public double getSignalParaP8() {
		return signalParaP8;
	}
	
	public void setSignalParaP8(double signalParaP8) {
		this.signalParaP8 = signalParaP8;
	}
	
	public double getSignalParaP9() {
		return signalParaP9;
	}
	
	public void setSignalParaP9(double signalParaP9) {
		this.signalParaP9 = signalParaP9;
	}
	
	public double getSignalParaP10() {
		return signalParaP10;
	}
	
	public void setSignalParaP10(double signalParaP10) {
		this.signalParaP10 = signalParaP10;
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
	
	public int getEmaPressureRatioLag() {
		return emaPressureRatioLag;
	}
	
	public void setEmaPressureRatioLag(int emaPressureRatioLag) {
		this.emaPressureRatioLag = emaPressureRatioLag;
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
		builder.append("BreakoutStrategyParam [universe=");
		builder.append(universe);
		builder.append(", pressureRatePeriod=");
		builder.append(pressureRatePeriod);
		builder.append(", boundaryPointN1=");
		builder.append(boundaryPointN1);
		builder.append(", boundaryPointN2=");
		builder.append(boundaryPointN2);
		builder.append(", breakPointR=");
		builder.append(breakPointR);
		builder.append(", breakPointP=");
		builder.append(breakPointP);
		builder.append(", waveNum=");
		builder.append(waveNum);
		builder.append(", lowBoundary1=");
		builder.append(lowBoundary1);
		builder.append(", qtyStandardThreshold=");
		builder.append(qtyStandardThreshold);
		builder.append(", paraRate=");
		builder.append(paraRate);
		builder.append(", minPressureRate=");
		builder.append(minPressureRate);
		builder.append(", askValueThreshold=");
		builder.append(askValueThreshold);
		builder.append(", lowBoundary2=");
		builder.append(lowBoundary2);
		builder.append(", pressureUpdatePara=");
		builder.append(pressureUpdatePara);
		builder.append(", signalParaP1=");
		builder.append(signalParaP1);
		builder.append(", signalParaP2=");
		builder.append(signalParaP2);
		builder.append(", signalParaP3=");
		builder.append(signalParaP3);
		builder.append(", signalParaP4=");
		builder.append(signalParaP4);
		builder.append(", signalParaP5=");
		builder.append(signalParaP5);
		builder.append(", signalParaP6=");
		builder.append(signalParaP6);
		builder.append(", signalParaP7=");
		builder.append(signalParaP7);
		builder.append(", signalParaP8=");
		builder.append(signalParaP8);
		builder.append(", signalParaP9=");
		builder.append(signalParaP9);
		builder.append(", signalParaP10=");
		builder.append(signalParaP10);
		builder.append(", avgPriceLag=");
		builder.append(avgPriceLag);
		builder.append(", emaPressureRatioLag=");
		builder.append(emaPressureRatioLag);
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
		builder.append(", closeOpenParaP1=");
		builder.append(closeOpenParaP1);
		builder.append(", closeOpenParaP2=");
		builder.append(closeOpenParaP2);
		builder.append(", closeOpenParaP3=");
		builder.append(closeOpenParaP3);
		builder.append(", closeOpenParaP4=");
		builder.append(closeOpenParaP4);
		builder.append(", closeOpenParaP5=");
		builder.append(closeOpenParaP5);
		builder.append(", autoTrade=");
		builder.append(autoTrade);
		builder.append("]");
		return builder.toString();
	}
	
}
