package com.huatai.strategy.intraday.breakout;

import com.huatai.common.marketdata.Level;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.Trade;
import com.huatai.platform.algo.SignalAnalyzer;
import com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber.FormalQuoteSubscriber;
import com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber.MarketCloseSubscriber;
import com.huatai.strategy.intraday.breakout.BreakoutEPLSubscriber.TradeIndexSubscriber;

public class BreakoutSignalAnalyzer extends SignalAnalyzer<BreakoutAnalysisResult> {
	private static final String quoteStmt = "insert into FormalQuote select symbol, previousClosingPx, asks, bids, totalVolume, turnover, timestamp, formalizeTime(timestamp) as time from Quote where isValidTime(timestamp)";
	private static final String stmtQuoteCalculation = "select symbol, calMidPrice(bids, asks), asks, bids, totalVolume, turnover, timestamp, time from FormalQuote group by symbol having calMidPrice(bids, asks ) > 0";
	private static final String stmtAnalysis = "select symbol, previousClosingPx, asks, bids from FormalQuote group by symbol";
	private static final String stmtMarketAtClose = "select timestamp from Quote where isMarketAtClose(timestamp)";
	
	private final BreakoutDataCache dataCache;
	
	private BreakoutEPLSubscriber eplSubscriber;
	private FormalQuoteSubscriber quoteSubscriber;
	private TradeIndexSubscriber tradeIndexSubscriber;
	private MarketCloseSubscriber marketCloseSubscriber;
	
	public BreakoutSignalAnalyzer(BreakoutDataCache dataCache) {
		this.dataCache = dataCache;
	}
	
	@Override
	public void init() {
		eplSubscriber = new BreakoutEPLSubscriber(dataCache);
		quoteSubscriber = eplSubscriber.new FormalQuoteSubscriber();
		tradeIndexSubscriber = eplSubscriber.new TradeIndexSubscriber();
		marketCloseSubscriber = eplSubscriber.new MarketCloseSubscriber();
		
		esperEngine.createEPL(quoteStmt);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtQuoteCalculation), quoteSubscriber);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtAnalysis), tradeIndexSubscriber);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtMarketAtClose), marketCloseSubscriber);
	}
	
	@Override
	public BreakoutAnalysisResult analyze(MarketData marketData) {
		if (marketData instanceof Quote) {
			eplSubscriber.clearResult();
			Quote quote = (Quote) marketData;
			if (quote.getLevel() == Level.full) {
				esperEngine.sendEvent(marketData);
			}
		} else if (marketData instanceof Trade) {
			esperEngine.sendEvent(marketData);
		}
		return eplSubscriber.getResult();
	}
	
	@Override
	public void uninit() {}
	
}
