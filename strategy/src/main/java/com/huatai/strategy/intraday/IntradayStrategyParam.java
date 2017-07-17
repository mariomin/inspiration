package com.huatai.strategy.intraday;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.huatai.strategy.intraday.common.PortfolioRecord;

public class IntradayStrategyParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Universe")
	private List<PortfolioRecord> universe;
	
	@SerializedName("Breakout_PressureRatePeriod")
	private int breakout_pressureRatePeriod;
	
	@SerializedName("Breakout_BoundaryPointN1")
	private int breakout_boundaryPointN1;
	
	@SerializedName("Breakout_BoundaryPointN2")
	private int breakout_boundaryPointN2;
	
	@SerializedName("Breakout_BreakPointR")
	private double breakout_breakPointR;
	
	@SerializedName("Breakout_BreakPointP")
	private double breakout_breakPointP;
	
	@SerializedName("Breakout_WaveNum")
	private int breakout_waveNum;
	
	@SerializedName("Breakout_LowBoundary1")
	private double breakout_lowBoundary1;
	
	@SerializedName("Breakout_QtyStandardThreshold")
	private double breakout_qtyStandardThreshold;
	
	@SerializedName("Breakout_ParaRate")
	private double breakout_paraRate;
	
	@SerializedName("Breakout_MinPressureRate")
	private double breakout_minPressureRate;
	
	@SerializedName("Breakout_AskValueThreshold")
	private double breakout_askValueThreshold;
	
	@SerializedName("Breakout_LowBoundary2")
	private double breakout_lowBoundary2;
	
	@SerializedName("Breakout_PressureUpdatePara")
	private double breakout_pressureUpdatePara;
	
	@SerializedName("Breakout_SignalParaP1")
	private int breakout_signalParaP1;
	
	@SerializedName("Breakout_SignalParaP2")
	private double breakout_signalParaP2;
	
	@SerializedName("Breakout_SignalParaP3")
	private int breakout_signalParaP3;
	
	@SerializedName("Breakout_SignalParaP4")
	private double breakout_signalParaP4;
	
	@SerializedName("Breakout_SignalParaP5")
	private double breakout_signalParaP5;
	
	@SerializedName("Breakout_SignalParaP6")
	private double breakout_signalParaP6;
	
	@SerializedName("Breakout_SignalParaP7")
	private double breakout_signalParaP7;
	
	@SerializedName("Breakout_SignalParaP8")
	private double breakout_signalParaP8;
	
	@SerializedName("Breakout_SignalParaP9")
	private double breakout_signalParaP9;
	
	@SerializedName("Breakout_SignalParaP10")
	private double breakout_signalParaP10;
	
	@SerializedName("Breakout_AvgPriceLag")
	private int breakout_avgPriceLag;
	
	@SerializedName("Breakout_EMAPressureRatioLag")
	private int breakout_emaPressureRatioLag;
	
	@SerializedName("Breakout_CloseMaxDrop")
	private double breakout_closeMaxDrop;
	
	@SerializedName("Reverse_BoundaryPointN1")
	private int reverse_boundaryPointN1;
	
	@SerializedName("Reverse_BoundaryPointN2")
	private int reverse_boundaryPointN2;
	
	@SerializedName("Reverse_WaveNum")
	private int reverse_waveNum;
	
	@SerializedName("Reverse_PressureRatePeriod")
	private int reverse_pressureRatePeriod;
	
	@SerializedName("Reverse_OrderPressureN")
	private int reverse_orderPressureN;
	
	@SerializedName("Reverse_OrderPressureLag")
	private int reverse_orderPressureLag;
	
	@SerializedName("Reverse_IndexCode")
	private String reverse_indexCode;
	
	@SerializedName("Reverse_ParaFast")
	private int reverse_paraFast;
	
	@SerializedName("Reverse_ParaSlow")
	private int reverse_paraSlow;
	
	@SerializedName("Reverse_SpeedLag")
	private int reverse_speedLag;
	
	@SerializedName("Reverse_IndexDropSpeedRate")
	private double reverse_indexDropSpeedRate;
	
	@SerializedName("Reverse_VolumePeriod")
	private int reverse_volumePeriod;
	
	@SerializedName("Reverse_AvgPriceLag")
	private int reverse_avgPriceLag;
	
	@SerializedName("Reverse_EMAPressureRatioLag")
	private int reverse_emaPressureRatioLag;
	
	@SerializedName("Reverse_EMAOrderPressureTriggerRate")
	private double reverse_emaOrderPressureTriggerRate;
	
	@SerializedName("Reverse_TriggerPressure")
	private double reverse_triggerPressure;
	
	@SerializedName("Reverse_MinSupportAmount")
	private double reverse_minSupportAmount;
	
	@SerializedName("Reverse_MinSupportRate")
	private double reverse_minSupportRate;
	
	@SerializedName("Reverse_MinPierceNum")
	private double reverse_minPierceNum;
	
	@SerializedName("Reverse_DyRatio")
	private double reverse_dyRatio;
	
	@SerializedName("Reverse_DyRatio2")
	private double reverse_dyRatio2;
	
	@SerializedName("Reverse_DyAmountRatio")
	private double reverse_dyAmountRatio;
	
	@SerializedName("Reverse_DropRate")
	private double reverse_dropRate;
	
	@SerializedName("Reverse_VolumeRate")
	private double reverse_volumeRate;
	
	@SerializedName("Reverse_SpeedRate")
	private double reverse_speedRate;
	
	@SerializedName("Reverse_RevLag")
	private int reverse_revLag;
	
	@SerializedName("Reverse_MinPrice")
	private double reverse_minPrice;
	
	@SerializedName("Reverse_CloseMaxDrop")
	private double reverse_closeMaxDrop;
	
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
	
	public int getBreakout_pressureRatePeriod() {
		return breakout_pressureRatePeriod;
	}
	
	public void setBreakout_pressureRatePeriod(int breakout_pressureRatePeriod) {
		this.breakout_pressureRatePeriod = breakout_pressureRatePeriod;
	}
	
	public int getBreakout_boundaryPointN1() {
		return breakout_boundaryPointN1;
	}
	
	public void setBreakout_boundaryPointN1(int breakout_boundaryPointN1) {
		this.breakout_boundaryPointN1 = breakout_boundaryPointN1;
	}
	
	public int getBreakout_boundaryPointN2() {
		return breakout_boundaryPointN2;
	}
	
	public void setBreakout_boundaryPointN2(int breakout_boundaryPointN2) {
		this.breakout_boundaryPointN2 = breakout_boundaryPointN2;
	}
	
	public double getBreakout_breakPointR() {
		return breakout_breakPointR;
	}
	
	public void setBreakout_breakPointR(double breakout_breakPointR) {
		this.breakout_breakPointR = breakout_breakPointR;
	}
	
	public double getBreakout_breakPointP() {
		return breakout_breakPointP;
	}
	
	public void setBreakout_breakPointP(double breakout_breakPointP) {
		this.breakout_breakPointP = breakout_breakPointP;
	}
	
	public int getBreakout_waveNum() {
		return breakout_waveNum;
	}
	
	public void setBreakout_waveNum(int breakout_waveNum) {
		this.breakout_waveNum = breakout_waveNum;
	}
	
	public double getBreakout_lowBoundary1() {
		return breakout_lowBoundary1;
	}
	
	public void setBreakout_lowBoundary1(double breakout_lowBoundary1) {
		this.breakout_lowBoundary1 = breakout_lowBoundary1;
	}
	
	public double getBreakout_qtyStandardThreshold() {
		return breakout_qtyStandardThreshold;
	}
	
	public void setBreakout_qtyStandardThreshold(double breakout_qtyStandardThreshold) {
		this.breakout_qtyStandardThreshold = breakout_qtyStandardThreshold;
	}
	
	public double getBreakout_paraRate() {
		return breakout_paraRate;
	}
	
	public void setBreakout_paraRate(double breakout_paraRate) {
		this.breakout_paraRate = breakout_paraRate;
	}
	
	public double getBreakout_minPressureRate() {
		return breakout_minPressureRate;
	}
	
	public void setBreakout_minPressureRate(double breakout_minPressureRate) {
		this.breakout_minPressureRate = breakout_minPressureRate;
	}
	
	public double getBreakout_askValueThreshold() {
		return breakout_askValueThreshold;
	}
	
	public void setBreakout_askValueThreshold(double breakout_askValueThreshold) {
		this.breakout_askValueThreshold = breakout_askValueThreshold;
	}
	
	public double getBreakout_lowBoundary2() {
		return breakout_lowBoundary2;
	}
	
	public void setBreakout_lowBoundary2(double breakout_lowBoundary2) {
		this.breakout_lowBoundary2 = breakout_lowBoundary2;
	}
	
	public double getBreakout_pressureUpdatePara() {
		return breakout_pressureUpdatePara;
	}
	
	public void setBreakout_pressureUpdatePara(double breakout_pressureUpdatePara) {
		this.breakout_pressureUpdatePara = breakout_pressureUpdatePara;
	}
	
	public int getBreakout_signalParaP1() {
		return breakout_signalParaP1;
	}
	
	public void setBreakout_signalParaP1(int breakout_signalParaP1) {
		this.breakout_signalParaP1 = breakout_signalParaP1;
	}
	
	public double getBreakout_signalParaP2() {
		return breakout_signalParaP2;
	}
	
	public void setBreakout_signalParaP2(double breakout_signalParaP2) {
		this.breakout_signalParaP2 = breakout_signalParaP2;
	}
	
	public int getBreakout_signalParaP3() {
		return breakout_signalParaP3;
	}
	
	public void setBreakout_signalParaP3(int breakout_signalParaP3) {
		this.breakout_signalParaP3 = breakout_signalParaP3;
	}
	
	public double getBreakout_signalParaP4() {
		return breakout_signalParaP4;
	}
	
	public void setBreakout_signalParaP4(double breakout_signalParaP4) {
		this.breakout_signalParaP4 = breakout_signalParaP4;
	}
	
	public double getBreakout_signalParaP5() {
		return breakout_signalParaP5;
	}
	
	public void setBreakout_signalParaP5(double breakout_signalParaP5) {
		this.breakout_signalParaP5 = breakout_signalParaP5;
	}
	
	public double getBreakout_signalParaP6() {
		return breakout_signalParaP6;
	}
	
	public void setBreakout_signalParaP6(double breakout_signalParaP6) {
		this.breakout_signalParaP6 = breakout_signalParaP6;
	}
	
	public double getBreakout_signalParaP7() {
		return breakout_signalParaP7;
	}
	
	public void setBreakout_signalParaP7(double breakout_signalParaP7) {
		this.breakout_signalParaP7 = breakout_signalParaP7;
	}
	
	public double getBreakout_signalParaP8() {
		return breakout_signalParaP8;
	}
	
	public void setBreakout_signalParaP8(double breakout_signalParaP8) {
		this.breakout_signalParaP8 = breakout_signalParaP8;
	}
	
	public double getBreakout_signalParaP9() {
		return breakout_signalParaP9;
	}
	
	public void setBreakout_signalParaP9(double breakout_signalParaP9) {
		this.breakout_signalParaP9 = breakout_signalParaP9;
	}
	
	public double getBreakout_signalParaP10() {
		return breakout_signalParaP10;
	}
	
	public void setBreakout_signalParaP10(double breakout_signalParaP10) {
		this.breakout_signalParaP10 = breakout_signalParaP10;
	}
	
	public int getBreakout_avgPriceLag() {
		return breakout_avgPriceLag;
	}
	
	public void setBreakout_avgPriceLag(int breakout_avgPriceLag) {
		this.breakout_avgPriceLag = breakout_avgPriceLag;
	}
	
	public int getBreakout_emaPressureRatioLag() {
		return breakout_emaPressureRatioLag;
	}
	
	public void setBreakout_emaPressureRatioLag(int breakout_emaPressureRatioLag) {
		this.breakout_emaPressureRatioLag = breakout_emaPressureRatioLag;
	}
	
	public double getBreakout_closeMaxDrop() {
		return breakout_closeMaxDrop;
	}
	
	public void setBreakout_closeMaxDrop(double breakout_closeMaxDrop) {
		this.breakout_closeMaxDrop = breakout_closeMaxDrop;
	}
	
	public int getReverse_boundaryPointN1() {
		return reverse_boundaryPointN1;
	}
	
	public void setReverse_boundaryPointN1(int reverse_boundaryPointN1) {
		this.reverse_boundaryPointN1 = reverse_boundaryPointN1;
	}
	
	public int getReverse_boundaryPointN2() {
		return reverse_boundaryPointN2;
	}
	
	public void setReverse_boundaryPointN2(int reverse_boundaryPointN2) {
		this.reverse_boundaryPointN2 = reverse_boundaryPointN2;
	}
	
	public int getReverse_waveNum() {
		return reverse_waveNum;
	}
	
	public void setReverse_waveNum(int reverse_waveNum) {
		this.reverse_waveNum = reverse_waveNum;
	}
	
	public int getReverse_pressureRatePeriod() {
		return reverse_pressureRatePeriod;
	}
	
	public void setReverse_pressureRatePeriod(int reverse_pressureRatePeriod) {
		this.reverse_pressureRatePeriod = reverse_pressureRatePeriod;
	}
	
	public int getReverse_orderPressureN() {
		return reverse_orderPressureN;
	}
	
	public void setReverse_orderPressureN(int reverse_orderPressureN) {
		this.reverse_orderPressureN = reverse_orderPressureN;
	}
	
	public int getReverse_orderPressureLag() {
		return reverse_orderPressureLag;
	}
	
	public void setReverse_orderPressureLag(int reverse_orderPressureLag) {
		this.reverse_orderPressureLag = reverse_orderPressureLag;
	}
	
	public String getReverse_indexCode() {
		return reverse_indexCode;
	}
	
	public void setReverse_indexCode(String reverse_indexCode) {
		this.reverse_indexCode = reverse_indexCode;
	}
	
	public int getReverse_paraFast() {
		return reverse_paraFast;
	}
	
	public void setReverse_paraFast(int reverse_paraFast) {
		this.reverse_paraFast = reverse_paraFast;
	}
	
	public int getReverse_paraSlow() {
		return reverse_paraSlow;
	}
	
	public void setReverse_paraSlow(int reverse_paraSlow) {
		this.reverse_paraSlow = reverse_paraSlow;
	}
	
	public int getReverse_speedLag() {
		return reverse_speedLag;
	}
	
	public void setReverse_speedLag(int reverse_speedLag) {
		this.reverse_speedLag = reverse_speedLag;
	}
	
	public double getReverse_indexDropSpeedRate() {
		return reverse_indexDropSpeedRate;
	}
	
	public void setReverse_indexDropSpeedRate(double reverse_indexDropSpeedRate) {
		this.reverse_indexDropSpeedRate = reverse_indexDropSpeedRate;
	}
	
	public int getReverse_volumePeriod() {
		return reverse_volumePeriod;
	}
	
	public void setReverse_volumePeriod(int reverse_volumePeriod) {
		this.reverse_volumePeriod = reverse_volumePeriod;
	}
	
	public int getReverse_avgPriceLag() {
		return reverse_avgPriceLag;
	}
	
	public void setReverse_avgPriceLag(int reverse_avgPriceLag) {
		this.reverse_avgPriceLag = reverse_avgPriceLag;
	}
	
	public int getReverse_emaPressureRatioLag() {
		return reverse_emaPressureRatioLag;
	}
	
	public void setReverse_emaPressureRatioLag(int reverse_emaPressureRatioLag) {
		this.reverse_emaPressureRatioLag = reverse_emaPressureRatioLag;
	}
	
	public double getReverse_emaOrderPressureTriggerRate() {
		return reverse_emaOrderPressureTriggerRate;
	}
	
	public void setReverse_emaOrderPressureTriggerRate(double reverse_emaOrderPressureTriggerRate) {
		this.reverse_emaOrderPressureTriggerRate = reverse_emaOrderPressureTriggerRate;
	}
	
	public double getReverse_triggerPressure() {
		return reverse_triggerPressure;
	}
	
	public void setReverse_triggerPressure(double reverse_triggerPressure) {
		this.reverse_triggerPressure = reverse_triggerPressure;
	}
	
	public double getReverse_minSupportAmount() {
		return reverse_minSupportAmount;
	}
	
	public void setReverse_minSupportAmount(double reverse_minSupportAmount) {
		this.reverse_minSupportAmount = reverse_minSupportAmount;
	}
	
	public double getReverse_minSupportRate() {
		return reverse_minSupportRate;
	}
	
	public void setReverse_minSupportRate(double reverse_minSupportRate) {
		this.reverse_minSupportRate = reverse_minSupportRate;
	}
	
	public double getReverse_minPierceNum() {
		return reverse_minPierceNum;
	}
	
	public void setReverse_minPierceNum(double reverse_minPierceNum) {
		this.reverse_minPierceNum = reverse_minPierceNum;
	}
	
	public double getReverse_dyRatio() {
		return reverse_dyRatio;
	}
	
	public void setReverse_dyRatio(double reverse_dyRatio) {
		this.reverse_dyRatio = reverse_dyRatio;
	}
	
	public double getReverse_dyRatio2() {
		return reverse_dyRatio2;
	}
	
	public void setReverse_dyRatio2(double reverse_dyRatio2) {
		this.reverse_dyRatio2 = reverse_dyRatio2;
	}
	
	public double getReverse_dyAmountRatio() {
		return reverse_dyAmountRatio;
	}
	
	public void setReverse_dyAmountRatio(double reverse_dyAmountRatio) {
		this.reverse_dyAmountRatio = reverse_dyAmountRatio;
	}
	
	public double getReverse_dropRate() {
		return reverse_dropRate;
	}
	
	public void setReverse_dropRate(double reverse_dropRate) {
		this.reverse_dropRate = reverse_dropRate;
	}
	
	public double getReverse_volumeRate() {
		return reverse_volumeRate;
	}
	
	public void setReverse_volumeRate(double reverse_volumeRate) {
		this.reverse_volumeRate = reverse_volumeRate;
	}
	
	public double getReverse_speedRate() {
		return reverse_speedRate;
	}
	
	public void setReverse_speedRate(double reverse_speedRate) {
		this.reverse_speedRate = reverse_speedRate;
	}
	
	public int getReverse_revLag() {
		return reverse_revLag;
	}
	
	public void setReverse_revLag(int reverse_revLag) {
		this.reverse_revLag = reverse_revLag;
	}
	
	public double getReverse_minPrice() {
		return reverse_minPrice;
	}
	
	public void setReverse_minPrice(double reverse_minPrice) {
		this.reverse_minPrice = reverse_minPrice;
	}
	
	public double getReverse_closeMaxDrop() {
		return reverse_closeMaxDrop;
	}
	
	public void setReverse_closeMaxDrop(double reverse_closeMaxDrop) {
		this.reverse_closeMaxDrop = reverse_closeMaxDrop;
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
	
	public boolean isAutoTrade() {
		return autoTrade;
	}
	
	public void setAutoTrade(boolean autoTrade) {
		this.autoTrade = autoTrade;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IntradayStrategyParam [universe=");
		builder.append(universe);
		builder.append(", breakout_pressureRatePeriod=");
		builder.append(breakout_pressureRatePeriod);
		builder.append(", breakout_boundaryPointN1=");
		builder.append(breakout_boundaryPointN1);
		builder.append(", breakout_boundaryPointN2=");
		builder.append(breakout_boundaryPointN2);
		builder.append(", breakout_breakPointR=");
		builder.append(breakout_breakPointR);
		builder.append(", breakout_breakPointP=");
		builder.append(breakout_breakPointP);
		builder.append(", breakout_waveNum=");
		builder.append(breakout_waveNum);
		builder.append(", breakout_lowBoundary1=");
		builder.append(breakout_lowBoundary1);
		builder.append(", breakout_qtyStandardThreshold=");
		builder.append(breakout_qtyStandardThreshold);
		builder.append(", breakout_paraRate=");
		builder.append(breakout_paraRate);
		builder.append(", breakout_minPressureRate=");
		builder.append(breakout_minPressureRate);
		builder.append(", breakout_askValueThreshold=");
		builder.append(breakout_askValueThreshold);
		builder.append(", breakout_lowBoundary2=");
		builder.append(breakout_lowBoundary2);
		builder.append(", breakout_pressureUpdatePara=");
		builder.append(breakout_pressureUpdatePara);
		builder.append(", breakout_signalParaP1=");
		builder.append(breakout_signalParaP1);
		builder.append(", breakout_signalParaP2=");
		builder.append(breakout_signalParaP2);
		builder.append(", breakout_signalParaP3=");
		builder.append(breakout_signalParaP3);
		builder.append(", breakout_signalParaP4=");
		builder.append(breakout_signalParaP4);
		builder.append(", breakout_signalParaP5=");
		builder.append(breakout_signalParaP5);
		builder.append(", breakout_signalParaP6=");
		builder.append(breakout_signalParaP6);
		builder.append(", breakout_signalParaP7=");
		builder.append(breakout_signalParaP7);
		builder.append(", breakout_signalParaP8=");
		builder.append(breakout_signalParaP8);
		builder.append(", breakout_signalParaP9=");
		builder.append(breakout_signalParaP9);
		builder.append(", breakout_signalParaP10=");
		builder.append(breakout_signalParaP10);
		builder.append(", breakout_avgPriceLag=");
		builder.append(breakout_avgPriceLag);
		builder.append(", breakout_emaPressureRatioLag=");
		builder.append(breakout_emaPressureRatioLag);
		builder.append(", breakout_closeMaxDrop=");
		builder.append(breakout_closeMaxDrop);
		builder.append(", reverse_boundaryPointN1=");
		builder.append(reverse_boundaryPointN1);
		builder.append(", reverse_boundaryPointN2=");
		builder.append(reverse_boundaryPointN2);
		builder.append(", reverse_waveNum=");
		builder.append(reverse_waveNum);
		builder.append(", reverse_pressureRatePeriod=");
		builder.append(reverse_pressureRatePeriod);
		builder.append(", reverse_orderPressureN=");
		builder.append(reverse_orderPressureN);
		builder.append(", reverse_orderPressureLag=");
		builder.append(reverse_orderPressureLag);
		builder.append(", reverse_indexCode=");
		builder.append(reverse_indexCode);
		builder.append(", reverse_paraFast=");
		builder.append(reverse_paraFast);
		builder.append(", reverse_paraSlow=");
		builder.append(reverse_paraSlow);
		builder.append(", reverse_speedLag=");
		builder.append(reverse_speedLag);
		builder.append(", reverse_indexDropSpeedRate=");
		builder.append(reverse_indexDropSpeedRate);
		builder.append(", reverse_volumePeriod=");
		builder.append(reverse_volumePeriod);
		builder.append(", reverse_avgPriceLag=");
		builder.append(reverse_avgPriceLag);
		builder.append(", reverse_emaPressureRatioLag=");
		builder.append(reverse_emaPressureRatioLag);
		builder.append(", reverse_emaOrderPressureTriggerRate=");
		builder.append(reverse_emaOrderPressureTriggerRate);
		builder.append(", reverse_triggerPressure=");
		builder.append(reverse_triggerPressure);
		builder.append(", reverse_minSupportAmount=");
		builder.append(reverse_minSupportAmount);
		builder.append(", reverse_minSupportRate=");
		builder.append(reverse_minSupportRate);
		builder.append(", reverse_minPierceNum=");
		builder.append(reverse_minPierceNum);
		builder.append(", reverse_dyRatio=");
		builder.append(reverse_dyRatio);
		builder.append(", reverse_dyRatio2=");
		builder.append(reverse_dyRatio2);
		builder.append(", reverse_dyAmountRatio=");
		builder.append(reverse_dyAmountRatio);
		builder.append(", reverse_dropRate=");
		builder.append(reverse_dropRate);
		builder.append(", reverse_volumeRate=");
		builder.append(reverse_volumeRate);
		builder.append(", reverse_speedRate=");
		builder.append(reverse_speedRate);
		builder.append(", reverse_revLag=");
		builder.append(reverse_revLag);
		builder.append(", reverse_minPrice=");
		builder.append(reverse_minPrice);
		builder.append(", reverse_closeMaxDrop=");
		builder.append(reverse_closeMaxDrop);
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
