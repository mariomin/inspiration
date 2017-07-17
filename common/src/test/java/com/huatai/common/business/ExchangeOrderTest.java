package com.huatai.common.business;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.OrderUtil;

public class ExchangeOrderTest {
	
	@Test
	public void testBuySorting() {
		Map<String, Order> orders = new HashMap<String, Order>();
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("investor001", "product001", "ac", AssetAccountType.cash), "tc",
				"securityAccount");
		AlgoOrder algoOrder = new AlgoOrder(index, "000001.SZ", OrderSide.Buy, 2000d, 0d, OrderType.LIMIT, "clien0001");
		
		ExchangeOrder order = algoOrder.createExchangeOrder(200d, 50d, OrderType.LIMIT);
		orders.put(order.getId(), order);
		order = algoOrder.createExchangeOrder(400d, 49.5, OrderType.LIMIT);
		orders.put(order.getId(), order);
		order = algoOrder.createExchangeOrder(800d, 50.5, OrderType.LIMIT);
		orders.put(order.getId(), order);
		
		Set<Order> sortedOrders = OrderUtil.sortExchangeOrders(orders.values());
		Order prev = null;
		for (Order sortedOrder : sortedOrders) {
			if (prev == null) {
				prev = sortedOrder;
			} else {
				assertTrue(prev.getPrice() > sortedOrder.getPrice());
				prev = sortedOrder;
			}
		}
	}
	
	@Test
	public void testSellSorting() {
		Map<String, Order> orders = new HashMap<String, Order>();
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("investor001", "product001", "ac", AssetAccountType.cash), "tc",
				"securityAccount");
		AlgoOrder algoOrder = new AlgoOrder(index, "000001.SZ", OrderSide.Sell, 2000d, 0d, OrderType.MARKET, "client002");
		
		ExchangeOrder order = algoOrder.createExchangeOrder(200d, 50d, OrderType.LIMIT);
		orders.put(order.getId(), order);
		order = algoOrder.createExchangeOrder(400d, 49.5d, OrderType.LIMIT);
		orders.put(order.getId(), order);
		order = algoOrder.createExchangeOrder(800d, 50.5d, OrderType.LIMIT);
		orders.put(order.getId(), order);
		
		Set<Order> sortedOrders = OrderUtil.sortExchangeOrders(orders.values());
		Order prev = null;
		for (Order sortedOrder : sortedOrders) {
			if (prev == null) {
				prev = sortedOrder;
			} else {
				assertTrue(prev.getPrice() < sortedOrder.getPrice());
				prev = sortedOrder;
			}
		}
	}
}
