package com.huatai.common.backtest;

import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.exchange.common.business.BaseOrder;
import com.huatai.exchange.common.business.BaseOrder.Side;
import com.huatai.exchange.common.business.Order.STATUS;
import com.huatai.exchange.common.business.Order.TYPE;

public class DataTypeConvertor {
	
	public static BaseOrder.Side omsOrdSide2ExchangeOrdSide(OrderSide orderSide) {
		if (orderSide == OrderSide.Buy)
			return BaseOrder.Side.BID;
		else return BaseOrder.Side.ASK;
	}
	
	public static OrderSide exchangeOrdSide2OmsOrdSide(Side side) {
		if (side == Side.BID)
			return OrderSide.Buy;
		else return OrderSide.Sell;
	}
	
	public static OrderType exchangeOrdType2omsOrdType(TYPE type) {
		if (type == TYPE.MARKET)
			return OrderType.MARKET;
		else return OrderType.LIMIT;
	}
	
	public static OrdStatus exchangeOrdStatus2omsOrdStatus(STATUS status) {
		if (status == STATUS.NEW)
			return OrdStatus.NEW;
		else if (status == STATUS.CANCELLED)
			return OrdStatus.CANCELED;
		else if (status == STATUS.DONE)
			return OrdStatus.FILLED;
		else return OrdStatus.PENDING_NEW;
	}
	
}
