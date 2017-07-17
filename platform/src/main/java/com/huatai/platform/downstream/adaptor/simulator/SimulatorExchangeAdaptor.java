package com.huatai.platform.downstream.adaptor.simulator;

import java.io.UnsupportedEncodingException;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.downstream.IDownStreamAdaptor;
import com.huatai.common.downstream.IDownStreamListener;
import com.huatai.common.downstream.fix.IFixReceiver;
import com.huatai.common.fix.FixAdaptor;
import com.huatai.common.fix.FixException;
import com.huatai.common.type.ExchangeType;

import quickfix.SessionID;

public class SimulatorExchangeAdaptor extends FixAdaptor<IFixReceiver> implements IDownStreamAdaptor {
	private ExchangeType exchangeType = ExchangeType.SIM;
	private final IDownStreamListener downstreamListener;
	private SimulatorExchangeService service;
	
	public SimulatorExchangeAdaptor(String fixSettings, IDownStreamListener downstreamListener) throws FixException, UnsupportedEncodingException {
		super(fixSettings);
		this.downstreamListener = downstreamListener;
	}
	
	@Override
	protected IFixReceiver createFixSession(SessionID session) {
		service = new SimulatorExchangeService(session);
		service.setDownstreamListener(downstreamListener);
		return service;
	}
	
	@Override
	public void init() throws FixException {
		super.init();
	}
	
	@Override
	public void uninit() {
		super.uninit();
	}
	
	@Override
	public ExchangeType[] getExchangeTypes() {
		return new ExchangeType[] { exchangeType };
	}
	
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	
	@Override
	public boolean allReady() {
		return true;
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
