package com.huatai.strategy.mock;

import java.util.List;

import com.huatai.common.marketdata.MarketData;
import com.huatai.platform.algo.SignalAnalyzer;

public class MockSignalAnalyzer extends SignalAnalyzer<Object> {
	
	public MockSignalAnalyzer(String strategyId) {
		super();
	}
	
	@Override
	public void init() {}
	
	@Override
	public List<?> analyze(MarketData marketData) {
		return null;
	}
	
	@Override
	public void uninit() {}
	
}
