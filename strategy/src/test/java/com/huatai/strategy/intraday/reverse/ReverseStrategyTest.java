package com.huatai.strategy.intraday.reverse;

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
@ContextConfiguration(locations = { "classpath:com/huatai/strategy/intraday/reverse/algo.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class ReverseStrategyTest {
	private static final Logger log = LoggerFactory.getLogger(ReverseStrategyTest.class);
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
		
		//		PortfolioRecord record = new PortfolioRecord();
		//		record.setSymbol("000826.SZ");
		//		record.setInitPosition(10000);
		//		universe.add(record);
		
		for (String symbol : symbols) {
			PortfolioRecord record = new PortfolioRecord();
			record.setSymbol(symbol);
			universe.add(record);
			record.setInitPosition(10000);
		}
		
		ReverseStrategyParam strategyParams = new ReverseStrategyParam();
		strategyParams.setUniverse(universe);
		strategyParams.setBoundaryPointN1(30);
		strategyParams.setBoundaryPointN2(60);
		strategyParams.setWaveNum(5);
		
		strategyParams.setPressureRatePeriod(15);
		strategyParams.setEmaPressureRatioLag(10);
		strategyParams.setOrderPressureN(5);
		strategyParams.setOrderPressureLag(20);
		strategyParams.setAvgPriceLag(10);
		strategyParams.setEmaOrderPressureTriggerRate(2);
		strategyParams.setTriggerPressure(0);
		strategyParams.setMinSupportAmount(20);
		strategyParams.setMinSupportRate(2);
		strategyParams.setMinPierceNum(2);
		strategyParams.setDyRatio(0);
		strategyParams.setDyRatio2(3);
		strategyParams.setDyAmountRatio(0);
		strategyParams.setDropRate(4);
		strategyParams.setVolumeRate(0);
		strategyParams.setSpeedRate(1);
		strategyParams.setRevLag(15);
		strategyParams.setMinPrice(4);
		
		strategyParams.setIndexCode("000300.SH");
		strategyParams.setParaFast(60);
		strategyParams.setParaSlow(120);
		strategyParams.setSpeedLag(30);
		strategyParams.setIndexDropSpeedRate(-0.5);
		strategyParams.setVolumePeriod(60);
		
		strategyParams.setBuyLevel(-1);
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
		strategyParams.setCloseMaxDrop(0.002d);
		
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("", "", "", AssetAccountType.cash), "", "");
		StrategyAlterCommand strategyCommand = new StrategyAlterCommand(index);
		List<SecurityAccountDetail> securityAccountDetails = new ArrayList<SecurityAccountDetail>();
		securityAccountDetails.add(new SecurityAccountDetail("testSecurityAccount", ExchangeType.SH, Currency.CNH));
		strategyCommand.setSecurityAccountDetails(securityAccountDetails);
		strategyCommand.setStrategy("ReverseStrategy");
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
			File file = new File("src/test/java/com/huatai/algo/strategy/intraday/reverse/QuoteTrade.txt");
			//			File file = new File("D:/marketdata/md20170411.txt");
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
