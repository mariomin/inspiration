package com.huatai.strategy.intraday;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.type.QtyPrice;
import com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber;
import com.huatai.strategy.intraday.reverse.ReverseEPLSubscriber;

public class IntradayEPLSubscriber {
	private final Logger log = LoggerFactory.getLogger(IntradayEPLSubscriber.class);
	private final IntradayAnalysisResult result;
	private final BreakoutEPLSubscriber breakoutEPLSubscriber;
	private final ReverseEPLSubscriber reverseEPLSubscriber;
	private final com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber.FormalQuoteSubscriber breakout_QuoteSubscriber;
	private final com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber.MarketCloseSubscriber breakout_MarketCloseSubscriber;
	private final com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber.TradeIndexSubscriber breakout_TradeIndexSubscriber;
	private final com.huatai.strategy.intraday.reverse.ReverseEPLSubscriber.QuoteCalculationSubscriber reverse_QuoteSubscriber;
	private final com.huatai.strategy.intraday.reverse.ReverseEPLSubscriber.IndexCalculationSubscriber reverse_IndexCalSubscriber;
	private final com.huatai.strategy.intraday.reverse.ReverseEPLSubscriber.MarketCloseSubscriber reverse_MarketCloseSubscriber;
	private final com.huatai.strategy.intraday.reverse.ReverseEPLSubscriber.SignalAnalysisSubscriber reverse_SignalAnalysisSubscriber;
	
	public IntradayEPLSubscriber(IntradayDataCache dataCache) {
		log.info("creating intradayEPLSubscriber");
		breakoutEPLSubscriber = new BreakoutEPLSubscriber(dataCache.breakoutDataCache);
		reverseEPLSubscriber = new ReverseEPLSubscriber(dataCache.reverseDataCache);
		result = new IntradayAnalysisResult(breakoutEPLSubscriber.getResult(), reverseEPLSubscriber.getResult());
		breakout_QuoteSubscriber = breakoutEPLSubscriber.new FormalQuoteSubscriber();
		breakout_MarketCloseSubscriber = breakoutEPLSubscriber.new MarketCloseSubscriber();
		breakout_TradeIndexSubscriber = breakoutEPLSubscriber.new TradeIndexSubscriber();
		reverse_QuoteSubscriber = reverseEPLSubscriber.new QuoteCalculationSubscriber();
		reverse_IndexCalSubscriber = reverseEPLSubscriber.new IndexCalculationSubscriber();
		reverse_MarketCloseSubscriber = reverseEPLSubscriber.new MarketCloseSubscriber();
		reverse_SignalAnalysisSubscriber = reverseEPLSubscriber.new SignalAnalysisSubscriber();
	}
	
	public class QuoteCalculationSubscriber {
		
		public void update(String symbol, double midPrice, List<QtyPrice> asks, List<QtyPrice> bids, double totalVolume, double turnover, Date date,
				long time) {
			breakout_QuoteSubscriber.update(symbol, midPrice, asks, bids, totalVolume, turnover, date, time);
			reverse_QuoteSubscriber.update(symbol, midPrice, asks, bids, totalVolume, turnover, date, time);
		}
		
	}
	
	public class SignalAnalysisSubscriber {
		public void update(String symbol, double preClosePx, List<QtyPrice> asks, List<QtyPrice> bids) {
			breakout_TradeIndexSubscriber.update(symbol, preClosePx, asks, bids);
			reverse_SignalAnalysisSubscriber.update(symbol, preClosePx, asks, bids);
		}
		
	}
	
	public class MarketCloseSubscriber {
		public void update(Date timestamp) {
			breakout_MarketCloseSubscriber.update(timestamp);
			reverse_MarketCloseSubscriber.update(timestamp);
		}
	}
	
	public class IndexCalculationSubscriber {
		public void update(String symbol, double lastPx, double totalVolume, double turnover, Date date, long time) {
			reverse_IndexCalSubscriber.update(symbol, lastPx, totalVolume, turnover, date, time);
		}
		
	}
	
	public void clearResult() {
		result.clearAll();
	}
	
	public IntradayAnalysisResult getResult() {
		return result;
	}
}
