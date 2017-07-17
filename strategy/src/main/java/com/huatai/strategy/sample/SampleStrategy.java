package com.huatai.strategy.sample;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.algo.AtsStrategy;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.Trade;
import com.huatai.common.type.StrategyState;
import com.huatai.platform.algo.Strategy;

@AtsStrategy(chineseName = "CEP策略示例", param = SampleStrategyParams.class)
public class SampleStrategy extends Strategy<SampleStrategyParams, AnalysisResult> {
	private static final Logger log = LoggerFactory.getLogger(SampleStrategy.class);
	private final TimeEvent timeEvent = new TimeEvent("Test Time Event");;
	
	@Override
	public void uninit() {
		subscribeService.unsubscribeMarketDataEvent();
		subscribeService.unsubscribeTimerEvent(timeEvent);
	}
	
	@Override
	public void onStateUpdated() {}
	
	public class TimeEvent extends AsyncEvent {
		private static final long serialVersionUID = 1L;
		private final String eventName;
		
		public TimeEvent(String eventName) {
			this.eventName = eventName;
		}
		
		public String getEventName() {
			return eventName;
		}
	}
	
	@Override
	public void subscribe() {
		Set<String> universe = params.getUniverse();
		try {
			// 订阅Quote行情
			subscribeService.subscribeMarketDataEvent(Quote.class, universe);
			// 订阅Trade行情
			subscribeService.subscribeMarketDataEvent(Trade.class, universe);
			// 订阅每隔2000ms一次的定时事件
			subscribeService.subscribeRepeatTimerEvent(2000, timeEvent);
		} catch (Exception e) {
			log.error("failed to subscribe marketData", e);
		}
	}
	
	@Override
	public void createSignalAnalyzer() {
		// 使用CEP进行数据分析的信号分析器
		signalAnalyzer = new SampleCEPSignalAnalyzer(params.getBuyLimitSet());
	}
	
	@Override
	public void createTradeExecutor() {
		tradeExecutor = new SampleTradeExecutor();
	}
	
	@Override
	public void onMarketData(MarketData marketData) {
		if ((getState() == StrategyState.Running) && (marketData instanceof Quote)) {
			AnalysisResult result = signalAnalyzer.analyze(marketData);
			if (result != null) {
				try {
					tradeExecutor.execute(result);
				} catch (Exception e) {
					log.error("failed to execute analysis result:{}", e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void onTimeEvent(AsyncEvent event) {
		log.info("receiving a TimeEvent, event: {}", ((TimeEvent) event).getEventName());
	}
	
}
