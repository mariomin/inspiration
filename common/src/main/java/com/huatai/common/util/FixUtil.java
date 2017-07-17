package com.huatai.common.util;

import com.huatai.common.fix.FixConvertionException;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;

import quickfix.field.OrdType;
import quickfix.field.Side;

public class FixUtil {
	
	public static char toFixOrderSide(OrderSide side) throws FixConvertionException {
		if (side == OrderSide.Buy)
			return Side.BUY;
		else if (side == OrderSide.Sell)
			return Side.SELL;
		else throw new FixConvertionException("toFixOrderSide: cant map side " + side);
	}
	
	public static OrderSide fromFixOrderSide(char side) throws FixConvertionException {
		if (side == Side.BUY)
			return OrderSide.Buy;
		else if (side == Side.SELL)
			return OrderSide.Sell;
		else if (side == Side.UNDISCLOSED)
			return OrderSide.Undisclosed;
		else throw new FixConvertionException("fromFixOrderSide: cant map side " + side);
	}
	
	public static char toFixExchangeOrderType(OrderType type) throws FixConvertionException {
		if (type == OrderType.MARKET)
			return OrdType.MARKET;
		else if (type == OrderType.LIMIT)
			return OrdType.LIMIT;
		else throw new FixConvertionException("toFixExchangeOrderType: cant map ExchangeOrderType " + type);
	}
	
	public static OrderType fromFixExchangeOrderType(char type) throws FixConvertionException {
		if (type == OrdType.MARKET)
			return OrderType.MARKET;
		else if (type == OrdType.LIMIT)
			return OrderType.LIMIT;
		else throw new FixConvertionException("fromFixExchangeOrderType: cant map type " + type);
	}
	
	public static char toFixOrderType(OrderType type) throws FixConvertionException {
		if (type == OrderType.MARKET)
			return OrdType.MARKET;
		else if (type == OrderType.LIMIT)
			return OrdType.LIMIT;
		else throw new FixConvertionException("toFixOrderType: cant map OrderType " + type);
	}
	
	public static OrderType fromFixOrderType(char type) throws FixConvertionException {
		if ((type == OrdType.MARKET) || (type == 'U'))
			return OrderType.MARKET;
		else if (type == OrdType.LIMIT)
			return OrderType.LIMIT;
		else if (type == OrdType.ON_BASIS)
			return OrderType.ON_BASIS;
		else throw new FixConvertionException("fromFixOrderType: cant map type " + type);
	}
	
}
