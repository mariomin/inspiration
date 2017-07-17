package com.huatai.strategy.intraday;

import com.huatai.common.marketdata.Level;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.platform.algo.SignalAnalyzer;
import com.huatai.strategy.intraday.IntradayEPLSubscriber.IndexCalculationSubscriber;
import com.huatai.strategy.intraday.IntradayEPLSubscriber.MarketCloseSubscriber;
import com.huatai.strategy.intraday.IntradayEPLSubscriber.QuoteCalculationSubscriber;
import com.huatai.strategy.intraday.IntradayEPLSubscriber.SignalAnalysisSubscriber;

public class IntradaySignalAnalyzer extends SignalAnalyzer<IntradayAnalysisResult> {
	private final IntradayDataCache dataCache;
	
	private IntradayEPLSubscriber eplSubscriber;
	private QuoteCalculationSubscriber quoteCalSubscriber;
	private SignalAnalysisSubscriber signalAnalysisSubscriber;
	private MarketCloseSubscriber marketCloseSubscriber;
	private IndexCalculationSubscriber indexCalSubscriber;
	
	public IntradaySignalAnalyzer(IntradayDataCache dataCache) {
		this.dataCache = dataCache;
	}
	
	@Override
	public void init() {
		eplSubscriber = new IntradayEPLSubscriber(dataCache);
		quoteCalSubscriber = eplSubscriber.new QuoteCalculationSubscriber();
		signalAnalysisSubscriber = eplSubscriber.new SignalAnalysisSubscriber();
		marketCloseSubscriber = eplSubscriber.new MarketCloseSubscriber();
		indexCalSubscriber = eplSubscriber.new IndexCalculationSubscriber();
		
		String indexCode = dataCache.reverseDataCache.params.getIndexCode();
		String quoteStmt = "insert into FormalQuote select symbol, previousClosingPx, asks, bids, totalVolume, turnover, timestamp, formalizeTime(timestamp) as time from Quote where isValidTime(timestamp) and symbol!='"
				+ indexCode + "'";
		String stmtQuoteCalculation = "select symbol, calMidPrice(bids, asks), asks, bids, totalVolume, turnover, timestamp, time from FormalQuote where calMidPrice(bids, asks ) > 0";
		String stmtSignalAnalysis = "select symbol, previousClosingPx, asks, bids from FormalQuote";
		String stmtMarketAtClose = "select timestamp from Quote where isMarketAtClose(timestamp)";
		String stmtIndexCalculation = "select symbol, lastPx, totalVolume,turnover,timestamp, formalizeTime(timestamp) as time from Quote where isValidTime(timestamp) and symbol='"
				+ indexCode + "'";
		
		esperEngine.createEPL(quoteStmt);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtQuoteCalculation), quoteCalSubscriber);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtSignalAnalysis), signalAnalysisSubscriber);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtMarketAtClose), marketCloseSubscriber);
		esperEngine.setSubscriber(esperEngine.createEPL(stmtIndexCalculation), indexCalSubscriber);
	}
	
	@Override
	public IntradayAnalysisResult analyze(MarketData marketData) {
		if (marketData instanceof Quote) {
			eplSubscriber.clearResult();
			Quote quote = (Quote) marketData;
			if (quote.getLevel() == Level.full) {
				esperEngine.sendEvent(marketData);
			}
		}
		return eplSubscriber.getResult();
	}
	
	@Override
	public void uninit() {}
	
}
