package com.huatai.common.type;

public enum OrderField {
	// reserved tags for order service
	OrigExchangeOrdId, ExchangeOrdId, EntrustNo, BasketOrdId, RejectCode, Reason,
	
	// reserved tags for strategy
	Strategy, StrategyParams, StrategyState, StartTime, EndTime,
	
	// reserved tags for performance collection
	ClientCreated, PreValidation, PostValidation, EnterOMS, QuitOMS, EnterUpstream, QuitUpstream, QuitProcess,
	
	ExchangeServiceId,
	
	// reserved tags for O32
	OperatorNo, OperatorPassword, AccountCode, AssertNo, CombiNo, ServiceResult, SymbolSubordinations, TradingRestrictions,
	
	// reserved tags for uf
	ClientId, Password, SysnodeId, BatchNo,
	
	// reserved tags for intraday trading
	IntradayTradingCumBuyFreezedQty;
}
