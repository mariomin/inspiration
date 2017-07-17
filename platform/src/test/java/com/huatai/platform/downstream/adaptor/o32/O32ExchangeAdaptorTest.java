package com.huatai.platform.downstream.adaptor.o32;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.platform.downstream.TestDownStreamListener;

@Ignore
@ContextConfiguration(locations = { "file:ats/downstream/test/com/huatai/downstream/adaptor/o32/adaptor-o32.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class O32ExchangeAdaptorTest {
	
	@Autowired
	private O32ExchangeAdaptor o32Adaptor;
	
	@Autowired
	private TestDownStreamListener downstreamListener;
	
	@Before
	public void setUp() throws Exception {
		o32Adaptor.init();
	}
	
	@Test
	public void testNewOrder() throws InterruptedException {
		String clOrdId = "123";
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("3162", "830002", "2102", AssetAccountType.cash), "2102a", null);
		ExchangeOrder order = new ExchangeOrder(index, "000001.SZ", OrderSide.Buy, 100D, 8.5, OrderType.LIMIT, clOrdId);
		order.setId("111111");
		order.setSecurityId("000001");
		order.setPrice(10.0);
		order.setQuantity(100D);
		order.setReportNo("123");
		
		downstreamListener.addExchangeOrder(order);
		o32Adaptor.sendNewOrder(order);
		ExchangeOrder actualOrder = downstreamListener.getExchangeOrderByReportNo(order.getReportNo());
		
		Thread.sleep(6000);
		
		actualOrder = downstreamListener.getExchangeOrderByReportNo(order.getReportNo());
		System.out.println("OrdStatus=" + actualOrder.getOrdStatus());
		System.out.println("Order=" + actualOrder);
		
		Assert.assertTrue(com.huatai.common.type.OrdStatus.FILLED == actualOrder.getOrdStatus());
		
	}
	
}
