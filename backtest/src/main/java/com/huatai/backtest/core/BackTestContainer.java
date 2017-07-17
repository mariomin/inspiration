package com.huatai.backtest.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.algo.IStrategyQueryService;
import com.huatai.common.algo.IStrategyTradingService;
import com.huatai.common.backtest.BenchMark;
import com.huatai.common.backtest.Clock;
import com.huatai.common.backtest.IBackTestMarketDataLoader;
import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.TimeUtil;
import com.huatai.common.backtest.report.BackTestRequest;
import com.huatai.common.backtest.report.BenchMarkRecord;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.CandleStick;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.strategy.OrderMsg;
import com.huatai.common.strategy.StrategyException;
import com.huatai.common.type.StrategyState;
import com.huatai.exchange.Exchange;
import com.huatai.platform.algo.Container;
import com.huatai.platform.algo.EsperEngine;
import com.huatai.platform.algo.Strategy;

public class BackTestContainer extends Container implements IStrategyTradingService, IStrategyQueryService {
	private static final Logger log = LoggerFactory.getLogger(BackTestContainer.class);
	private Exchange exchange;
	
	private BackTestRequest backTestRequest;
	private BackTestTradingService backTestTradingService;
	private List<MarketData> marketDatas;
	private IMarketDataCalculateService mdCalService;
	private IBackTestMarketDataLoader backTestMarketDataLoader;
	
	private final Clock clock = new Clock();
	private final Map<String, List<BenchMarkRecord>> benchMarks = new HashMap<String, List<BenchMarkRecord>>();
	private final Map<String, MarketData> historicalMarketDatas = new HashMap<String, MarketData>();
	
	private class BackTestTask implements Runnable {
		
		@Override
		public void run() {
			strategy.setState(StrategyState.Running);
			
			if (marketDatas == null) return;
			MarketData previousMarketData = null;
			for (int i = 0; (i < marketDatas.size()) && (getStrategyState() == StrategyState.Running); i++) {
				MarketData marketData = marketDatas.get(i);
				
				// 1. reset trade status && update clock
				backTestTradingService.resetPlacedOrder();
				clock.setManualClock(marketData.getTimestamp());
				
				// 2. update BenchMark
				if (benchMarks.containsKey(marketData.getSymbol())) {
					List<BenchMarkRecord> records = benchMarks.get(marketData.getSymbol());
					if (marketData instanceof Quote) {
						records.add(new BenchMarkRecord(marketData.getTimestamp(), ((Quote) marketData).getLastPx()));
					} else if (marketData instanceof CandleStick) {
						records.add(new BenchMarkRecord(marketData.getTimestamp(), ((CandleStick) marketData).getClosePx()));
					}
					continue;
				}
				
				// 3. MarketDataCalculateService update its data set;
				mdCalService.updateHistoricalData(marketData, historicalMarketDatas.get(marketData.getSymbol()));
				
				// 4. TradingService update the status of each position
				if ((previousMarketData != null) && TimeUtil.isDifferentDay(previousMarketData.getTimestamp(), marketData.getTimestamp())) {
					backTestTradingService.cancelYesterdayOrders();
					backTestTradingService.updateAvailablePosition();
				}
				
				// 5. Invoke the onMarketData() method of strategy
				strategy.onMarketData(marketData);
				
				// 6. Match order in exchange
				if (backTestTradingService.isPlacedOrder()) {
					backTestTradingService.getExchange().makeMatch();
				}
				
				// 7. update PnL
				if ((previousMarketData != null) && TimeUtil.isDifferentTimeFrame(previousMarketData.getTimestamp(), marketData.getTimestamp(),
						backTestRequest.getReportTimeFrame())) {
					backTestTradingService.updatePnL(backTestRequest.getBackTestTimeFrame(), mdCalService);
				}
				
				// 8. update historicalMarketDatas && previousMarketData
				historicalMarketDatas.put(marketData.getSymbol(), marketData);
				previousMarketData = marketData;
			}
			
			stop();
			uninit();
		}
		
	}
	
