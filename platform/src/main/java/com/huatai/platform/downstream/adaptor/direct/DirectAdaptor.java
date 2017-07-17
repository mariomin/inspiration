package com.huatai.platform.downstream.adaptor.direct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamAdaptor;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.type.ExchangeType;
import com.huatai.exchange.Exchange;

public class DirectAdaptor implements IDownStreamAdaptor {
	private static final Logger log = LoggerFactory.getLogger(DirectAdaptor.class);
	private ExchangeType exchangeType = ExchangeType.SIM;
	private final DirectService service;
	
	public DirectAdaptor(Exchange exchange, IDownStreamListener downstreamListener) {
		service = new DirectService(exchange);
		service.setDownstreamListener(downstreamListener);
	}
	
	@Override
	public void init() throws Exception {
		log.info("initialising ");
		service.start();
	}
	
	@Override
	public void uninit() {
		service.stop();
	}
	
	@Override
	public boolean allReady() {
		return true;
	}
	
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	
	@Override
	public ExchangeType[] getExchangeTypes() {
		return new ExchangeType[] { exchangeType };
	}
	
	@Override
	public void sendNewOrder(ExchangeOrder order) {
		service.newOrder(order);
	}
	
	@Override
	public void sendCancelOrder(ExchangeOrder order) {
		service.cancelOrder(order);
	}
	
}
