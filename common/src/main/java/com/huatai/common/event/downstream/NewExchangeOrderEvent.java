package com.huatai.common.event.downstream;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.event.base.AsyncEvent;

public class NewExchangeOrderEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final ExchangeOrder order;
	
	public NewExchangeOrderEvent(String key, ExchangeOrder order) {
		super(key);
		this.order = order;
	}
	
	public ExchangeOrder getOrder() {
		return order;
	}
}
