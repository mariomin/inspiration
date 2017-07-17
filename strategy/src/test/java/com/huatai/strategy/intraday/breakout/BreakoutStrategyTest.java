package com.huatai.strategy.intraday.breakout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.huatai.common.algo.SecurityAccountDetail;
import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.event.algo.CreateStrategyEvent;
import com.huatai.common.event.algo.StartStrategyEvent;
import com.huatai.common.index.AssetAccountIndex;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.marketdata.Trade;
import com.huatai.common.marketdata.event.quote.QuoteReplyEvent;
import com.huatai.common.marketdata.event.trade.TradeReplyEvent;
import com.huatai.common.marketdata.sim.SimQuote;
import com.huatai.common.marketdata.sim.SimTrade;
import com.huatai.common.type.AssetAccountType;
import com.huatai.common.type.Currency;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.StrategyState;
import com.huatai.common.util.GsonUtil;
import com.huatai.common.util.IdGenerator;
import com.huatai.platform.Platform;
import com.huatai.platform.algo.AlgoTradeManager;
import com.huatai.platform.algo.Strategy;
import com.huatai.platform.algo.StrategyContainer;
import com.huatai.strategy.intraday.common.PortfolioRecord;

@Ignore
@ContextConfiguration(locations = { "classpath:com/huatai/strategy/intraday/breakout/algo.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class BreakoutStrategyTest {
	private static final Logger log = LoggerFactory.getLogger(BreakoutStrategyTest.class);
	private static final IdGenerator idGenerator = IdGenerator.getInstance();
	
	@Autowired
	private Platform platform;
	
	@Autowired
	private AlgoTradeManager algoTradeManager;
	
	@Autowired
	private HashSet<String> symbols;
	
	@Before
	public void before() throws Exception {
		platform.init();
	}
	
	@After
	public void after() {
		platform.shutdown();
	}
	
	@Test
	public void testIntradayStrategy() throws Exception {
		StrategyContainer strategyContainer = null;
		Strategy<?, ?> strategy = null;
		
		// create NewStrategyEvent
		List<PortfolioRecord> universe = new ArrayList<PortfolioRecord>();
		for (String symbol : symbols) {
			PortfolioRecord record = new PortfolioRecord();
			record.setSymbol(symbol);
			universe.add(record);
			record.setInitPosition(10000);
		}
		
		// universe.add("000651.SZ");
		// universe.add("000793.SZ");
		// universe.add("002450.SZ");
		// universe.add("600050.SH");
		// universe.add("600150.SH");
		// universe.add("600588.SH");
		// universe.add("600737.SH");
		// universe.add("600827.SH");
		// universe.add("601668.SH");
		// universe.add("601718.SH");
		// universe.add("601186.SH");
		// universe.addAll(symbols);
		
		BreakoutStrategyParam strategyParams = new BreakoutStrategyParam();
		strategyParams.setUniverse(universe);
		strategyParams.setPressureRatePeriod(15);
		strategyParams.setBoundaryPointN1(30);
		strategyParams.setBoundaryPointN2(60);
		strategyParams.setBreakPointR(0.015d);
		strategyParams.setBreakPointP(0.5d);
		strategyParams.setWaveNum(5);
		strategyParams.setLowBoundary1(0.005d);
		strategyParams.setQtyStandardThreshold(1d);
		strategyParams.setParaRate(0.5d);
		strategyParams.setMinPressureRate(4d);
		strategyParams.setAskValueThreshold(150000d);
		strategyParams.setLowBoundary2(0.003d);
		strategyParams.setPressureUpdatePara(0.003d);
		strategyParams.setSignalParaP1(10);
		strategyParams.setSignalParaP2(0d);
		strategyParams.setSignalParaP3(15);
		strategyParams.setSignalParaP4(150000d);
		strategyParams.setSignalParaP5(0.5d);
		strategyParams.setSignalParaP6(2d);
		strategyParams.setSignalParaP7(0.003d);
		strategyParams.setSignalParaP8(50000);
		strategyParams.setSignalParaP9(4d);
		strategyParams.setSignalParaP10(0.09d);
		
		strategyParams.setBuyLevel(1);
		strategyParams.setBuyDeviation(0.01);
		strategyParams.setSellLevel(1);
		strategyParams.setSellDeviation(-0.01);
		strategyParams.setCloseOpenParaP1(2d);
		strategyParams.setCloseOpenParaP2(500000);
		strategyParams.setCloseOpenParaP3(0.2d);
		strategyParams.setCloseOpenParaP4(4);
		strategyParams.setCloseOpenParaP5(3);
		strategyParams.setAvgPriceLag(10);
		strategyParams.setEmaPressureRatioLag(10);
		strategyParams.setCloseMaxDrop(0.003d);
		
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("", "", "", AssetAccountType.cash), "", "");
		StrategyAlterCommand strategyCommand = new StrategyAlterCommand(index);
		List<SecurityAccountDetail> securityAccountDetails = new ArrayList<SecurityAccountDetail>();
		securityAccountDetails.add(new SecurityAccountDetail("testSecurityAccount", ExchangeType.SH, Currency.CNH));
		strategyCommand.setSecurityAccountDetails(securityAccountDetails);
		strategyCommand.setStrategy("BreakoutStrategy");
		strategyCommand.setStrategyParas(strategyParams);
		
		CreateStrategyEvent newStrategyEvent = new CreateStrategyEvent(null, "request1", strategyCommand);
		newStrategyEvent.setLoadAccount(false);
		newStrategyEvent.setLoadPosition(false);
		algoTradeManager.processCreateStrategyEvent(newStrategyEvent);
		Thread.sleep(7000);
		strategyContainer = algoTradeManager.getStrategyContainer(strategyCommand.getId());
		strategy = strategyContainer.getStrategy();
		Assert.assertEquals(strategy.getState(), StrategyState.Initializing);
		
		StrategyAlterCommand strategyAlterCommand = new StrategyAlterCommand(index);
		strategyAlterCommand.setStrategyId(strategyCommand.getId());
		StartStrategyEvent startStrategyEvent = new StartStrategyEvent(null, strategyAlterCommand);
		algoTradeManager.processStartStrategyEvent(startStrategyEvent);
		Assert.assertEquals(strategy.getState(), StrategyState.Running);
		
		BufferedReader br = null;
		try {
			File file = new File("src/test/java/com/huatai/algo/strategy/intraday/breakout/QuoteTrade.txt");
			//			File file = new File("D:/marketdata/md20170227.txt");
			FileInputStream fis = new FileInputStream(file);
			
			br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			Gson gson = GsonUtil.getGsonInstance();
			
			while ((line = br.readLine()) != null) {
				if (line.contains("Level")) {
					SimQuote sq = gson.fromJson(line, SimQuote.class);
					algoTradeManager.processQuoteReplyEvent(createQuoteReplyEvent(sq));
				} else {
					SimTrade st = gson.fromJson(line, SimTrade.class);
					algoTradeManager.processTradeReplyEvent(createTradeReplyEvent(st));
				}
			}
		} catch (Exception e) {
			log.error("error:", e);
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("error:", e);
				}
			}
		}
		Thread.sleep(1000);
	}
	
	private QuoteReplyEvent createQuoteReplyEvent(SimQuote sq) {
		Quote quote = SimQuote.createQuote(sq, idGenerator.getNextID());
		return new QuoteReplyEvent(null, quote);
	}
	
	private TradeReplyEvent createTradeReplyEvent(SimTrade st) {
		Trade trade = SimTrade.createTrade(st);
		return new TradeReplyEvent(null, trade);
	}
	
}
