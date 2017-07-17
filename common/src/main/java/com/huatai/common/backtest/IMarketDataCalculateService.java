package com.huatai.common.backtest;

import java.util.Date;
import java.util.List;

import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.TimeFrame;

public interface IMarketDataCalculateService extends IBackTestService {
	// both start and end are inclusive;
	List<Double> getOpenPx(String symbol, TimeFrame timeFrame, int start, int end);
	
	// both start and end are inclusive;
	List<Double> getClosePx(String symbol, TimeFrame timeFrame, int start, int end);
	
	// both start and end are inclusive;
	List<Double> getHighPx(String symbol, TimeFrame timeFrame, int start, int end);
	
	// both start and end are inclusive;
	List<Double> getLowPx(String symbol, TimeFrame timeFrame, int start, int end);
	
	// both start and end are inclusive;
	List<Double> getVolume(String symbol, TimeFrame timeFrame, int start, int end);
	
	// both start and end are inclusive;
	List<Date> getTime(String symbol, TimeFrame timeFrame, int start, int end);
	
	void updateHistoricalData(MarketData marketData, MarketData preMarketData);
}
