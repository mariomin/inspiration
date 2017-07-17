package com.huatai.strategy.strongtrend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.ITradingService;
import com.huatai.common.backtest.StrategyUtil;
import com.huatai.common.marketdata.CandleStick;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.TimeFrame;
import com.huatai.common.util.ArrayUtil;
import com.huatai.common.util.DecimalUtil;
import com.huatai.platform.algo.SignalAnalyzer;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

public class StrongTrendAnalyzer extends SignalAnalyzer<StrongTrendAnalysisResult> {
	private final Core core = new Core();
	private final Map<String, Integer> holdDays = new HashMap<String, Integer>();
	private final IMarketDataCalculateService mdCalService;
	private final ITradingService tradingService;
	
	public StrongTrendAnalyzer(IMarketDataCalculateService mdCalService, ITradingService tradingService) {
		this.mdCalService = mdCalService;
		this.tradingService = tradingService;
	}
	
	@Override
	public void init() {}
	
	private void updateHoldDays(String symbol) {
		if (DecimalUtil.greaterThan(tradingService.getHoldingQty(symbol), 0.0)) {
			Integer holiday = holdDays.get(symbol);
			if (holiday == null) {
				holiday = 0;
			}
			holdDays.put(symbol, holiday + 1);
		}
	}
	
	@Override
	public StrongTrendAnalysisResult analyze(MarketData marketData) {
		if (marketData instanceof CandleStick) {
			CandleStick stick = (CandleStick) marketData;
			
			String symbol = stick.getSymbol();
			updateHoldDays(symbol);
			
			int nTR = 12;
			int nMatrix = 9;
			int dataLength = (3 * nTR) + nMatrix + 120;
			List<String> buyList = new ArrayList<String>();
			List<String> sellList = new ArrayList<String>();
			List<Double> closePrices = mdCalService.getClosePx(symbol, TimeFrame.PERIOD_D1, 1, dataLength);
			
			if (closePrices != null) {
				double[] prices = ArrayUtil.reverse(closePrices);
				
				MInteger trixLength = new MInteger();
				MInteger matrixLength = new MInteger();
				MInteger ma5Length = new MInteger();
				MInteger ma10Length = new MInteger();
				MInteger ma30Length = new MInteger();
				MInteger bpLength = new MInteger();
				MInteger bpvLength = new MInteger();
				MInteger mbpvLength = new MInteger();
				
				double[] trix = new double[dataLength];
				double[] matrix = new double[dataLength];
				double[] ma5 = new double[dataLength];
				double[] ma10 = new double[dataLength];
				double[] ma30 = new double[dataLength];
				double[] bp = new double[dataLength];
				double[] bpv = new double[dataLength];
				double[] mbpv = new double[dataLength];
				
				core.trix(0, dataLength - 1, prices, nTR, new MInteger(), trixLength, trix);
				core.sma(0, trixLength.value - 1, trix, nMatrix, new MInteger(), matrixLength, matrix);
				core.sma(0, dataLength - 1, prices, 5, new MInteger(), ma5Length, ma5);
				core.sma(0, dataLength - 1, prices, 10, new MInteger(), ma10Length, ma10);
				core.sma(0, dataLength - 1, prices, 30, new MInteger(), ma30Length, ma30);
				
				StrategyUtil.bp(0, ma5Length.value - 1, ma30Length.value - 1, ma5, ma30, new MInteger(), bpLength, bp);
				StrategyUtil.bpv(0, bpLength.value - 1, bp, 100, new MInteger(), bpvLength, bpv);
				
				core.sma(0, bpvLength.value - 1, bpv, 2, new MInteger(), mbpvLength, mbpv);
				
				if (((bpvLength.value >= 3) && (mbpvLength.value >= 3) && DecimalUtil.greaterThan(bpv[bpvLength.value - 1], bpv[bpvLength.value - 2])
						&& DecimalUtil.greaterThan(bpv[bpvLength.value - 2], bpv[bpvLength.value - 3])
						&& DecimalUtil.greaterThan(prices[dataLength - 1], ma5[ma5Length.value - 1])
						&& DecimalUtil.greaterThan(ma5[ma5Length.value - 1], ma10[ma10Length.value - 1])
						&& DecimalUtil.greaterThan(trix[trixLength.value - 1], matrix[matrixLength.value - 1])
						&& DecimalUtil.greaterThan(prices[dataLength - 1], prices[dataLength - 2])
						&& DecimalUtil.greaterThan(prices[dataLength - 2], prices[dataLength - 3])
						&& DecimalUtil.greaterThan(mbpv[mbpvLength.value - 1], mbpv[mbpvLength.value - 2]))) {
					buyList.add(symbol);
				}
			}
			
			double availableQty = tradingService.getTradableQty(symbol);
			if (DecimalUtil.greaterThan(availableQty, 0)) {
				int holdDay = holdDays.get(symbol);
				List<Double> prices = mdCalService.getClosePx(symbol, TimeFrame.PERIOD_D1, 1, holdDay);
				
				double maxPrice = 0.0;
				if (prices == null) {
					mdCalService.getClosePx(symbol, TimeFrame.PERIOD_D1, 1, holdDay);
				}
				
				if (prices != null) {
					for (double price : prices) {
						maxPrice = Math.max(price, maxPrice);
					}
				}
				
				if (stopLoss(symbol, 0.1, TimeFrame.PERIOD_D1) || sellList.contains(symbol))
					return new StrongTrendAnalysisResult(symbol, 0);
				else if (DecimalUtil.equalLessThan(maxPrice / tradingService.getTradableQty(symbol), 1.3))
					return stopWinSell(symbol, maxPrice, prices.get(0), 0.08);
				else if (DecimalUtil.greaterThan(maxPrice / tradingService.getTradableQty(symbol), 1.3))
					return stopWinSell(symbol, maxPrice, prices.get(0), 0.15);
				
			} else if (buyList.contains(symbol)) {
				double price = mdCalService.getOpenPx(symbol, TimeFrame.PERIOD_D1, 0, 0).get(0);
				double quantity = (int) (50000 / price / 100.0D) * 100;
				return new StrongTrendAnalysisResult(symbol, quantity);
			}
		}
		
		return null;
	}
	
	private boolean stopLoss(String symbol, double stopLossPercentage, TimeFrame timeFrame) {
		double avgCost = tradingService.getTradableQty(symbol);
		List<Double> closePxes = mdCalService.getClosePx(symbol, timeFrame, 1, 1);
		double price = (closePxes != null) ? closePxes.get(0) : 0;
		return DecimalUtil.lessThan(price / avgCost, 1 - stopLossPercentage);
	}
	
	private StrongTrendAnalysisResult stopWinSell(String symbol, double maxPrice, double price, double topDownLine) {
		if (DecimalUtil.greaterThan(maxPrice / price, 1 + topDownLine)) return new StrongTrendAnalysisResult(symbol, 0d);
		
		return null;
	}
	
	@Override
	public void uninit() {}
	
}
