package com.huatai.strategy.intraday.breakout;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.algo.AtsStrategy;
import com.huatai.common.business.ExchangeOrder;
import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.Quote;
import com.huatai.common.type.OrdStatus;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.StrategyState;
import com.huatai.common.util.DecimalUtil;
import com.huatai.platform.algo.Strategy;
import com.huatai.strategy.intraday.common.IntradayTimeEvent;
import com.huatai.strategy.intraday.common.OrderInfo;
import com.huatai.strategy.intraday.common.PortfolioRecord;

@AtsStrategy(chineseName = "日内突破策略", param = BreakoutStrategyParam.class)
public class BreakoutStrategy extends Strategy<BreakoutStrategyParam, BreakoutAnalysisResult> {
	private final static Logger log = LoggerFactory.getLogger(BreakoutStrategy.class);
	private final BreakoutDataCache dataCache = new BreakoutDataCache();
	
	@Override
	public void init() {
		dataCache.params = params;
		initPortfolio();
		super.init();
		log.info("Strategy Param={}", params);
	}
	
	private void initPortfolio() {
		for (PortfolioRecord record : params.getUniverse()) {
			dataCache.portfolioSet.put(record.getSymbol(), record);
			dataCache.holdingPositions.put(record.getSymbol(), 0d);
			dataCache.openPrices.put(record.getSymbol(), 0d);
		}
	}
	
	@Override
	public void subscribe() {
		Set<String> symbols = new HashSet<String>();
		for (PortfolioRecord record : params.getUniverse()) {
			symbols.add(record.getSymbol());
		}
		
		try {
			subscribeService.subscribeMarketDataEvent(Quote.class, symbols);
		} catch (Exception e) {
			log.error("failed to subscribe market data", e);
		}
	}
	
	@Override
	public void onStateUpdated() {}
	
	@Override
	public void createSignalAnalyzer() {
		signalAnalyzer = new BreakoutSignalAnalyzer(dataCache);
	}
	
	@Override
	public void createTradeExecutor() {
		tradeExecutor = new BreakoutTradeExecutor(dataCache, subscribeService);
	}
	
	@Override
	public void onMarketData(MarketData marketData) {
		if ((getState() != StrategyState.Initializing) && (getState() != StrategyState.Stopped)) {
			BreakoutAnalysisResult result = signalAnalyzer.analyze(marketData);
			if (getState() == StrategyState.Running) {
				checkOrderStatus(marketData.getTimestamp(), marketData.getSymbol());
				try {
					tradeExecutor.execute(result);
				} catch (Exception e) {
					log.error("failed to execute analysis result: {}", e);
				}
			}
		}
	}
	
	@Override
	public void onTimeEvent(AsyncEvent event) {
		if (event instanceof IntradayTimeEvent) {
			IntradayTimeEvent timeEvent = (IntradayTimeEvent) event;
			String symbol = timeEvent.getSymbol();
			OrderInfo orderInfo = dataCache.orderInfoSet.get(symbol);
			if (orderInfo != null) {
				ExchangeOrder order = strategyQueryService.getExchangeOrder(orderInfo.getClOrdId());
				if ((order != null) && (order.getOrdStatus() != OrdStatus.FILLED) && (order.getOrdStatus() != OrdStatus.CANCELED)
						&& (getState() == StrategyState.Running)) {
					
					double ask1 = dataCache.asks.get(symbol).get(0).price;
					if ((order.getOrderSide() == OrderSide.Buy) || DecimalUtil.greaterThan(order.getPrice(), ask1)) {
						PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
						String tradingAccount = order.getOrderSide() == OrderSide.Buy ? portfolio.getBuyTradingAccount()
								: portfolio.getSellTradingAccount();
						String securityAccount = order.getOrderSide() == OrderSide.Buy ? portfolio.getBuySecurityAccount()
								: portfolio.getSellSecurityAccount();
						strategyTradingService.cancelOrder(tradingAccount, securityAccount, order.getClOrdId(), 0l);
						log.info("order is not filled during {} seconds, then cancel this order: order={}", timeEvent.getTimeout(), order);
					} else {
						log.info("order is not filled during {} seconds, price is not large than aks1={}, then don't cancel this order",
								timeEvent.getTimeout(), ask1);
					}
					
				}
			}
		}
	}
	
	@Override
	public void onOrderUpdated(ExchangeOrder order) {
		OrderInfo orderInfo = dataCache.orderInfoSet.get(order.getSymbol());
		if (orderInfo != null) {
			if ((order.getOrdStatus() == OrdStatus.FILLED) || (order.getOrdStatus() == OrdStatus.CANCELED)
					|| (order.getOrdStatus() == OrdStatus.REJECTED)) {
				double holdingQty = dataCache.holdingPositions.get(order.getSymbol());
				if (order.getOrderSide() == OrderSide.Buy) {
					holdingQty += order.getCumQty();
				} else {
					holdingQty -= order.getCumQty();
				}
				dataCache.holdingPositions.put(order.getSymbol(), holdingQty);
				dataCache.orderInfoSet.remove(order.getSymbol());
				log.info(" order operation is done: Order={}", order);
			}
		} else {
			log.warn("received an order which is not existed in OrderInfoSet: symbol={}", order.getSymbol());
		}
	}
	
	@Override
	public void onNewOrderRejected(String clOrdId, String symbol, String text) {
		OrderInfo orderInfo = dataCache.orderInfoSet.get(symbol);
		if ((orderInfo != null) && (orderInfo.getClOrdId() == clOrdId)) {
			dataCache.orderInfoSet.remove(symbol);
			log.info("new order was rejected: clOrdId={}, symbol={}, reason={}", clOrdId, symbol, text);
		}
	}
	
	@Override
	public void onCancelOrderRejected(String clOrdId, String symbol, String text) {
		log.info("cancel order  was rejected: clOrdId={}, symbol={}, reason={}", clOrdId, symbol, text);
	}
	
	private void checkOrderStatus(Date date, String symbol) {
		OrderInfo orderInfo = dataCache.orderInfoSet.get(symbol);
		if (orderInfo != null) {
			ExchangeOrder order = strategyQueryService.getExchangeOrder(orderInfo.getClOrdId());
			int allowedTicks = orderInfo.getAllowedTicks() - 1;
			if ((allowedTicks < 0) && (order != null) && (order.getOrdStatus() != OrdStatus.FILLED) && (order.getOrdStatus() != OrdStatus.CANCELED)
					&& (order.getOrdStatus() != OrdStatus.REJECTED) && (getState() == StrategyState.Running)) {
				double ask1 = dataCache.asks.get(symbol).get(0).price;
				if ((order.getOrderSide() == OrderSide.Buy) || DecimalUtil.greaterThan(order.getPrice(), ask1)) {
					PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
					String tradingAccount = order.getOrderSide() == OrderSide.Buy ? portfolio.getBuyTradingAccount()
							: portfolio.getSellTradingAccount();
					String securityAccount = order.getOrderSide() == OrderSide.Buy ? portfolio.getBuySecurityAccount()
							: portfolio.getSellSecurityAccount();
					strategyTradingService.cancelOrder(tradingAccount, securityAccount, order.getClOrdId(), 0l);
					log.info("at {} order is not filled during limit ticks, then cancel this order: symbol={}, clOrdId={}", date, symbol,
							order.getClOrdId());
				} else {
					log.info("order is not filled during limit ticks, price is not large than aks1={}, then don't cancel this order", ask1);
				}
				
			}
			orderInfo.setAllowedTicks(allowedTicks);
		}
	}
	
	@Override
	public void uninit() {}
}
