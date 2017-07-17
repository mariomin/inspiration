package com.huatai.common.event.account;

import java.util.Collection;

import com.huatai.common.data.DataObject;
import com.huatai.common.event.base.BaseResponseEvent;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.portfolio.PortfolioPosition;
import com.huatai.common.portfolio.TradingPosition;

public class ResponsePositionEvent extends BaseResponseEvent {
	private static final long serialVersionUID = 1L;
	private final Collection<TradingPosition> tradingPositions;
	private final Collection<PortfolioPosition> portfolioPositions;
	private final DataObject dataObject = new DataObject();
	private final TradingAccountIndex tradingAccountIndex;
	
	public ResponsePositionEvent(String key, String requestID, TradingAccountIndex tradingAccountIndex, Collection<TradingPosition> tradingPositions,
			Collection<PortfolioPosition> portfolioPositions) {
		super(key, requestID);
		this.tradingAccountIndex = tradingAccountIndex;
		this.tradingPositions = tradingPositions;
		this.portfolioPositions = portfolioPositions;
	}
	
	public Collection<TradingPosition> getTradingPositions() {
		return tradingPositions;
	}
	
	public Collection<PortfolioPosition> getPortfolioPositions() {
		return portfolioPositions;
	}
	
	public DataObject getDataObject() {
		return dataObject;
	}
	
	public TradingAccountIndex getTradingAccountIndex() {
		return tradingAccountIndex;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponsePositionEvent [tradingPositions=");
		builder.append(tradingPositions);
		builder.append(", portfolioPositions=");
		builder.append(portfolioPositions);
		builder.append(", dataObject=");
		builder.append(dataObject);
		builder.append(", tradingAccountIndex=");
		builder.append(tradingAccountIndex);
		builder.append("]");
		return builder.toString();
	}
	
}
