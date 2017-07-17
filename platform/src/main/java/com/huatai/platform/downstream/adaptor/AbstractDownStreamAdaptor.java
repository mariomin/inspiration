package com.huatai.platform.downstream.adaptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamAdaptor;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.downstream.IDownStreamSender;

public abstract class AbstractDownStreamAdaptor implements IDownStreamAdaptor {
	protected Map<String, IExchangeService> exchangeServices = new ConcurrentHashMap<String, IExchangeService>();
	
	@Autowired
	private IDownStreamListener downstreamListener;
	
	@Override
	public void init() throws Exception {
		for (IExchangeService service : exchangeServices.values()) {
			service.start();
		}
	}
	
	@Override
	public void uninit() {
		for (IExchangeService service : exchangeServices.values()) {
			service.stop();
		}
		exchangeServices.clear();
	}
	
	@Override
	public void sendNewOrder(ExchangeOrder order) {
		IDownStreamSender exchangeService = selectExchangeService(order);
		
		if (exchangeService.addReportNoIfAbsent(order.getReportNo())) {
			exchangeService.newOrder(order);
		} else {
			downstreamListener.onDuplicateNewOrder(order.getReportNo());
		}
	}
	
	@Override
	public void sendCancelOrder(ExchangeOrder order) {
		IDownStreamSender exchangeService = selectExchangeService(order);
		if (exchangeService.addReportNoIfAbsent(order.getCancelReportNo())) {
			exchangeService.cancelOrder(order);
		} else {
			
			downstreamListener.onDuplicateCancelOrder(order.getCancelReportNo());
			
		}
		
	}
	
	/**
	 * TODO 需要根据账号以及PBU等信息进行路由，确保报单和撤单路由到同一个ExchangeService上
	 * @param order
	 * @return
	 */
	protected IDownStreamSender selectExchangeService(ExchangeOrder order) {
		int loc = (int) (Math.random() * exchangeServices.size());
		return (IDownStreamSender) (exchangeServices.values().toArray())[loc];
	}
	
}
