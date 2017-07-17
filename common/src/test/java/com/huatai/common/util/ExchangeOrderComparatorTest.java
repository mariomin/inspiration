package com.huatai.common.util;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.business.Order;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;

public class ExchangeOrderComparatorTest {
	private static final Logger log = LoggerFactory.getLogger(ExchangeOrderComparatorTest.class);
	
	@Test
	public void testSell() throws InterruptedException {
		Set<Order> orders = new TreeSet<Order>(OrderUtil.exchangeOrderComparator);
		SecurityTradingIndex index = new SecurityTradingIndex(
				new AssetAccountIndex("investor001", "product001", "assetAccount", AssetAccountType.cash), "tradingAccount", "securityAccount");
		ExchangeOrder order1 = new ExchangeOrder(index, "000001.SZ", OrderSide.Sell, 80000d, 68.4, OrderType.LIMIT, "");
		ExchangeOrder order2 = new ExchangeOrder(index, "000001.SZ", OrderSide.Sell, 80000d, 68.5, OrderType.LIMIT, "");
		ExchangeOrder order3 = new ExchangeOrder(index, "000001.SZ", OrderSide.Sell, 80000d, 68.4, OrderType.LIMIT, "");
		ExchangeOrder order4 = new ExchangeOrder(index, "000001.SZ", OrderSide.Sell, 80000d, 68.4, OrderType.LIMIT, "");
		ExchangeOrder order5 = new ExchangeOrder(index, "000001.SZ", OrderSide.Sell, 80000d, 68.4, OrderType.LIMIT, "");
		ExchangeOrder order6 = new ExchangeOrder(index, "000001.SZ", OrderSide.Sell, 80000d, 68.3, OrderType.LIMIT, "");
		
		orders.add(order2);
		orders.add(order4);
		orders.add(order5);
		orders.add(order1);
		orders.add(order3);
		orders.add(order6);
		
		Order prev = null;
		log.info("--- Sell ---");
		for (Order order : orders) {
			log.info("{} : {}", order.getPrice(), order.getCreated().getTime());
			if (null != prev) {
				if (DecimalUtil.equal(prev.getPrice(), order.getPrice())) {
					assert (order.getCreated().getTime() >= prev.getCreated().getTime());
				} else {
					assert (prev.getPrice() < order.getPrice());
				}
			}
		}
	}
	
	@Test
	public void testBuy() throws InterruptedException {
		Set<Order> orders = new TreeSet<Order>(OrderUtil.exchangeOrderComparator);
		SecurityTradingIndex index = new SecurityTradingIndex(
				new AssetAccountIndex("investor001", "product001", "assetAccount", AssetAccountType.cash), "tradingAccount", "securityAccount");
		
		ExchangeOrder order1 = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 80000d, 68.4, OrderType.LIMIT, "");
		
		Thread.sleep(1);
		ExchangeOrder order2 = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 80000d, 68.5, OrderType.LIMIT, "");
		
		Thread.sleep(1);
		ExchangeOrder order3 = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 80000d, 68.4, OrderType.LIMIT, "");
		
		Thread.sleep(1);
		ExchangeOrder order4 = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 80000d, 68.4, OrderType.LIMIT, "");
		
		Thread.sleep(1);
		ExchangeOrder order5 = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 80000d, 68.4, OrderType.LIMIT, "");
		
		Thread.sleep(1);
		ExchangeOrder order6 = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 80000d, 68.3, OrderType.LIMIT, "");
		
		orders.add(order5);
		orders.add(order1);
		orders.add(order2);
		orders.add(order4);
		orders.add(order3);
		orders.add(order6);
		
		Order prev = null;
		log.info("--- Buy ---");
		for (Order order : orders) {
			log.info("{} : {}", order.getPrice(), order.getCreated().getTime());
			if (null != prev) {
				if (DecimalUtil.equal(prev.getPrice(), order.getPrice())) {
					assert (order.getCreated().getTime() >= prev.getCreated().getTime());
				} else {
					assert (prev.getPrice() > order.getPrice());
				}
			}
		}
	}
	
}
