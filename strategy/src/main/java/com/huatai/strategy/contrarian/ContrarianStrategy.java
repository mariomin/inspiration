package com.huatai.strategy.contrarian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.algo.AtsStrategy;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.type.StrategyState;
import com.huatai.platform.algo.Strategy;

@AtsStrategy(chineseName = "反转策略", param = ContrarianStrategy.class)
public class ContrarianStrategy extends Strategy<ContrarianStrategyParams, ContrarianAnalysisResult> {
	private final static Logger log = LoggerFactory.getLogger(ContrarianStrategy.class);
	
	@Override
	public void init() {
		super.init();
		log.info("Strategy Param={}", params);
	}
	
	@Override
	public void onMarketData(MarketData marketData) {
		if ((getState() != StrategyState.Initializing) && (getState() != StrategyState.Stopped)) {
			ContrarianAnalysisResult result = signalAnalyzer.analyze(marketData);
			if (getState() == StrategyState.Running) {
				try {
					if (result != null) {
						tradeExecutor.execute(result);
					}
				} catch (Exception e) {
					log.error("failed to execute analysis result: {}", e);
				}
			}
		}
	}
	
	@Override
	public void onTimeEvent(AsyncEvent event) {}
	
	@Override
	public void createSignalAnalyzer() {
		signalAnalyzer = new ContrarianAnalyzer(mdCalService, tradingService);
	}
	
	@Override
	public void createTradeExecutor() {
		tradeExecutor = new ContrarianTradeExecutor(mdCalService, tradingService);
	}
	
	@Override
	public void subscribe() {}
	
	@Override
	public void onStateUpdated() {}
	
}
