package com.huatai.platform.downstream.adaptor.uf;

import org.junit.After;
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
import com.huatai.common.downstream.DownStreamException;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.OrderField;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.OrderType;
import com.huatai.common.util.RegularExpressionUtil;
import com.huatai.platform.downstream.TestDownStreamListener;

@Ignore
@ContextConfiguration(locations = { "classpath:com/huatai/downstream/adaptor/uf/adaptor.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class UFExchangeAdaptorTest {
	private static final String clientId = "033000001871";
	private static final String password = "203019";
	private static final String branchNo = "330";
	private static final String fundAccount = "330001871";
	private static final String securityAccount = "A141098178";
	
	private ExchangeOrder order;
	
	@Autowired
	private UFExchangeAdaptor ufExchangeAdaptor;
	
	@Autowired
	private TestDownStreamListener downStreamListener;
	
	@Before
	public void before() throws Exception {
		ufExchangeAdaptor.init();
		order = createExchangeOrder(clientId, password);
		downStreamListener.addExchangeOrder(order);
	}
	
	@After
	public void after() throws Exception {
		ufExchangeAdaptor.uninit();
	}
	
	@Test
	public void test() throws DownStreamException, InterruptedException {
		Thread.sleep(2000);
		ufExchangeAdaptor.sendNewOrder(order);
		//部分成交速度极慢，需要10秒左右
		Thread.sleep(20000);
		ufExchangeAdaptor.sendCancelOrder(order);
		Thread.sleep(5000);
	}
	
	public ExchangeOrder createExchangeOrder(String clientId, String password) {
		String symbol = "600036.SH";
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("investor001", "product001", fundAccount, AssetAccountType.cash),
				fundAccount, securityAccount);
		
		ExchangeOrder order = new ExchangeOrder(index, symbol, OrderSide.Buy, 6000.0, 5.0, OrderType.LIMIT, "11000011001");
		order.setSecurityAccount(securityAccount);
		order.setBranchNo(branchNo);
		order.setSecurityId(order.getSymbol().split(RegularExpressionUtil.dot)[0]);
		order.putField(OrderField.Password.name(), password);
		return order;
	}
}
