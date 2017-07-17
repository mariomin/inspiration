package com.huatai.common.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import com.huatai.common.business.AlgoOrder;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.business.Order;
import com.huatai.common.type.ExecType;
import com.huatai.common.type.OrderField;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.exchange.common.business.BaseOrder.Side;
import com.huatai.exchange.common.business.Order.TYPE;

public class OrderUtil {
	
	public static final Comparator<Order> exchangeOrderComparator = new Comparator<Order>() {
		
		@Override
		public int compare(Order x, Order y) {
			int result = PriceUtil.compareBySide(x.getOrderSide(), x.getPrice(), y.getPrice());
			
			if (result == 0) {
				result = x.getCreated().compareTo(y.getCreated());
				if (result == 0) {
					result = x.getId().compareTo(y.getId());
				}
			}
			return result;
		}
	};
	
	public static boolean inLimit(double price, double limit, OrderSide side) {
		if (DecimalUtil.equal(limit, 0)) return true;
		
		if (side.equals(OrderSide.Buy))
			return DecimalUtil.equalLessThan(price, limit);
		else return DecimalUtil.equalGreaterThan(price, limit);
	}
	
	public static Map<OrderField, Object> updateOrderWithFields(Order order, Map<OrderField, Object> fields) {
		if ((order == null) || (fields == null) || fields.isEmpty()) return null;
		
		Map<OrderField, Object> results = new HashMap<OrderField, Object>();
		PropertyAccessor accessor = PropertyAccessorFactory.forBeanPropertyAccess(order);
		for (Entry<OrderField, Object> entry : fields.entrySet()) {
			OrderField key = entry.getKey();
			results.put(key, accessor.getPropertyValue(key.name()));
			accessor.setPropertyValue(key.name(), entry.getValue());
		}
		return results;
	}
	
	public static boolean checkNeedFixUpdate(ExecType execType, Collection<String> fixCheckItems, AlgoOrder oldOrder, AlgoOrder newOrder) {
		if (!(execType.equals(ExecType.RESTATED) || execType.equals(ExecType.REPLACE))) return true;
		
		PropertyAccessor oldOrderAccessor = PropertyAccessorFactory.forBeanPropertyAccess(oldOrder);
		PropertyAccessor newOrderAccessor = PropertyAccessorFactory.forBeanPropertyAccess(newOrder);
		
		for (String key : fixCheckItems) {
			Object obj1 = oldOrderAccessor.getPropertyValue(key);
			Object obj2 = newOrderAccessor.getPropertyValue(key);
			if (!obj1.equals(obj2)) return true;
		}
		
		return false;
	}
	
	public static Set<Order> sortExchangeOrders(Collection<Order> orders) {
		Set<Order> set = new TreeSet<Order>(exchangeOrderComparator);
		for (Order order : orders) {
			set.add(order);
		}
		return set;
	}
	
	public static boolean isBetterPrice(OrderSide side, double p1, double p2) {
		if (DecimalUtil.equal(p2, 0)) // consider as no limit
			return true;
		if (side.isBuy())
			return DecimalUtil.equalLessThan(p1, p2);
		else return DecimalUtil.equalGreaterThan(p1, p2);
	}
	
	public static com.huatai.exchange.common.business.Order createFromExchangeOrder(ExchangeOrder exchangeOrder, String broker, String PBU) {
		String symbol = exchangeOrder.getSymbol();
		TYPE type = exchangeOrder.getOrderType() == OrderType.LIMIT ? TYPE.LIMIT : TYPE.MARKET;
		Side side = exchangeOrder.getOrderSide() == OrderSide.Buy ? Side.BID : Side.ASK;
		Double quantity = exchangeOrder.getQuantity();
		Double price = exchangeOrder.getPrice();
		
		return new com.huatai.exchange.common.business.Order(symbol, type, side, quantity, price, broker, PBU);
	}
}
