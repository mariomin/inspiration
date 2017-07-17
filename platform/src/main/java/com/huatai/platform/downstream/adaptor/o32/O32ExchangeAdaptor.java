package com.huatai.platform.downstream.adaptor.o32;

import java.util.List;

import com.huatai.common.type.ExchangeType;
import com.huatai.platform.downstream.adaptor.AbstractDownStreamAdaptor;

public class O32ExchangeAdaptor extends AbstractDownStreamAdaptor {
	
	public O32ExchangeAdaptor(List<O32ExchangeService> o32ExchangeServices) {
		for (O32ExchangeService service : o32ExchangeServices) {
			exchangeServices.put(service.getId(), service);
		}
	}
	
	@Override
	public boolean allReady() {
		return true;
	}
	
	@Override
	public ExchangeType[] getExchangeTypes() {
		return new ExchangeType[] { ExchangeType.SH, ExchangeType.SZ };
	}
	
}
