package com.huatai.common.backtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huatai.common.marketdata.CandleStick;
import com.huatai.common.marketdata.TimeFrame;

public class MarketDataCache {
	// key:= TimeFrame.name()+"."+symbol; value:= corresponding candle sticks;
	private final Map<String, List<CandleStick>> historicalData = new HashMap<String, List<CandleStick>>();
	
	private static String generateKey(String symbol, TimeFrame timeFrame) {
		return symbol + "." + timeFrame.name();
	}
	
	public void put(String symbol, TimeFrame timeFrame, List<CandleStick> list) {
		String key = generateKey(symbol, timeFrame);
		historicalData.put(key, list);
	}
	
	public List<CandleStick> get(String symbol, TimeFrame timeFrame) {
		String key = generateKey(symbol, timeFrame);
		return historicalData.get(key);
	}
}
