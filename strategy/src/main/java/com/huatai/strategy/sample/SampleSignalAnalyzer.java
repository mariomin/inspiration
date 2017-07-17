package com.huatai.strategy.sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.platform.algo.SignalAnalyzer;

public class SampleSignalAnalyzer extends SignalAnalyzer<AnalysisResult> {
	private final Map<String, Double> buyLimits;
	private final Map<String, LinkedList<Double>> askPrices = new HashMap<String, LinkedList<Double>>();
	private final int size = 30;
	
	public SampleSignalAnalyzer(Map<String, Double> buyLimits) {
		this.buyLimits = buyLimits;
	}
	
	@Override
	public void init() {}
	
	@Override
	public void uninit() {}
	
	@Override
	public AnalysisResult analyze(MarketData marketData) {
		AnalysisResult result = null;
		Quote quote = (Quote) marketData;
		String symbol = quote.getSymbol();
		double ask = quote.getAsk();
		LinkedList<Double> askPriceList = askPrices.get(symbol);
		
		if (askPriceList == null) {
			askPriceList = new LinkedList<Double>();
			askPrices.put(symbol, askPriceList);
		}
		
		askPriceList.offer(quote.getAsk());
		while (askPriceList.size() > size) {
			askPriceList.poll();
		}
		
		double sum = 0d;
		for (Double askPrice : askPriceList) {
			sum += askPrice;
		}
		double avgPrice = sum / askPriceList.size();
		double buyLimit = buyLimits.get(symbol);
		if ((buyLimit > ask) && (buyLimit < avgPrice)) {
			result = new AnalysisResult(symbol, ask);
		}
		
		return result;
	}
	
}
