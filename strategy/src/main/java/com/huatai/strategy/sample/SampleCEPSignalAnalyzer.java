package com.huatai.strategy.sample;

import java.util.Map;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.platform.algo.SignalAnalyzer;

public class SampleCEPSignalAnalyzer extends SignalAnalyzer<AnalysisResult> {
	private final Map<String, Double> buyLimits;
	private final AnalysisResult result = new AnalysisResult();
	
	public SampleCEPSignalAnalyzer(Map<String, Double> buyLimits) {
		this.buyLimits = buyLimits;
	}
	
	@Override
	public void init() {
		// 创建EPL语句，绑定到CEP引擎
		String stmtBuy = "select ask, avg(ask) as avgPrice, symbol from Quote.win:length(30) group by symbol ";
		EPStatement epStmt = esperEngine.createEPL(stmtBuy);
		esperEngine.addListener(epStmt, new UpdateListener() {
			
			@Override
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				for (EventBean newEvent : newEvents) {
					double ask = (Double) newEvent.get("ask");
					String symbol = (String) newEvent.get("symbol");
					double avgPrice = (Double) newEvent.get("avgPrice");
					double buyLimit = buyLimits.get(symbol);
					if ((buyLimit > ask) && (buyLimit < avgPrice)) {
						result.symbol = symbol;
						result.price = ask;
					}
				}
			}
			
		});
	}
	
	@Override
	public AnalysisResult analyze(MarketData marketData) {
		if (marketData instanceof Quote) {
			esperEngine.sendEvent(marketData);
		}
		
		return result;
	}
	
	@Override
	public void uninit() {}
	
}
