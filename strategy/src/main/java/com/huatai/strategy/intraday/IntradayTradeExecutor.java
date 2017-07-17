package com.huatai.strategy.intraday;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.huatai.common.algo.ISubscribeService;
import com.huatai.common.strategy.OrderMsg;
import com.huatai.common.type.OrderSide;
import com.huatai.common.type.QtyPrice;
import com.huatai.common.type.SecurityType;
import com.huatai.common.util.DecimalUtil;
import com.huatai.common.util.GsonUtil;
import com.huatai.platform.algo.StrategyTradeExecutor;
import com.huatai.strategy.intraday.breakout.BreakoutCloseSignal;
import com.huatai.strategy.intraday.breakout.BreakoutDataCache;
import com.huatai.strategy.intraday.breakout.BreakoutOpenSignal;
import com.huatai.strategy.intraday.common.IntradayTimeEvent;
import com.huatai.strategy.intraday.common.MarketAtCloseSignal;
import com.huatai.strategy.intraday.common.OrderInfo;
import com.huatai.strategy.intraday.common.PortfolioRecord;
import com.huatai.strategy.intraday.reverse.ReverseCloseSignal;
import com.huatai.strategy.intraday.reverse.ReverseDataCache;
import com.huatai.strategy.intraday.reverse.ReverseOpenSignal;

public class IntradayTradeExecutor extends StrategyTradeExecutor<IntradayAnalysisResult> {
	private final Logger log = LoggerFactory.getLogger(IntradayTradeExecutor.class);
	private final Gson gson = GsonUtil.getGsonInstance();
	private final IntradayDataCache intradayDataCache;
	private final ISubscribeService subscribeService;
	
	public IntradayTradeExecutor(IntradayDataCache intradayDataCache, ISubscribeService subscribeService) {
		this.intradayDataCache = intradayDataCache;
		this.subscribeService = subscribeService;
	}
	
	@Override
	public void init() {}
	
	@Override
	public void execute(IntradayAnalysisResult result) throws Exception {
		processBreakoutOpenSignals(result.getBreakoutResult().getOpenSignals(), intradayDataCache.breakoutDataCache);
		processReverseOpenSignals(result.getReverseResult().getOpenSignals(), intradayDataCache.reverseDataCache);
		processBreakoutCloseSignal(result.getBreakoutResult().getCloseSignal(), intradayDataCache.breakoutDataCache);
		processReverseCloseSignal(result.getReverseResult().getCloseSignal(), intradayDataCache.reverseDataCache);
		processBreakoutMarketAtCloseSignal(result.getBreakoutResult().getMarketAtCloseSignal(), intradayDataCache.breakoutDataCache);
		processReverseMarketAtCloseSignal(result.getReverseResult().getMarketAtCloseSignal(), intradayDataCache.reverseDataCache);
		
	}
	
