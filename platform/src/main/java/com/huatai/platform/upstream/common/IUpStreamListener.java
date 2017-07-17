package com.huatai.platform.upstream.common;

import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.strategy.StrategySubscriptionType;

public interface IUpStreamListener {
	
	void onState(boolean on);
	
	void onPositionRequest(String posReqID, int posReqType, TradingAccountIndex tradingAccountIndex);
	
	void onStrategyAlterCommand(StrategyAlterCommand strategyAlter);
	
	void onStrategySubscribe(String requestID, SecurityTradingIndex securityTradingIndex, String subscribeID, String strategyID,
			StrategySubscriptionType subType);
	
	void onStrategyQuery(String requestID, SecurityTradingIndex securityTradingIndex);
	
	void onStrategyNameRequest(String requestID);
	
}
