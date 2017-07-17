package com.huatai.common.fund;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.index.TradingFundIndex;
import com.huatai.common.util.ConcurrentHashSet;

public class FundCache {
	private final Map<TradingFundIndex, TradingFund> tradingFunds = new ConcurrentHashMap<TradingFundIndex, TradingFund>();
	private final Map<TradingAccountIndex, Set<TradingFundIndex>> tradingAcc2TradingFund = new ConcurrentHashMap<TradingAccountIndex, Set<TradingFundIndex>>();
	
	public boolean contains(TradingFundIndex index) {
		return tradingAcc2TradingFund.containsKey(index);
	}
	
	public void updateTradingFund(TradingFund tradingFund) {
		TradingFundIndex tradingFundIndex = tradingFund.extractTradingFundIndex();
		tradingFunds.put(tradingFundIndex, tradingFund);
		TradingAccountIndex tradingAccountIndex = tradingFund.extractTradingAccountIndex();
		tradingAcc2TradingFund.putIfAbsent(tradingAccountIndex, new ConcurrentHashSet<TradingFundIndex>());
		tradingAcc2TradingFund.get(tradingAccountIndex).add(tradingFundIndex);
	}
	
	public TradingFund getTradingFund(TradingFundIndex index) {
		return tradingFunds.get(index);
	}
	
	public List<TradingFund> getTradingFunds(TradingFundIndex index) {
		List<TradingFund> result = new ArrayList<TradingFund>();
		for (TradingFundIndex tradingFundIndex : tradingAcc2TradingFund.get(index)) {
			result.add(tradingFunds.get(tradingFundIndex));
		}
		return result;
	}
}
