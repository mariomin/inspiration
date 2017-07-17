package com.huatai.strategy.contrarian;

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

public class ContrarianAnalyzer extends SignalAnalyzer<ContrarianAnalysisResult> {
	private final Core core = new Core();
	private final Map<String, Integer> holdDays = new HashMap<String, Integer>();
	private final IMarketDataCalculateService mdCalService;
	private final ITradingService tradingService;
	
	public ContrarianAnalyzer(IMarketDataCalculateService mdCalService, ITradingService tradingService) {
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
	public ContrarianAnalysisResult analyze(MarketData marketData) {
		if (marketData instanceof CandleStick) {
			CandleStick stick = (CandleStick) marketData;
			
			String symbol = stick.getSymbol();
			updateHoldDays(symbol);
			
			int nTR = 12;
			int nMatrix = 9;
			int dataLength = (3 * nTR) + nMatrix + 150;
			List<String> buyList = new ArrayList<String>();
			List<Double> closePrices = mdCalService.getClosePx(symbol, TimeFrame.PERIOD_D1, 1, dataLength);
			
			if (closePrices != null) {
				double[] prices = ArrayUtil.reverse(closePrices);
				
				MInteger hhv30Length = new MInteger();
				MInteger llv15Length = new MInteger();
				MInteger ma5Length = new MInteger();
				MInteger ma30Length = new MInteger();
				MInteger bpLength = new MInteger();
				MInteger bpvLength = new MInteger();
				MInteger llbpv60Length = new MInteger();
				
				double[] hhv30 = new double[dataLength];
				double[] llv15 = new double[dataLength];
				double[] ma5 = new double[dataLength];
				double[] ma30 = new double[dataLength];
				double[] bp = new double[dataLength];
				double[] bpv = new double[dataLength];
				double[] llbpv60 = new double[dataLength];
				
				core.max(0, dataLength - 1, prices, 30, new MInteger(), hhv30Length, hhv30);
				core.min(0, dataLength - 1, prices, 15, new MInteger(), llv15Length, llv15);
				
				core.sma(0, dataLength - 1, prices, 5, new MInteger(), ma5Length, ma5);
				core.sma(0, dataLength - 1, prices, 30, new MInteger(), ma30Length, ma30);
				
				StrategyUtil.bp(0, ma5Length.value - 1, ma30Length.value - 1, ma5, ma30, new MInteger(), bpLength, bp);
				StrategyUtil.bpv(0, bpLength.value - 1, bp, 100, new MInteger(), bpvLength, bpv);
				
				core.min(0, bpvLength.value - 1, bpv, 60, new MInteger(), llbpv60Length, llbpv60);
				
				if (DecimalUtil.lessThan(llbpv60[llbpv60Length.value - 1], -1.5)
						&& DecimalUtil.greaterThan(bpv[bpvLength.value - 1], bpv[bpvLength.value - 2])
						&& DecimalUtil.greaterThan(bpv[bpvLength.value - 2], bpv[bpvLength.value - 3])
						&& DecimalUtil.greaterThan((hhv30[hhv30Length.value - 1] / closePrices.get(closePrices.size() - 1)), 1.15)) {
					buyList.add(symbol);
				}
			}
			
			double availableQty = tradingService.getTradableQty(symbol);
			if (DecimalUtil.greaterThan(availableQty, 0)) {
				if ((stopLoss(symbol, 0.2, TimeFrame.PERIOD_D1))) return new ContrarianAnalysisResult(symbol, 0);
				
				int days = holdDays.get(symbol); // 此处应为持仓天数
				if (days > 0) {
					stopWinSell(symbol, days, 0.15);
				}
			} else if (buyList.contains(symbol)) {
				double price = mdCalService.getOpenPx(symbol, TimeFrame.PERIOD_D1, 0, 0).get(0);
				double quantity = (int) (50000 / price / 100.0D) * 100;
				return new ContrarianAnalysisResult(symbol, quantity);
			}
		}
		
		return null;
	}
	
	private ContrarianAnalysisResult stopWinSell(String symbol, int days, double topDownPercentage) {
		List<Double> prices = mdCalService.getClosePx(symbol, TimeFrame.PERIOD_D1, 1, days);
		if (!tradingService.getPositionSymbols().contains(symbol) || prices.isEmpty()) return null;
		
		double maxPrice = 0.0;
		for (double price : prices) {
			maxPrice = Math.max(price, maxPrice);
		}
		
		if (DecimalUtil.greaterThan(maxPrice / prices.get(0), 1 + topDownPercentage)) return new ContrarianAnalysisResult(symbol, 0);
		
		return null;
	}
	
	private boolean stopLoss(String symbol, double stopLossPercentage, TimeFrame timeFrame) {
		double avgCost = tradingService.getTradableQty(symbol);
		List<Double> closePxes = mdCalService.getClosePx(symbol, timeFrame, 1, 1);
		double price = (closePxes != null) ? closePxes.get(0) : 0;
		return DecimalUtil.lessThan(price / avgCost, 1 - stopLossPercentage);
	}
	
	@Override
	public void uninit() {}
	
}
