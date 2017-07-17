package com.huatai.common.algo;

import com.huatai.common.marketdata.MarketData;

public interface ISignalAnalyzer<V> {
	
	void init();
	
	void uninit();
	
	V analyze(MarketData marketData);
}
