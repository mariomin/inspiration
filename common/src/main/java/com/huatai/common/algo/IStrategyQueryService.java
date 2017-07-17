package com.huatai.common.algo;

import com.huatai.common.business.ExchangeOrder;

public interface IStrategyQueryService {
	
	ExchangeOrder getExchangeOrder(String clOrdId);
	
}
