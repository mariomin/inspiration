package com.huatai.platform.downstream.adaptor.uf;

import java.util.List;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.downstream.IDownStreamSender;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.OrderField;
import com.huatai.platform.downstream.adaptor.AbstractDownStreamAdaptor;
import com.huatai.platform.downstream.adaptor.IExchangeService;

public class UFExchangeAdaptor extends AbstractDownStreamAdaptor {
	
	public UFExchangeAdaptor(List<IExchangeService> services, IDownStreamListener downstreamListener) {
		for (IExchangeService service : services) {
			service.setDownstreamListener(downstreamListener);
			exchangeServices.put(service.getId(), service);
		}
	}
	
	@Override
	public ExchangeType[] getExchangeTypes() {
		return new ExchangeType[] { ExchangeType.SH, ExchangeType.SZ };
	}
	
	@Override
	public boolean allReady() {
		return true;
	}
	
	@Override
	public void sendNewOrder(ExchangeOrder order) {
		// TODO 需要替换
		order.putField(OrderField.ClientId.name(), "033000001871");
		order.putField(OrderField.Password.name(), "203019");
		selectExchangeService().newOrder(order);
	}
	
	@Override
	public void sendCancelOrder(ExchangeOrder order) {
		// TODO 需要替换
		order.putField(OrderField.ClientId.name(), "033000001871");
		order.putField(OrderField.Password.name(), "203019");
		selectExchangeService().cancelOrder(order);
	}
	
	private IDownStreamSender selectExchangeService() {
		int loc = (int) (Math.random() * exchangeServices.size());
		return (IDownStreamSender) (exchangeServices.values().toArray())[loc];
	}
	
}
