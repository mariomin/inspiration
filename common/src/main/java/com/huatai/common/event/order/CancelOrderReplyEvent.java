package com.huatai.common.event.order;

import com.huatai.common.business.Order;

public class CancelOrderReplyEvent extends OrderReplyEvent {
	private static final long serialVersionUID = 1L;
	
	public CancelOrderReplyEvent(String key, boolean ok, String message, String origClOrdId, String clOrdId, Order order) {
		super(key, origClOrdId, ok, message, clOrdId, order);
	}
	
}
