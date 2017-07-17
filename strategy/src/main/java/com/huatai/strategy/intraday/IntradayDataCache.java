package com.huatai.strategy.intraday;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.huatai.strategy.intraday.breakout.BreakoutDataCache;
import com.huatai.strategy.intraday.common.OrderInfo;
import com.huatai.strategy.intraday.common.PortfolioRecord;
import com.huatai.strategy.intraday.reverse.ReverseDataCache;

public class IntradayDataCache {
	public final BreakoutDataCache breakoutDataCache = new BreakoutDataCache();
	public final ReverseDataCache reverseDataCache = new ReverseDataCache();
	public Map<String, OrderInfo> orderInfoSet = new ConcurrentHashMap<String, OrderInfo>();
	public Map<String, Double> holdingPositions = new ConcurrentHashMap<String, Double>();
	
	public void initDataCache(IntradayStrategyParam params) {
		initBreakOutCache(params);
		initReverseCache(params);
		for (PortfolioRecord record : params.getUniverse()) {
			holdingPositions.put(record.getSymbol(), 0d);
		}
	}
	
	private void initBreakOutCache(IntradayStrategyParam params) {
		breakoutDataCache.params.setUniverse(params.getUniverse());
		breakoutDataCache.params.setPressureRatePeriod(params.getBreakout_pressureRatePeriod());
		breakoutDataCache.params.setBoundaryPointN1(params.getBreakout_boundaryPointN1());
		breakoutDataCache.params.setBoundaryPointN2(params.getBreakout_boundaryPointN2());
		breakoutDataCache.params.setBreakPointP(params.getBreakout_breakPointP());
		breakoutDataCache.params.setBreakPointR(params.getBreakout_breakPointR());
		breakoutDataCache.params.setWaveNum(params.getBreakout_waveNum());
		breakoutDataCache.params.setLowBoundary1(params.getBreakout_lowBoundary1());
		breakoutDataCache.params.setQtyStandardThreshold(params.getBreakout_qtyStandardThreshold());
		breakoutDataCache.params.setParaRate(params.getBreakout_paraRate());
		breakoutDataCache.params.setMinPressureRate(params.getBreakout_minPressureRate());
		breakoutDataCache.params.setAskValueThreshold(params.getBreakout_askValueThreshold());
		breakoutDataCache.params.setLowBoundary2(params.getBreakout_lowBoundary2());
		breakoutDataCache.params.setPressureUpdatePara(params.getBreakout_pressureUpdatePara());
		breakoutDataCache.params.setSignalParaP1(params.getBreakout_signalParaP1());
		breakoutDataCache.params.setSignalParaP2(params.getBreakout_signalParaP2());
		breakoutDataCache.params.setSignalParaP3(params.getBreakout_signalParaP3());
		breakoutDataCache.params.setSignalParaP4(params.getBreakout_signalParaP4());
		breakoutDataCache.params.setSignalParaP5(params.getBreakout_signalParaP5());
		breakoutDataCache.params.setSignalParaP6(params.getBreakout_signalParaP6());
		breakoutDataCache.params.setSignalParaP7(params.getBreakout_signalParaP7());
		breakoutDataCache.params.setSignalParaP8(params.getBreakout_signalParaP8());
		breakoutDataCache.params.setSignalParaP9(params.getBreakout_signalParaP9());
		breakoutDataCache.params.setSignalParaP10(params.getBreakout_signalParaP10());
		breakoutDataCache.params.setAvgPriceLag(params.getBreakout_avgPriceLag());
		breakoutDataCache.params.setEmaPressureRatioLag(params.getBreakout_emaPressureRatioLag());
		breakoutDataCache.params.setCloseMaxDrop(params.getBreakout_closeMaxDrop());
		breakoutDataCache.params.setBuyLevel(params.getBuyLevel());
		breakoutDataCache.params.setBuyDeviation(params.getBuyDeviation());
		breakoutDataCache.params.setSellLevel(params.getSellLevel());
		breakoutDataCache.params.setSellDeviation(params.getSellDeviation());
		breakoutDataCache.params.setCloseOpenParaP1(params.getCloseOpenParaP1());
		breakoutDataCache.params.setCloseOpenParaP2(params.getCloseOpenParaP2());
		breakoutDataCache.params.setCloseOpenParaP3(params.getCloseOpenParaP3());
		breakoutDataCache.params.setCloseOpenParaP4(params.getCloseOpenParaP4());
		breakoutDataCache.params.setCloseOpenParaP5(params.getCloseOpenParaP5());
		breakoutDataCache.params.setAutoTrade(params.isAutoTrade());
		
		for (PortfolioRecord record : params.getUniverse()) {
			breakoutDataCache.portfolioSet.put(record.getSymbol(), record);
			breakoutDataCache.holdingPositions.put(record.getSymbol(), 0d);
			breakoutDataCache.openPrices.put(record.getSymbol(), 0d);
		}
		
	}
	
