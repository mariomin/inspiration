package com.huatai.strategy.trixtrend;

import java.util.ArrayList;
import java.util.List;

import com.huatai.common.backtest.IMarketDataCalculateService;
import com.huatai.common.backtest.ITradingService;
import com.huatai.common.marketdata.CandleStick;
import com.huatai.common.marketdata.MarketData;
import com.huatai.common.marketdata.TimeFrame;
import com.huatai.common.util.ArrayUtil;
import com.huatai.common.util.DecimalUtil;
import com.huatai.platform.algo.SignalAnalyzer;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

public class TrixTrendAnalyzer extends SignalAnalyzer<TrixTrendAnalysisResult> {
	private final Core core = new Core();
	private final IMarketDataCalculateService mdCalService;
	private final ITradingService tradingService;
	
	public TrixTrendAnalyzer(IMarketDataCalculateService mdCalService, ITradingService tradingService) {
		this.mdCalService = mdCalService;
		this.tradingService = tradingService;
	}
	
	@Override
	public void init() {}
	
	@Override
	public TrixTrendAnalysisResult analyze(MarketData marketData) {
		if (marketData instanceof CandleStick) {
			CandleStick stick = (CandleStick) marketData;
			
			int nTR = 12;
			int nMatrix = 9;
			int dataLength = (3 * nTR) + nMatrix + 10;
			
			List<String> buyList = new ArrayList<String>();
			List<String> sellList = new ArrayList<String>();
			
			String symbol = stick.getSymbol();
			List<Double> closePrices = mdCalService.getClosePx(symbol, TimeFrame.PERIOD_D1, 1, dataLength);
			if (closePrices != null) {
				double[] prices = ArrayUtil.reverse(closePrices);
				
				MInteger trixLength = new MInteger();
				MInteger matrixLength = new MInteger();
				
				double[] trix = new double[dataLength];
				double[] matrix = new double[dataLength];
				
				core.trix(0, dataLength - 1, prices, nTR, new MInteger(), trixLength, trix);
				core.sma(0, trixLength.value - 1, trix, nMatrix, new MInteger(), matrixLength, matrix);
				
				if (DecimalUtil.greaterThan(trix[trixLength.value - 1], matrix[matrixLength.value - 1])
						&& DecimalUtil.lessThan(trix[trixLength.value - 5], matrix[matrixLength.value - 5])) {
					buyList.add(symbol);
				} else if (DecimalUtil.lessThan(trix[trixLength.value - 1], matrix[matrixLength.value - 2])
						&& DecimalUtil.greaterThan(trix[trixLength.value - 2], matrix[matrixLength.value - 3])) {
					sellList.add(symbol);
				}
			}
			
			double availableQty = tradingService.getTradableQty(symbol);
			if (DecimalUtil.greaterThan(availableQty, 0) && (stopLoss(symbol, 0.08, TimeFrame.PERIOD_D1) || sellList.contains(symbol)))
				return new TrixTrendAnalysisResult(symbol, 0);
			else if (DecimalUtil.isZero(availableQty) && buyList.contains(symbol)) {
				double price = mdCalService.getOpenPx(symbol, TimeFrame.PERIOD_D1, 0, 0).get(0);
				double quantity = (int) (50000 / price / 100.0D) * 100;
				return new TrixTrendAnalysisResult(symbol, quantity);
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
	
	@Override
	public void uninit() {}
	
}
