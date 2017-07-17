package com.huatai.backtest.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.MarketDataCache;
import com.huatai.common.backtest.TimeUtil;
import com.huatai.common.marketdata.CandleStick;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.TimeFrame;

public class BackTestMarketDataCalculateService implements IMarketDataCalculateService {
	private static final Logger log = LoggerFactory.getLogger(BackTestMarketDataCalculateService.class);
	private final MarketDataCache cache = new MarketDataCache();
	private final TimeFrame minTimeFrame;
	
	public BackTestMarketDataCalculateService(TimeFrame timeFrame) {
		minTimeFrame = timeFrame;
	}
	
	@Override
	public void init() {}
	
	@Override
	public void uninit() {}
	
	@Override
	public void updateHistoricalData(MarketData marketData, MarketData preMarketData) {
		String symbol = marketData.getSymbol();
		double highPx, lowPx, openPx, closePx, volume, turnover;
		
		if (marketData instanceof Quote) {
			Quote quote = (Quote) marketData;
			Quote preQuote = (Quote) marketData;
			highPx = quote.getLastPx();
			lowPx = quote.getLastPx();
			openPx = quote.getLastPx();
			closePx = quote.getLastPx();
			volume = quote.getTotalVolume() - preQuote.getTotalVolume();
			turnover = quote.getTurnover() - preQuote.getTurnover();
		} else if (marketData instanceof CandleStick) {
			CandleStick candleStick = (CandleStick) marketData;
			highPx = candleStick.getHighPx();
			lowPx = candleStick.getLowPx();
			openPx = candleStick.getOpenPx();
			closePx = candleStick.getClosePx();
			volume = candleStick.getVolume();
			turnover = candleStick.getTurnover();
		} else {
			log.error("unknow data format:{}", marketData);
			return;
		}
		
		for (TimeFrame timeFrame : TimeFrame.values()) {
			if (timeFrame.value < minTimeFrame.value) {
				continue;
			}
			
			List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
			if (candleSticks == null) {
				candleSticks = new LinkedList<CandleStick>();
				cache.put(symbol, timeFrame, candleSticks);
			}
			CandleStick previousCandleStick = candleSticks.isEmpty() ? null : ((LinkedList<CandleStick>) candleSticks).getFirst();
			// 如果新收到的数据晚于 之前的数据则报错
			if ((previousCandleStick != null) && marketData.getTimestamp().before(previousCandleStick.getTimestamp())) {
				log.error("outdated candleStick, {}", marketData);
				
				// 如果新收到的数据代表�?根新K线，或�?�之前K线集为空，则添加�?条新的K�?
			} else if ((previousCandleStick == null)
					|| TimeUtil.isDifferentTimeFrame(marketData.getTimestamp(), previousCandleStick.getTimestamp(), timeFrame)) {
				CandleStick newCandleStick = new CandleStick(symbol, openPx, closePx, highPx, lowPx, volume, marketData.getTimestamp(), turnover);
				newCandleStick.setTimestamp(TimeUtil.formatCandleStickTime(newCandleStick.getTimestamp(), timeFrame));
				candleSticks.add(0, newCandleStick);
				
				// 更新当前K线的收盘�?,�?高价 ，最低价 ，成交量
			} else {
				previousCandleStick.setClosePx(closePx);
				previousCandleStick.setHighPx(Math.max(previousCandleStick.getHighPx(), highPx));
				previousCandleStick.setLowPx(Math.min(previousCandleStick.getLowPx(), lowPx));
				previousCandleStick.setVolume(previousCandleStick.getVolume() + volume);
				previousCandleStick.setTurnover(previousCandleStick.getTurnover() + turnover);
			}
		}
	}
	
	@Override
	public List<Double> getOpenPx(String symbol, TimeFrame timeFrame, int start, int end) {
		List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
		if ((candleSticks == null) || (start < 0) || (start > end) || ((end + 1) > candleSticks.size())) return null;
		
		List<Double> result = new ArrayList<Double>();
		for (CandleStick candlestick : candleSticks.subList(start, end + 1)) {
			result.add(candlestick.getOpenPx());
		}
		
		return result;
	}
	
	@Override
	public List<Double> getClosePx(String symbol, TimeFrame timeFrame, int start, int end) {
		List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
		if ((candleSticks == null) || (start < 0) || (start > end) || ((end + 1) > candleSticks.size())) return null;
		
		List<Double> result = new ArrayList<Double>();
		for (CandleStick candlestick : candleSticks.subList(start, end + 1)) {
			result.add(candlestick.getClosePx());
		}
		
		return result;
	}
	
	@Override
	public List<Double> getHighPx(String symbol, TimeFrame timeFrame, int start, int end) {
		List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
		if ((candleSticks == null) || (start < 0) || (start > end) || ((end + 1) > candleSticks.size())) return null;
		
		List<Double> result = new ArrayList<Double>();
		for (CandleStick candlestick : candleSticks.subList(start, end + 1)) {
			result.add(candlestick.getHighPx());
		}
		
		return result;
	}
	
	@Override
	public List<Double> getLowPx(String symbol, TimeFrame timeFrame, int start, int end) {
		List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
		if ((candleSticks == null) || (start < 0) || (start > end) || ((end + 1) > candleSticks.size())) return null;
		
		List<Double> result = new ArrayList<Double>();
		for (CandleStick candlestick : candleSticks.subList(start, end + 1)) {
			result.add(candlestick.getLowPx());
		}
		
		return result;
	}
	
	@Override
	public List<Double> getVolume(String symbol, TimeFrame timeFrame, int start, int end) {
		List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
		if ((candleSticks == null) || (start < 0) || (start > end) || ((end + 1) > candleSticks.size())) return null;
		
		List<Double> result = new ArrayList<Double>();
		for (CandleStick candlestick : candleSticks.subList(start, end + 1)) {
			result.add(candlestick.getVolume());
		}
		
		return result;
	}
	
	@Override
	public List<Date> getTime(String symbol, TimeFrame timeFrame, int start, int end) {
		List<CandleStick> candleSticks = cache.get(symbol, timeFrame);
		if ((candleSticks == null) || (start < 0) || (start > end) || ((end + 1) > candleSticks.size())) return null;
		
		List<Date> result = new ArrayList<Date>();
		for (CandleStick candlestick : candleSticks.subList(start, end + 1)) {
			result.add(candlestick.getTimestamp());
		}
		
		return result;
	}
	
}