	@Override
	public void start() {
		StrategyState state = getStrategyState();
		if (state == StrategyState.Initializing) {
			super.start();
			new Thread(new BackTestTask()).start();
		}
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void onEvent(AsyncEvent event) {
		eventProcessor.onEvent(event);
	}
	
	@Override
	public void init() throws StrategyException {
		super.init();
		
		backTestTradingService = new BackTestTradingService(exchange, backTestRequest.getUniverse(), backTestRequest.getBaseCash(),
				backTestRequest.getCommission(), backTestRequest.getStampDuty());
		backTestTradingService.setEventListener(this);
		backTestTradingService.bindClock(clock);
		backTestTradingService.init();
		
		mdCalService = new BackTestMarketDataCalculateService(backTestRequest.getBackTestTimeFrame());
		
		try {
			backTestMarketDataLoader.init();
			mdCalService.init();
		} catch (Exception e) {
			log.error("BackTestContainer init failed{}", e.getMessage());
		}
		
		loadBackTestMarketData();
		
		for (BenchMark benchMark : backTestRequest.getBenchMarks()) {
			benchMarks.put(benchMark.value, new ArrayList<BenchMarkRecord>());
		}
		
		// load esperEngine
		esperEngine = new EsperEngine(esperConfig, id);
		esperEngine.init();
		
		// load strategy
		Strategy<?, ?> strategy = getStrategy();
		strategy.setEsperEngine(esperEngine);
		strategy.setStrategyTradingService(this);
		strategy.setSubscribeService(this);
		strategy.setStrategyQueryService(this);
		strategy.setMdCalService(mdCalService);
		strategy.setTradingService(backTestTradingService);
		strategy.init();
	}
	
	@Override
	public void uninit() {
		super.uninit();
	}
	
	private void loadBackTestMarketData() {
		marketDatas = backTestMarketDataLoader.loadMarketData(backTestRequest.getUniverse().keySet(), backTestRequest.getStartDate(),
				backTestRequest.getEndDate(), backTestRequest.getBackTestTimeFrame());
		validateMarketData(marketDatas, backTestRequest.getUniverse().keySet(), backTestRequest.getRequestID());
		log.info("loaded marketData, requestID: {}", backTestRequest.getRequestID());
	}
	
	private static void validateMarketData(List<MarketData> marketDatas, Set<String> symbols, String requestID) {
		Set<String> existSymbols = new HashSet<String>();
		for (MarketData marketData : marketDatas) {
			existSymbols.add(marketData.getSymbol());
		}
		
		symbols.removeAll(existSymbols);
		if (!symbols.isEmpty()) {
			log.warn("backtest request: {} missing symbols: {}", requestID, symbols);
		}
	}
	
	@Override
	public ExchangeOrder getExchangeOrder(String clOrdId) {
		return orders.get(clOrdId);
	}
	
	@Override
	public OrderMsg placeNewOrder(ExchangeOrder exchangeOrder, Long timeout) {
		String result = backTestTradingService.placeOrder(exchangeOrder);
		
		return (result == null) ? new OrderMsg(false, null, "Place limit order failed") : new OrderMsg(true, result, "Place limit order succeeded");
	}
	
	@Override
	public OrderMsg cancelOrder(String tradingAccount, String securityAccount, String clOrdId, Long timeout) {
		return (backTestTradingService.cancelOrder(clOrdId)) ? new OrderMsg(true, clOrdId, "Cancel order succeeded")
				: new OrderMsg(false, null, "Cancel order failed");
	}
	
	@Override
	public void sendStrategyReport(String report) {}
	
	public Strategy<?, ?> getStrategy() {
		return strategy;
	}
	
	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}
	
	public BackTestTradingService getBackTestTradingService() {
		return backTestTradingService;
	}
	
	public void setBackTestRequest(BackTestRequest backTestRequest) {
		this.backTestRequest = backTestRequest;
	}
	
	public void setStrategy(Strategy<?, ?> strategy) {
		this.strategy = strategy;
	}
	
	public void setBackTestMarketDataLoader(IBackTestMarketDataLoader backTestMarketDataLoader) {
		this.backTestMarketDataLoader = backTestMarketDataLoader;
	}
	
}
