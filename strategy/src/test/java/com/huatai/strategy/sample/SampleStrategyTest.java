package com.huatai.strategy.sample;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
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

import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.algo.CreateStrategyEvent;
import com.huatai.common.event.algo.StartStrategyEvent;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.event.quote.QuoteReplyEvent;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.StrategyState;
import com.huatai.platform.Platform;
import com.huatai.platform.algo.AlgoTradeManager;
import com.huatai.platform.algo.Strategy;
import com.huatai.platform.algo.StrategyContainer;

@Ignore
@ContextConfiguration(locations = { "classpath:com/huatai/strategy/sample/algo.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class SampleStrategyTest {
	@Autowired
	private Platform platform;
	
	@Autowired
	private AlgoTradeManager algoTradeManager;
	
	@Before
	public void before() throws Exception {
		platform.init();
	}
	
	@After
	public void after() {
		platform.shutdown();
	}
	
	@Test
	public void testSampleStrategy() throws Exception {
		StrategyContainer strategyContainer = null;
		Strategy<?, ?> strategy = null;
		
		// create NewStrategyEvent
		Set<String> universe = new HashSet<String>();
		universe.add("600001.SH");
		universe.add("600002.SH");
		Map<String, Double> buyLimitSet = new HashMap<String, Double>();
		buyLimitSet.put("600001.SH", 18.5d);
		buyLimitSet.put("600002.SH", 20.6d);
		SampleStrategyParams strategyParams = new SampleStrategyParams();
		strategyParams.setUniverse(universe);
		strategyParams.setBuyLimitSet(buyLimitSet);
		
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("", "", "", AssetAccountType.cash), "", "");
		StrategyAlterCommand autoTradeCommand = new StrategyAlterCommand(index);
		autoTradeCommand.setStrategy("SampleStrategy");
		autoTradeCommand.setStrategyParas(strategyParams);
		
		CreateStrategyEvent newStrategyEvent = new CreateStrategyEvent(null, "requestSampleStrategy", autoTradeCommand);
		newStrategyEvent.setLoadAccount(false);
		newStrategyEvent.setLoadPosition(false);
		algoTradeManager.processCreateStrategyEvent(newStrategyEvent);
		Thread.sleep(5000);
		strategyContainer = algoTradeManager.getStrategyContainer(autoTradeCommand.getId());
		strategy = strategyContainer.getStrategy();
		Assert.assertTrue(strategyContainer.getMarketDataSubscribeService().getQuoteSubscriptions("600001.SH").contains(strategyContainer.getId()));
		Assert.assertEquals(strategy.getState(), StrategyState.Initializing);
		// sending marketData to AlgoTradeManager before sending
		// StartStrategyEvent;
		algoTradeManager.processQuoteReplyEvent(createQuoteReplyEvent(18.5, 18.4, "600001.SH"));
		Thread.sleep(1000);
		
		// sending StartStrategyEvent
		StrategyAlterCommand strategyAlterCommand = new StrategyAlterCommand(index);
		strategyAlterCommand.setStrategyId(autoTradeCommand.getId());
		StartStrategyEvent startStrategyEvent = new StartStrategyEvent(null, strategyAlterCommand);
		algoTradeManager.processStartStrategyEvent(startStrategyEvent);
		Assert.assertEquals(strategy.getState(), StrategyState.Running);
		algoTradeManager.processQuoteReplyEvent(createQuoteReplyEvent(18.5, 18.4, "600001.SH"));
	}
	
	private QuoteReplyEvent createQuoteReplyEvent(double ask, double bid, String symbol) {
		Quote quote = new Quote(symbol);
		quote.setAsk(ask);
		quote.setBid(bid);
		return new QuoteReplyEvent(null, quote);
	}
}
