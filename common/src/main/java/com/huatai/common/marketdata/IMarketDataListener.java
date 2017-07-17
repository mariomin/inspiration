package com.huatai.common.marketdata;

import java.util.Set;

public interface IMarketDataListener {
	void onCodeTable(Set<String> symbols);
	
	void onState(boolean on);
	
	void onQuote(Quote quote);
	
	void onTrade(Trade trade);
	
	void onDepth(Depth depth);
	
	void onError(String err);
	
}
