package com.huatai.common.portfolio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.index.TradingPositionIndex;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.util.ConcurrentHashSet;

public class PortfolioCache {
	// positionId:Position
	private final Map<TradingPositionIndex, TradingPosition> tradingPositions = new ConcurrentHashMap<TradingPositionIndex, TradingPosition>();
	private final Map<TradingAccountIndex, Set<TradingPositionIndex>> tradingAccount2Positions = new ConcurrentHashMap<TradingAccountIndex, Set<TradingPositionIndex>>();
	
	public boolean contains(TradingAccountIndex key) {
		return tradingAccount2Positions.containsKey(key);
	}
	
	public void updateAlgoPosition(TradingPosition tradingPosition) {
		if (tradingPosition == null) return;
		
		TradingPositionIndex tradingPositionIndex = tradingPosition.extractTradingPositionIndex();
		tradingPositions.put(tradingPositionIndex, tradingPosition);
		TradingAccountIndex tradingAccountIndex = tradingPosition.extractTradingAccountIndex();
		tradingAccount2Positions.putIfAbsent(tradingAccountIndex, new ConcurrentHashSet<TradingPositionIndex>());
		tradingAccount2Positions.get(tradingAccountIndex).add(tradingPositionIndex);
	}
	
	public void updateAlgoPositions(Collection<TradingPosition> algoPositions) {
		if (algoPositions == null) return;
		
		for (TradingPosition position : algoPositions) {
			updateAlgoPosition(position);
		}
	}
	
	public TradingPosition getTradingPosition(TradingPositionIndex key) {
		return tradingPositions.get(key);
	}
	
	public List<TradingPosition> getTradingPositions(TradingAccountIndex key, String securityAccount, ExchangeType exchangeType) {
		List<TradingPosition> result = new ArrayList<TradingPosition>();
		Set<TradingPositionIndex> tradingPosKeys = tradingAccount2Positions.get(key);
		if (tradingPosKeys != null) {
			for (TradingPositionIndex tradingPosKey : tradingAccount2Positions.get(key)) {
				if (tradingPosKey.getSecurityAccount().equals(securityAccount)) {
					result.add(tradingPositions.get(tradingPosKey));
				}
			}
		}
		return result;
	}
}
