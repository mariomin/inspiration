package com.huatai.strategy.intraday.breakout;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.huatai.common.algo.ISubscribeService;
import com.huatai.common.type.OrderSide;
import com.huatai.common.util.DecimalUtil;
import com.huatai.common.util.GsonUtil;
import com.huatai.common.util.IdGenerator;
import com.huatai.platform.algo.StrategyTradeExecutor;
import com.huatai.strategy.intraday.common.IntradayTimeEvent;
import com.huatai.strategy.intraday.common.OrderInfo;
import com.huatai.strategy.intraday.common.PortfolioRecord;

public class SimBreakoutTradeExecutor extends StrategyTradeExecutor<BreakoutAnalysisResult> {
	private final Logger log = LoggerFactory.getLogger(BreakoutTradeExecutor.class);
	private final Gson gson = GsonUtil.getGsonInstance();
	private final BreakoutDataCache dataCache;
	private final ISubscribeService subscribeService;
	private final IdGenerator idGenerator = IdGenerator.getInstance();
	
	public SimBreakoutTradeExecutor(BreakoutDataCache dataCache, ISubscribeService subscribeService) {
		this.dataCache = dataCache;
		this.subscribeService = subscribeService;
	}
	
	@Override
	public void init() {}
	
	@Override
	public void execute(BreakoutAnalysisResult result) throws Exception {
		processOpenSignals(result.getOpenSignals());
		processCloseSignal(result.getCloseSignal());
	}
	
	private void processOpenSignals(List<BreakoutOpenSignal> openSignals) throws Exception {
		for (BreakoutOpenSignal openSignal : openSignals) {
			String symbol = openSignal.getSymbol();
			PortfolioRecord portfolio = dataCache.portfolioSet.get(symbol);
			double holdingPosition = dataCache.holdingPositions.get(symbol);
			double price = dataCache.asks.get(symbol).get(dataCache.params.getBuyLevel() - 1).price + dataCache.params.getBuyDeviation();
			double quantity = Math.min(dataCache.volumeAskV10Aves.get(symbol) * dataCache.params.getCloseOpenParaP1(),
					Math.min(Math.floor(dataCache.params.getCloseOpenParaP2() / price), portfolio.getInitPosition()));
			int signalParaP14 = dataCache.params.getCloseOpenParaP4();
			
			openSignal.setOrderPrice(price);
			openSignal.setOrderQty(quantity);
			
			if (openSignal.isOpenOrderSignal() && !dataCache.orderInfoSet.containsKey(symbol) && DecimalUtil.isZero(holdingPosition)
					&& dataCache.params.isAutoTrade()) {
				OrderInfo orderInfo = new OrderInfo(idGenerator.getNextID(), quantity, System.currentTimeMillis(), OrderSide.Buy);
				orderInfo.setAllowedTicks(Math.floorDiv(dataCache.params.getCloseOpenParaP4(), 3));
				dataCache.holdingPositions.put(symbol, quantity);
				subscribeService.subscribeTimerEvent(signalParaP14, new IntradayTimeEvent(symbol, orderInfo.getClOrdId(), signalParaP14));
				log.info("at {} hitting one open signal: {}", new Date(openSignal.getTimeStamp()), openSignal);
			}
			sendStrategyReport(gson.toJson(openSignal));
		}
	}
	
	private void processCloseSignal(BreakoutCloseSignal closeSignal) throws Exception {
		String symbol = closeSignal.getSymbol();
		if (symbol == null) return;
		double holdingPosition = dataCache.holdingPositions.get(symbol);
		if (closeSignal.isCloseSignal() && !dataCache.orderInfoSet.containsKey(symbol) && DecimalUtil.greaterThan(holdingPosition, 0d)) {
			double price = dataCache.bids.get(symbol).get(dataCache.params.getSellLevel() - 1).price + dataCache.params.getSellDeviation();
			double quantity = holdingPosition;
			int signalParaP15 = dataCache.params.getCloseOpenParaP5();
			
			OrderInfo orderInfo = new OrderInfo(symbol, quantity, System.currentTimeMillis(), OrderSide.Sell);
			dataCache.holdingPositions.put(symbol, 0d);
			orderInfo.setAllowedTicks(0);
			subscribeService.subscribeTimerEvent(signalParaP15, new IntradayTimeEvent(symbol, orderInfo.getClOrdId(), signalParaP15));
			log.info("at {} hitting one close signal: symbol={}, orderPrice={}, orderQty={}", closeSignal.getDate(), symbol, price, quantity);
		}
	}
	
}
