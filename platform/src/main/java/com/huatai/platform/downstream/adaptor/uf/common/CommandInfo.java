package com.huatai.platform.downstream.adaptor.uf.common;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.type.OrderAction;

public class CommandInfo {
	public ExchangeOrder order;
	public OrderAction action;
	
	public CommandInfo(OrderAction action, ExchangeOrder order) {
		this.action = action;
		this.order = order;
	}
}
