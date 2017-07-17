package com.huatai.common.strategy;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.type.StrategyState;

public interface IStrategy {
	
	void init() throws StrategyException;
	
	void uninit();
	
	void onMarketData(MarketData marketData);
	
	void onTimeEvent(AsyncEvent event);
	
	void onNewOrderRejected(String clOrdId, String symbol, String text);
	
	void onCancelOrderRejected(String clOrdId, String symbol, String text);
	
	void onOrderUpdated(ExchangeOrder order);
	
	void onFundUpdated(TradingFund fundaccount);
	
	void onPositionUpdated(TradingPosition position);
	
	void createSignalAnalyzer();
	
	void createTradeExecutor();
	
	void subscribe();
	
	void onStateUpdated();
	
	StrategyState getState();
	
	void setState(StrategyState state);
	
	String getName();
	
	String getId();
}
