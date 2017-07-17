package com.huatai.platform.downstream.adaptor.ctp;

import java.util.List;

import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.type.ExchangeType;
import com.huatai.platform.downstream.adaptor.AbstractDownStreamAdaptor;
import com.huatai.platform.downstream.adaptor.IExchangeService;

public class CTPExchangeAdaptor extends AbstractDownStreamAdaptor {
	public CTPExchangeAdaptor(List<IExchangeService> exchangeServices, IDownStreamListener downstreamListener) {
		for (IExchangeService exchangeService : exchangeServices) {
			this.exchangeServices.put(exchangeService.getId(), exchangeService);
		}
	}
	
	@Override
	public boolean allReady() {
		return true;
	}
	
	@Override
	public ExchangeType[] getExchangeTypes() {
		return new ExchangeType[] { ExchangeType.SHF };
	}
	
}
