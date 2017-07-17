package com.huatai.strategy.intraday.breakout;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.huatai.common.type.QtyPrice;
import com.huatai.strategy.intraday.common.OrderInfo;
import com.huatai.strategy.intraday.common.PortfolioRecord;

public class BreakoutDataCache {
	public BreakoutStrategyParam params = new BreakoutStrategyParam();
	
	// key:= symbol, String = clOrderId;
	public Map<String, OrderInfo> orderInfoSet = new ConcurrentHashMap<String, OrderInfo>();
	public Map<String, PortfolioRecord> portfolioSet = new ConcurrentHashMap<String, PortfolioRecord>();
	
	public Map<String, List<QtyPrice>> asks = new ConcurrentHashMap<String, List<QtyPrice>>();
	public Map<String, List<QtyPrice>> bids = new ConcurrentHashMap<String, List<QtyPrice>>();
	public Map<String, Double> volumeAskV10Aves = new ConcurrentHashMap<String, Double>();
	
	public Map<String, Double> holdingPositions = new ConcurrentHashMap<String, Double>();
	public Map<String, Double> openPrices = new ConcurrentHashMap<String, Double>();
	
}
