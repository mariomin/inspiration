package com.huatai.strategy.intraday.breakout;

import java.util.ArrayList;
import java.util.List;

import com.huatai.strategy.intraday.common.MarketAtCloseSignal;

public class BreakoutAnalysisResult {
	private final List<BreakoutOpenSignal> openSignals = new ArrayList<BreakoutOpenSignal>();
	private final BreakoutCloseSignal closeSignal = new BreakoutCloseSignal();
	private final MarketAtCloseSignal marketAtCloseSignal = new MarketAtCloseSignal();
	
	public void clearAll() {
		openSignals.clear();
		closeSignal.setMaxDrop(0d);
		closeSignal.setSymbol(null);
		marketAtCloseSignal.setMarketAtClose(false);
		marketAtCloseSignal.setDate(null);
	}
	
	public void add(BreakoutOpenSignal openSignal) {
		openSignals.add(openSignal);
	}
	
	public List<BreakoutOpenSignal> getOpenSignals() {
		return openSignals;
	}
	
	public BreakoutCloseSignal getCloseSignal() {
		return closeSignal;
	}
	
	public MarketAtCloseSignal getMarketAtCloseSignal() {
		return marketAtCloseSignal;
	}
}