	private void initReverseCache(IntradayStrategyParam params) {
		reverseDataCache.params.setUniverse(params.getUniverse());
		reverseDataCache.params.setBoundaryPointN1(params.getReverse_boundaryPointN1());
		reverseDataCache.params.setBoundaryPointN2(params.getReverse_boundaryPointN2());
		reverseDataCache.params.setWaveNum(params.getReverse_waveNum());
		reverseDataCache.params.setPressureRatePeriod(params.getReverse_pressureRatePeriod());
		reverseDataCache.params.setOrderPressureN(params.getReverse_orderPressureN());
		reverseDataCache.params.setOrderPressureLag(params.getReverse_orderPressureLag());
		reverseDataCache.params.setIndexCode(params.getReverse_indexCode());
		reverseDataCache.params.setParaFast(params.getReverse_paraFast());
		reverseDataCache.params.setParaSlow(params.getReverse_paraSlow());
		reverseDataCache.params.setSpeedLag(params.getReverse_speedLag());
		reverseDataCache.params.setIndexDropSpeedRate(params.getReverse_indexDropSpeedRate());
		reverseDataCache.params.setVolumePeriod(params.getReverse_volumePeriod());
		reverseDataCache.params.setAvgPriceLag(params.getReverse_avgPriceLag());
		reverseDataCache.params.setEmaPressureRatioLag(params.getReverse_emaPressureRatioLag());
		reverseDataCache.params.setEmaOrderPressureTriggerRate(params.getReverse_emaOrderPressureTriggerRate());
		reverseDataCache.params.setTriggerPressure(params.getReverse_triggerPressure());
		reverseDataCache.params.setMinSupportAmount(params.getReverse_minSupportAmount());
		reverseDataCache.params.setMinSupportRate(params.getReverse_minSupportRate());
		reverseDataCache.params.setMinPierceNum(params.getReverse_minPierceNum());
		reverseDataCache.params.setDyRatio(params.getReverse_dyRatio());
		reverseDataCache.params.setDyRatio2(params.getReverse_dyRatio2());
		reverseDataCache.params.setDyAmountRatio(params.getReverse_dyAmountRatio());
		reverseDataCache.params.setDropRate(params.getReverse_dropRate());
		reverseDataCache.params.setVolumeRate(params.getReverse_volumeRate());
		reverseDataCache.params.setSpeedRate(params.getReverse_speedRate());
		reverseDataCache.params.setRevLag(params.getReverse_revLag());
		reverseDataCache.params.setMinPrice(params.getReverse_minPrice());
		reverseDataCache.params.setCloseMaxDrop(params.getReverse_closeMaxDrop());
		reverseDataCache.params.setBuyLevel(params.getBuyLevel());
		reverseDataCache.params.setBuyDeviation(params.getBuyDeviation());
		reverseDataCache.params.setSellLevel(params.getSellLevel());
		reverseDataCache.params.setSellDeviation(params.getSellDeviation());
		reverseDataCache.params.setCloseOpenParaP1(params.getCloseOpenParaP1());
		reverseDataCache.params.setCloseOpenParaP2(params.getCloseOpenParaP2());
		reverseDataCache.params.setCloseOpenParaP3(params.getCloseOpenParaP3());
		reverseDataCache.params.setCloseOpenParaP4(params.getCloseOpenParaP4());
		reverseDataCache.params.setCloseOpenParaP5(params.getCloseOpenParaP5());
		reverseDataCache.params.setAutoTrade(params.isAutoTrade());
		
		for (PortfolioRecord record : params.getUniverse()) {
			reverseDataCache.portfolioSet.put(record.getSymbol(), record);
			reverseDataCache.holdingPositions.put(record.getSymbol(), 0d);
			reverseDataCache.openPrices.put(record.getSymbol(), 0d);
		}
	}
}
