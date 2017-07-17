package com.huatai.common.algo;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.strategy.OrderMsg;

public interface IStrategyTradingService {
	
	OrderMsg placeNewOrder(ExchangeOrder exchangeOrder, Long timeout);
	
	OrderMsg cancelOrder(String tradingAccount, String securityAccount, String clOrdId, Long timeout);
	
	void sendStrategyReport(String report);
	
}
