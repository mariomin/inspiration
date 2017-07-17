package com.huatai.platform.downstream.adaptor.ctp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huatai.common.business.ExchangeOrder;

@Ignore
@ContextConfiguration(locations = { "file:ats/downstream/test/com/huatai/downstream/adaptor/ctp/adaptor.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCTPExchangeAdaptor {
	
	@Autowired
	private CTPExchangeAdaptor exchangeAdaptor;
	
	private ExchangeOrder createExchangeOrder() {
		ExchangeOrder order = new ExchangeOrder();
		order.setSymbol("IF1703");
		return order;
	}
	
	@Test
	public void testCTP() throws InterruptedException {
		ExchangeOrder order = createExchangeOrder();
		exchangeAdaptor.sendNewOrder(order);
		Thread.currentThread().join();
	}
	
}
