package com.huatai.platform.downstream.adaptor.direct;

import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.exchange.common.business.BaseOrder;
import com.huatai.exchange.common.business.Order;

public class DirectUtil {
	
	public static BaseOrder.Side mapSide(OrderSide side) {
		if (side.equals(OrderSide.Buy))
			return BaseOrder.Side.BID;
		else return BaseOrder.Side.ASK;
	}
	
	public static Order.TYPE mapOrderType(OrderType type) {
		if (type.equals(OrderType.MARKET))
			return Order.TYPE.MARKET;
		else if (type.equals(OrderType.LIMIT))
			return Order.TYPE.LIMIT;
		else if (type.equals(OrderType.IOC))
			return Order.TYPE.FAK;
		else return null;
	}
}
