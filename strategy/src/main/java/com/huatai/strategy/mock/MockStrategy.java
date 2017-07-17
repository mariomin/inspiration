package com.huatai.strategy.mock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.algo.AtsStrategy;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.MarketData;
import com.huatai.platform.algo.Strategy;

@AtsStrategy(chineseName = "模拟示例", param = MockStrategyParams.class)
public class MockStrategy extends Strategy<MockStrategyParams, Object> {
	private final static Logger log = LoggerFactory.getLogger(MockStrategy.class);
	private ScheduledExecutorService scheduler;
	
	@Override
	public void init() {
		super.init();
		if (params.isAutoSendReport()) {
			scheduler = Executors.newSingleThreadScheduledExecutor();
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						tradeExecutor.execute(null);
					} catch (Exception e) {
						log.error("failed to execute analysis result:{}", e.getMessage());
					}
				}
			}, 10, 10, TimeUnit.SECONDS);
		}
	}
	
	@Override
	public void uninit() {
		if (null != scheduler) {
			scheduler.shutdownNow();
		}
	}
	
	@Override
	public void createSignalAnalyzer() {
		signalAnalyzer = new MockSignalAnalyzer(id);
	}
	
	@Override
	public void createTradeExecutor() {
		tradeExecutor = new MockTradeExecutor(id);
	}
	
	@Override
	public void subscribe() {}
	
	@Override
	public void onStateUpdated() {}
	
	@Override
	public void onMarketData(MarketData marketData) {}
	
	@Override
	public void onTimeEvent(AsyncEvent event) {}
	
}
