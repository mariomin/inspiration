package com.huatai.common.downstream;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.type.ExchangeType;

public interface IDownStreamAdaptor {
	
	void init() throws Exception;
	
	void uninit() throws Exception;
	
	boolean allReady();
	
	ExchangeType[] getExchangeTypes();
	
	void sendNewOrder(ExchangeOrder order);
	
	void sendCancelOrder(ExchangeOrder order);
}
