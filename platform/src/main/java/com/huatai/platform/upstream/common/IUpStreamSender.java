package com.huatai.platform.upstream.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.huatai.common.business.Order;
import com.huatai.common.data.DataObject;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.portfolio.PortfolioPosition;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.refdata.RefData;
import com.huatai.common.strategy.StrategyQueryRecord;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.ExecType;
import com.huatai.common.type.StrategyState;

import quickfix.Message;
import quickfix.field.RefMsgType;

public interface IUpStreamSender {
	
	void onState(boolean on);
	
	boolean getState();
	
	void sendMessage(Message message);
	
	void responseNewOrderReject(Message message, String error);
	
	void responseBusinessMessageReject(String refMsgType, String text);
	
	void updateOrder(ExecType execType, String execID, RefMsgType refMsgType, Order order, TradingFund tradingFund, TradingPosition tradingPosition,
			DataObject dataObject, String info);
	
	void responsePositionRequest(String requestId, TradingAccountIndex tradingAccountIndex, Collection<TradingPosition> tradingPositions,
			Collection<PortfolioPosition> collection, DataObject dataObject);
	
	void responseRefDataRequest(String requestId, Collection<RefData> refdatas, String text, int offset);
	
	void responseTradingSessionStatusReject(String tradSesReqID, ExchangeType exchangeType, String text);
	
	void responseStrategyAlter(String requestID, boolean success, String strategyId, String clOrderId, StrategyState strategyState, String failCause);
	
	void responseStrategyReport(String strategyId, String clOrderId, SecurityTradingIndex securityTradingIndex, String subscribeId,
			String strategyReport);
	
	void responseStrategySubscribe(String requestID, boolean success);
	
	void responseStrategyQuery(String requestID, List<StrategyQueryRecord> records);
	
	void responseStrategyNameQuery(String requestID, Set<String> strategyNames);
	
}
