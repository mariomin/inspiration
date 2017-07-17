package com.huatai.strategy.intraday;

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
@ContextConfiguration(locations = { "classpath:com/huatai/strategy/intraday/algo.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class IntradayStrategyTest {
	private static final Logger log = LoggerFactory.getLogger(IntradayStrategyTest.class);
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
		
		IntradayStrategyParam strategyParams = new IntradayStrategyParam();
		strategyParams.setUniverse(universe);
		strategyParams.setBreakout_pressureRatePeriod(15);
		strategyParams.setBreakout_boundaryPointN1(30);
		strategyParams.setBreakout_boundaryPointN2(60);
		strategyParams.setBreakout_breakPointR(0.015d);
		strategyParams.setBreakout_breakPointP(0.5d);
		strategyParams.setBreakout_waveNum(5);
		strategyParams.setBreakout_lowBoundary1(0.005d);
		strategyParams.setBreakout_qtyStandardThreshold(1d);
		strategyParams.setBreakout_paraRate(0.5d);
		strategyParams.setBreakout_minPressureRate(4d);
		strategyParams.setBreakout_askValueThreshold(150000d);
		strategyParams.setBreakout_lowBoundary2(0.003d);
		strategyParams.setBreakout_pressureUpdatePara(0.003d);
		strategyParams.setBreakout_signalParaP1(10);
		strategyParams.setBreakout_signalParaP2(0d);
		strategyParams.setBreakout_signalParaP3(15);
		strategyParams.setBreakout_signalParaP4(150000d);
		strategyParams.setBreakout_signalParaP5(0.5d);
		strategyParams.setBreakout_signalParaP6(2d);
		strategyParams.setBreakout_signalParaP7(0.003d);
		strategyParams.setBreakout_signalParaP8(50000);
		strategyParams.setBreakout_signalParaP9(4d);
		strategyParams.setBreakout_signalParaP10(0.09d);
		strategyParams.setBreakout_avgPriceLag(10);
		strategyParams.setBreakout_emaPressureRatioLag(10);
		strategyParams.setBreakout_closeMaxDrop(0.003d);
		
		strategyParams.setReverse_boundaryPointN1(30);
		strategyParams.setReverse_boundaryPointN2(60);
		strategyParams.setReverse_waveNum(5);
		strategyParams.setReverse_pressureRatePeriod(15);
		strategyParams.setReverse_emaPressureRatioLag(10);
		strategyParams.setReverse_orderPressureN(5);
		strategyParams.setReverse_orderPressureLag(20);
		strategyParams.setReverse_avgPriceLag(10);
		strategyParams.setReverse_emaOrderPressureTriggerRate(2);
		strategyParams.setReverse_triggerPressure(0);
		strategyParams.setReverse_minSupportAmount(20);
		strategyParams.setReverse_minSupportRate(2);
		strategyParams.setReverse_minPierceNum(2);
		strategyParams.setReverse_dyRatio(0);
		strategyParams.setReverse_dyRatio2(3);
		strategyParams.setReverse_dyAmountRatio(0);
		strategyParams.setReverse_dropRate(4);
		strategyParams.setReverse_volumeRate(0);
		strategyParams.setReverse_speedRate(1);
		strategyParams.setReverse_revLag(15);
		strategyParams.setReverse_minPrice(4);
		strategyParams.setReverse_indexCode("000300.SH");
		strategyParams.setReverse_paraFast(60);
		strategyParams.setReverse_paraSlow(120);
		strategyParams.setReverse_speedLag(30);
		strategyParams.setReverse_indexDropSpeedRate(-0.5);
		strategyParams.setReverse_volumePeriod(60);
		strategyParams.setReverse_avgPriceLag(10);
		strategyParams.setReverse_emaPressureRatioLag(10);
		strategyParams.setReverse_closeMaxDrop(0.002d);
		
		strategyParams.setBuyLevel(1);
		strategyParams.setBuyDeviation(0.01);
		strategyParams.setSellLevel(1);
		strategyParams.setSellDeviation(-0.01);
		strategyParams.setCloseOpenParaP1(2d);
		strategyParams.setCloseOpenParaP2(500000);
		strategyParams.setCloseOpenParaP3(0.2d);
		strategyParams.setCloseOpenParaP4(4);
		strategyParams.setCloseOpenParaP5(3);
		
		SecurityTradingIndex index = new SecurityTradingIndex(new AssetAccountIndex("", "", "", AssetAccountType.cash), "", "");
		StrategyAlterCommand strategyCommand = new StrategyAlterCommand(index);
		List<SecurityAccountDetail> securityAccountDetails = new ArrayList<SecurityAccountDetail>();
		securityAccountDetails.add(new SecurityAccountDetail("testSecurityAccount", ExchangeType.SH, Currency.CNH));
		strategyCommand.setSecurityAccountDetails(securityAccountDetails);
		strategyCommand.setStrategy("IntradayStrategy");
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
			File file = new File("src/test/java/com/huatai/algo/strategy/intraday/QuoteTrade.txt");
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
