package com.huatai.strategy.intraday.reverse;

import java.util.ArrayList;
import java.util.List;

import com.huatai.strategy.intraday.common.MarketAtCloseSignal;

public class ReverseAnalysisResult {
	private final List<ReverseOpenSignal> openSignals = new ArrayList<ReverseOpenSignal>();
	private final ReverseCloseSignal closeSignal = new ReverseCloseSignal();
	private final MarketAtCloseSignal marketAtCloseSignal = new MarketAtCloseSignal();
	
	public void clearAll() {
		openSignals.clear();
		closeSignal.setMaxDrop(0d);
		closeSignal.setSymbol(null);
		marketAtCloseSignal.setMarketAtClose(false);
		marketAtCloseSignal.setDate(null);
	}
	
	public void add(ReverseOpenSignal openSignal) {
		openSignals.add(openSignal);
	}
	
	public List<ReverseOpenSignal> getOpenSignals() {
		return openSignals;
	}
	
	public ReverseCloseSignal getCloseSignal() {
		return closeSignal;
	}
	
	public MarketAtCloseSignal getMarketAtCloseSignal() {
		return marketAtCloseSignal;
	}
}