	private void processBreakoutOpenSignals(List<BreakoutOpenSignal> openSignals, BreakoutDataCache dataCache) throws Exception {
		for (BreakoutOpenSignal openSignal : openSignals) {
			String symbol = openSignal.getSymbol();
			PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
			double holdingPosition = intradayDataCache.holdingPositions.get(symbol);
			int buyLevel = dataCache.params.getBuyLevel();
			List<QtyPrice> priceList = (buyLevel > 0) ? dataCache.asks.get(symbol) : dataCache.bids.get(symbol);
			double price = priceList.get(Math.abs(buyLevel) - 1).price + dataCache.params.getBuyDeviation();
			price = (new BigDecimal(price)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			
			double quantity = Math.min(dataCache.volumeAskV10Aves.get(symbol) * dataCache.params.getCloseOpenParaP1(),
					Math.min(dataCache.params.getCloseOpenParaP2() / price, portfolio.getInitPosition() * dataCache.params.getCloseOpenParaP3()));
			quantity = (new BigDecimal(quantity / 100).setScale(0, RoundingMode.HALF_DOWN)).doubleValue() * 100;
			int signalParaP14 = dataCache.params.getCloseOpenParaP4();
			
			openSignal.setOrderPrice(price);
			openSignal.setOrderQty(quantity);
			openSignal.setPortfolioNo(portfolio.getPortfolioNo());
			openSignal.setAutoTrade(dataCache.params.isAutoTrade());
			
			if (openSignal.isOpenOrderSignal() && !intradayDataCache.orderInfoSet.containsKey(symbol) && DecimalUtil.isZero(holdingPosition)
					&& dataCache.params.isAutoTrade()) {
				OrderMsg orderMsg = placeLimitOrder(portfolio.getBuyTradingAccount(), portfolio.getBuySecurityAccount(), portfolio.getPortfolioNo(),
						portfolio.getProtfolioType(), SecurityType.CS, symbol, OrderSide.Buy, price, quantity, 0l);
				if (orderMsg.isSuccessed()) {
					OrderInfo orderInfo = new OrderInfo(orderMsg.getClOrdId(), quantity, System.currentTimeMillis(), OrderSide.Buy);
					orderInfo.setAllowedTicks(Math.floorDiv(dataCache.params.getCloseOpenParaP4(), 3));
					dataCache.orderInfoSet.put(symbol, orderInfo);
					dataCache.openPrices.put(symbol, price);
					intradayDataCache.orderInfoSet.put(symbol, orderInfo);
					subscribeService.subscribeTimerEvent(1000 * signalParaP14, new IntradayTimeEvent(symbol, orderInfo.getClOrdId(), signalParaP14));
					log.info("at {} hitting one open signal, and place a buy order: symbol={}, clOrdId={}, quantity={}, price={}",
							new Date(openSignal.getTimeStamp()), symbol, orderInfo.getClOrdId(), quantity, price);
				} else {
					log.error("place new order failed: message={}", orderMsg.getMessage());
					throw new Exception("place new order failed");
				}
			}
			sendStrategyReport(gson.toJson(openSignal));
		}
	}
	
	private void processBreakoutCloseSignal(BreakoutCloseSignal closeSignal, BreakoutDataCache dataCache) throws Exception {
		if (closeSignal.isCloseSignal()) {
			String symbol = closeSignal.getSymbol();
			PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
			double holdingPosition = dataCache.holdingPositions.get(symbol);
			OrderInfo currentOrderInfo = dataCache.orderInfoSet.get(symbol);
			
			if ((currentOrderInfo == null) && DecimalUtil.greaterThan(holdingPosition, 0d)) {
				int sellLevel = dataCache.params.getSellLevel();
				List<QtyPrice> priceList = (sellLevel > 0) ? dataCache.asks.get(symbol) : dataCache.bids.get(symbol);
				double price = priceList.get(Math.abs(sellLevel) - 1).price + dataCache.params.getSellDeviation();
				price = (new BigDecimal(price)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				double quantity = holdingPosition;
				int signalParaP15 = dataCache.params.getCloseOpenParaP5();
				
				OrderMsg orderMsg = placeLimitOrder(portfolio.getBuyTradingAccount(), portfolio.getBuySecurityAccount(), portfolio.getPortfolioNo(),
						portfolio.getProtfolioType(), SecurityType.CS, symbol, OrderSide.Sell, price, quantity, 0l);
				if (orderMsg.isSuccessed()) {
					OrderInfo orderInfo = new OrderInfo(orderMsg.getClOrdId(), quantity, System.currentTimeMillis(), OrderSide.Sell);
					orderInfo.setAllowedTicks(0);
					dataCache.orderInfoSet.put(symbol, orderInfo);
					intradayDataCache.orderInfoSet.put(symbol, orderInfo);
					subscribeService.subscribeTimerEvent(1000 * signalParaP15, new IntradayTimeEvent(symbol, orderInfo.getClOrdId(), signalParaP15));
					log.info("at {} hitting one close signal, and place a sell order: symbol={}, clOrdId={}, quantity={}, price={}",
							closeSignal.getDate(), symbol, orderInfo.getClOrdId(), quantity, price);
				} else {
					log.error("place new order failed: message={}", orderMsg.getMessage());
					throw new Exception("place new order failed");
				}
			}
			
			if ((currentOrderInfo != null) && (currentOrderInfo.getSide() == OrderSide.Buy)) {
				cancelOrder(currentOrderInfo.getClOrdId(), 0l);
				log.info("at {} hitting one close signal, and place a cancel order: symbol={}, clOrdId={}", closeSignal.getDate(), symbol,
						currentOrderInfo.getClOrdId());
			}
		}
	}
	
	private void processReverseOpenSignals(List<ReverseOpenSignal> openSignals, ReverseDataCache dataCache) throws Exception {
		for (ReverseOpenSignal openSignal : openSignals) {
			String symbol = openSignal.getSymbol();
			PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
			double holdingPosition = intradayDataCache.holdingPositions.get(symbol);
			int buyLevel = dataCache.params.getBuyLevel();
			List<QtyPrice> priceList = (buyLevel > 0) ? dataCache.asks.get(symbol) : dataCache.bids.get(symbol);
			double price = priceList.get(Math.abs(buyLevel) - 1).price + dataCache.params.getBuyDeviation();
			price = (new BigDecimal(price)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			
			double quantity = Math.min(dataCache.volumeAskV10Aves.get(symbol) * dataCache.params.getCloseOpenParaP1(),
					Math.min(dataCache.params.getCloseOpenParaP2() / price, portfolio.getInitPosition() * dataCache.params.getCloseOpenParaP3()));
			quantity = (new BigDecimal(quantity / 100).setScale(0, RoundingMode.HALF_DOWN)).doubleValue() * 100;
			int signalParaP14 = dataCache.params.getCloseOpenParaP4();
			
			openSignal.setOrderPrice(price);
			openSignal.setOrderQty(quantity);
			openSignal.setPortfolioNo(portfolio.getPortfolioNo());
			openSignal.setAutoTrade(dataCache.params.isAutoTrade());
			
			if (openSignal.isOpenOrderSignal() && !intradayDataCache.orderInfoSet.containsKey(symbol) && DecimalUtil.isZero(holdingPosition)
					&& dataCache.params.isAutoTrade()) {
				OrderMsg orderMsg = placeLimitOrder(portfolio.getBuyTradingAccount(), portfolio.getBuySecurityAccount(), portfolio.getPortfolioNo(),
						portfolio.getProtfolioType(), SecurityType.CS, symbol, OrderSide.Buy, price, quantity, 0l);
				if (orderMsg.isSuccessed()) {
					OrderInfo orderInfo = new OrderInfo(orderMsg.getClOrdId(), quantity, System.currentTimeMillis(), OrderSide.Buy);
					orderInfo.setAllowedTicks(Math.floorDiv(dataCache.params.getCloseOpenParaP4(), 3));
					dataCache.orderInfoSet.put(symbol, orderInfo);
					dataCache.openPrices.put(symbol, price);
					intradayDataCache.orderInfoSet.put(symbol, orderInfo);
					subscribeService.subscribeTimerEvent(1000 * signalParaP14, new IntradayTimeEvent(symbol, orderInfo.getClOrdId(), signalParaP14));
					log.info("at {} hitting one open signal, and place a buy order: symbol={}, clOrdId={}, quantity={}, price={}",
							new Date(openSignal.getTimeStamp()), symbol, orderInfo.getClOrdId(), quantity, price);
				} else {
					log.error("place new order failed: message={}", orderMsg.getMessage());
					throw new Exception("place new order failed");
				}
			}
			// TODO
			// sendStrategyReport(gson.toJson(openSignal));
		}
	}
	
	private void processReverseCloseSignal(ReverseCloseSignal closeSignal, ReverseDataCache dataCache) throws Exception {
		String symbol = closeSignal.getSymbol();
		if (symbol == null) return;
		
		PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
		double holdingPosition = dataCache.holdingPositions.get(symbol);
		OrderInfo currentOrderInfo = dataCache.orderInfoSet.get(symbol);
		double openPrice = dataCache.openPrices.get(symbol);
		double ask1 = dataCache.asks.get(symbol).get(0).price;
		if (closeSignal.isCloseSignal(openPrice, ask1)) {
			if ((currentOrderInfo == null) && DecimalUtil.greaterThan(holdingPosition, 0d)) {
				int sellLevel = dataCache.params.getSellLevel();
				List<QtyPrice> priceList = (sellLevel > 0) ? dataCache.asks.get(symbol) : dataCache.bids.get(symbol);
				double price = priceList.get(Math.abs(sellLevel) - 1).price + dataCache.params.getSellDeviation();
				price = (new BigDecimal(price)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				double quantity = holdingPosition;
				int signalParaP15 = dataCache.params.getCloseOpenParaP5();
				
				OrderMsg orderMsg = placeLimitOrder(portfolio.getBuyTradingAccount(), portfolio.getBuySecurityAccount(), portfolio.getPortfolioNo(),
						portfolio.getProtfolioType(), SecurityType.CS, symbol, OrderSide.Sell, price, quantity, 0l);
				if (orderMsg.isSuccessed()) {
					OrderInfo orderInfo = new OrderInfo(orderMsg.getClOrdId(), quantity, System.currentTimeMillis(), OrderSide.Sell);
					orderInfo.setAllowedTicks(0);
					dataCache.orderInfoSet.put(symbol, orderInfo);
					intradayDataCache.orderInfoSet.put(symbol, orderInfo);
					subscribeService.subscribeTimerEvent(1000 * signalParaP15, new IntradayTimeEvent(symbol, orderInfo.getClOrdId(), signalParaP15));
					log.info("at {} hitting one close signal, and place a sell order: symbol={}, clOrdId={}, quantity={}, price={}",
							closeSignal.getDate(), symbol, orderInfo.getClOrdId(), quantity, price);
				} else {
					log.error("place new order failed: message={}", orderMsg.getMessage());
					throw new Exception("place new order failed");
				}
			}
			
			if ((currentOrderInfo != null) && (currentOrderInfo.getSide() == OrderSide.Buy)) {
				cancelOrder(currentOrderInfo.getClOrdId(), 0l);
				log.info("at {} hitting one close signal, and place a cancel order: symbol={}, clOrdId={}", closeSignal.getDate(), symbol,
						currentOrderInfo.getClOrdId());
			}
		}
	}
	
	private void processBreakoutMarketAtCloseSignal(MarketAtCloseSignal signal, BreakoutDataCache dataCache) {
		if (signal.isMarketAtClose()) {
			for (String symbol : dataCache.holdingPositions.keySet()) {
				double holdingPosition = dataCache.holdingPositions.get(symbol);
				OrderInfo orderInfo = dataCache.orderInfoSet.get(symbol);
				PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
				if (!DecimalUtil.isZero(holdingPosition) && (orderInfo == null)) {
					OrderMsg orderMsg = this.placeMarketOrder(portfolio.getSellTradingAccount(), portfolio.getSellSecurityAccount(),
							portfolio.getPortfolioNo(), portfolio.getProtfolioType(), SecurityType.CS, symbol, OrderSide.Sell, holdingPosition, '3',
							5, 0d, 0l);
					if (orderMsg.isSuccessed()) {
						OrderInfo newOrderInfo = new OrderInfo(orderMsg.getClOrdId(), holdingPosition, System.currentTimeMillis(), OrderSide.Sell);
						dataCache.orderInfoSet.put(symbol, newOrderInfo);
						intradayDataCache.orderInfoSet.put(symbol, newOrderInfo);
						log.info("at {} received MarketAtClose signal, then close the opened position by sending MarketOrder: symbol={}, clOrdId={}",
								signal.getDate(), symbol, orderMsg.getClOrdId());
					}
				} else if (orderInfo != null) {
					cancelOrder(orderInfo.getClOrdId(), 0l);
					log.info("at {} received MarketAtAcloseSignal, cancel the order not filled:symbol={}, clOrdId={}", signal.getDate(), symbol,
							orderInfo.getClOrdId());
				}
			}
		}
	}
	
	private void processReverseMarketAtCloseSignal(MarketAtCloseSignal signal, ReverseDataCache dataCache) {
		if (signal.isMarketAtClose()) {
			for (String symbol : dataCache.holdingPositions.keySet()) {
				double holdingPosition = dataCache.holdingPositions.get(symbol);
				OrderInfo orderInfo = dataCache.orderInfoSet.get(symbol);
				PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
				if (!DecimalUtil.isZero(holdingPosition) && (orderInfo == null)) {
					OrderMsg orderMsg = this.placeMarketOrder(portfolio.getSellTradingAccount(), portfolio.getSellSecurityAccount(),
							portfolio.getPortfolioNo(), portfolio.getProtfolioType(), SecurityType.CS, symbol, OrderSide.Sell, holdingPosition, '3',
							5, 0d, 0l);
					if (orderMsg.isSuccessed()) {
						OrderInfo newOrderInfo = new OrderInfo(orderMsg.getClOrdId(), holdingPosition, System.currentTimeMillis(), OrderSide.Sell);
						dataCache.orderInfoSet.put(symbol, newOrderInfo);
						intradayDataCache.orderInfoSet.put(symbol, newOrderInfo);
						log.info("at {} received MarketAtClose signal, then close the opened position by sending MarketOrder: symbol={}, clOrdId={}",
								signal.getDate(), symbol, orderMsg.getClOrdId());
					}
				} else if (orderInfo != null) {
					cancelOrder(orderInfo.getClOrdId(), 0l);
					log.info("at {} received MarketAtAcloseSignal, cancel the order not filled:symbol={}, clOrdId={}", signal.getDate(), symbol,
							orderInfo.getClOrdId());
				}
			}
		}
	}
	
}
