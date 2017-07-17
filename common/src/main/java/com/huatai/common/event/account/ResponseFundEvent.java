package com.huatai.common.event.account;

import java.util.ArrayList;
import java.util.Collection;

import com.huatai.common.event.base.BaseResponseEvent;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.index.TradingAccountIndex;

public class ResponseFundEvent extends BaseResponseEvent {
	private static final long serialVersionUID = 1L;
	private Collection<TradingFund> tradingFunds;
	private final TradingAccountIndex tradingAccountIndex;
	
	public ResponseFundEvent(String key, String requestID, TradingAccountIndex tradingAccountIndex, Collection<TradingFund> tradingFunds) {
		super(key, requestID);
		if (tradingFunds != null) {
			this.tradingFunds = new ArrayList<TradingFund>(tradingFunds);
		}
		this.tradingAccountIndex = tradingAccountIndex;
	}
	
	public Collection<TradingFund> getTradingFunds() {
		return tradingFunds;
	}
	
	public TradingAccountIndex getTradingAccountIndex() {
		return tradingAccountIndex;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseFundEvent [tradingFunds=");
		builder.append(tradingFunds);
		builder.append(", tradingAccountIndex=");
		builder.append(tradingAccountIndex);
		builder.append("]");
		return builder.toString();
	}
	
}
