package com.huatai.common.backtest;

import java.util.Collection;
import java.util.Map;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.marketdata.TimeFrame;

public interface ITradingService extends IBackTestService {
	
	String placeOrder(ExchangeOrder exchangeOrder);
	
	boolean cancelOrder(String orderId);
	
	double getBaseCash();
	
	double getAvailableCash();
	
	double getTotalCash();
	
	double getHoldingQty(String symbol);
	
	double getTradableQty(String symbol);
	
	Collection<String> getPositionSymbols();
	
	void updateAvailablePosition();
	
	void cancelYesterdayOrders();
	
	void updatePnL(TimeFrame timeFrame, IMarketDataCalculateService iMarketDataCalculateService);
	
	void bindClock(Clock clock);
	
	Map<String, ExchangeOrder> getExchangeOrders();
	
	boolean isPlacedOrder();
	
	void resetPlacedOrder();
}
