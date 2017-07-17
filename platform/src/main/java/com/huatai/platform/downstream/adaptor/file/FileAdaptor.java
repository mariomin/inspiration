package com.huatai.platform.downstream.adaptor.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamAdaptor;
import com.huatai.common.type.ExchangeType;

public class FileAdaptor implements IDownStreamAdaptor {
	private static final Logger log = LoggerFactory.getLogger(FileAdaptor.class);
	private ExchangeType exchangeType = ExchangeType.SIM;
	private final FileService service;
	private final String name;
	
	public FileAdaptor(String name) throws Exception {
		this.name = name;
		service = new FileService(name);
	}
	
	@Override
	public void init() throws Exception {
		log.info("initialising " + name);
		service.start();
	}
	
	@Override
	public void uninit() throws Exception {
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
