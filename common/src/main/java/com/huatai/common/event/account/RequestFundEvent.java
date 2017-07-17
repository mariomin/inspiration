package com.huatai.common.event.account;

import com.huatai.common.event.base.BaseRequestEvent;
import com.huatai.common.index.TradingAccountIndex;

public class RequestFundEvent extends BaseRequestEvent {
	private static final long serialVersionUID = 1L;
	private final TradingAccountIndex tradingAccountIndex;
	
	public RequestFundEvent(String key, String requestID, TradingAccountIndex tradingAccountIndex) {
		super(key, requestID);
		this.tradingAccountIndex = tradingAccountIndex;
	}
	
	public TradingAccountIndex getTradingAccountIndex() {
		return tradingAccountIndex;
	}
	
}
