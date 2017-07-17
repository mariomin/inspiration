package com.huatai.common.marketdata.event.trade;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.Trade;

public class TradeReplyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final Trade trade;
	
	public TradeReplyEvent(String key, Trade trade) {
		super(key);
		this.trade = trade;
	}
	
	public Trade getTrade() {
		return trade;
	}
	
}
