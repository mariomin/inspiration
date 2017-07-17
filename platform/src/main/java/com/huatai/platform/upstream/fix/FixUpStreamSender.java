package com.huatai.platform.upstream.fix;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.business.Order;
import com.huatai.common.data.DataObject;
import com.huatai.common.fix.FixConvertionException;
import com.huatai.common.fix.FixConvertor;
import com.huatai.common.fix.message.RegisterredStrategyListResponse;
import com.huatai.common.fix.message.StrategyAlterResponse;
import com.huatai.common.fix.message.StrategyQueryResponse;
import com.huatai.common.fix.message.StrategyReport;
import com.huatai.common.fix.message.StrategySubscribeResponse;
import com.huatai.common.fix.tag.FixTags;
import com.huatai.common.fix.tag.NoStrategies;
import com.huatai.common.fix.tag.NoStrategyNames;
import com.huatai.common.fund.TradingFund;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.portfolio.PortfolioPosition;
import com.huatai.common.portfolio.TradingPosition;
import com.huatai.common.refdata.RefData;
import com.huatai.common.strategy.StrategyQueryRecord;
import com.huatai.common.type.ExchangeType;
import com.huatai.common.type.StrategyState;
import com.huatai.platform.upstream.UpStreamSender;

import quickfix.BooleanField;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.field.RefMsgType;
import quickfix.field.Text;
import quickfix.field.TradSesStatus;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.AdjustedPositionReport;
import quickfix.fix50sp2.BusinessMessageReject;
import quickfix.fix50sp2.ExecutionReport;
import quickfix.fix50sp2.NewOrderSingle;
import quickfix.fix50sp2.OrderCancelReject;
import quickfix.fix50sp2.OrderCancelRequest;
import quickfix.fix50sp2.SecurityList;
import quickfix.fix50sp2.TradingSessionStatus;

public class FixUpStreamSender extends UpStreamSender {
	private static final Logger log = LoggerFactory.getLogger(FixUpStreamSender.class);
	private final SessionID sessionID;
	
	public FixUpStreamSender(SessionID sessionID) {
		super(true);
		this.sessionID = sessionID;
	}
	
	@Override
	public void sendMessage(Message message) {
		if (message == null) return;
		
		try {
			Session.sendToTarget(message, sessionID);
		} catch (SessionNotFound e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void responseBusinessMessageReject(String refMsgType, String text) {
		BusinessMessageReject reject = FixConvertor.convertToBusinessMessageReject(refMsgType, text);
		sendMessage(reject);
	}
	
	@Override
	public void responseNewOrderReject(Message message, String text) {
		ExecutionReport executionReport = FixConvertor.convertToNewOrderReject(message, text);
		if (executionReport != null) {
			sendMessage(executionReport);
		}
	}
	
	@Override
	public void updateOrder(com.huatai.common.type.ExecType execType, String execID, RefMsgType refMsgType, Order order, TradingFund tradingFund,
			TradingPosition tradingPosition, DataObject dataObject, String text) {
		log.debug("updateOrder: {},  {}", execType, order);
		
		try {
			switch (execType) {
				case REJECTED: {
					if (refMsgType.valueEquals(NewOrderSingle.MSGTYPE)) {
						ExecutionReport executionReport = FixConvertor.convertToNewOrderReject(order.getClOrdId(), order, text);
						sendMessage(executionReport);
					} else if (refMsgType.valueEquals(OrderCancelRequest.MSGTYPE)) {
						OrderCancelReject orderCancelReject = FixConvertor.convertToOrderCancelReject(order.getClOrdId(), order.getOrigClOrdId(),
								order, text);
						sendMessage(orderCancelReject);
					}
				}
					break;
				default: {
					ExecutionReport executionReport = FixConvertor.orderToExecutionReport(execType, execID, order, tradingFund, tradingPosition,
							dataObject);
					sendMessage(executionReport);
				}
			}
		} catch (FixConvertionException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void responsePositionRequest(String requestId, TradingAccountIndex tradingAccountIndex, Collection<TradingPosition> positions,
			Collection<PortfolioPosition> portfolioPositions, DataObject dataObject) {
		AdjustedPositionReport positionReport = FixConvertor.convertToAdjustedPositionReport(requestId, tradingAccountIndex, positions,
				portfolioPositions, dataObject);
		sendMessage(positionReport);
	}
	
	@Override
	public void responseRefDataRequest(String requestId, Collection<RefData> refdatas, String text, int offset) {
		SecurityList securitylist = com.huatai.common.fix.FixConvertor.convertToSecurityList(requestId, refdatas, offset);
		sendMessage(securitylist);
	}
	
	@Override
	public void responseTradingSessionStatusReject(String tradSesReqID, ExchangeType exchangeType, String text) {
		TradingSessionStatus tradingSessionStatus = FixConvertor.convertToTradingSessionStatus(tradSesReqID, exchangeType,
				TradSesStatus.REQUEST_REJECTED, text);
		sendMessage(tradingSessionStatus);
	}
	
	@Override
	public void responseStrategyReport(String strategyId, String clOrderId, SecurityTradingIndex securityTradingIndex, String subscribeId,
			String strategyReport) {
		StrategyReport response = new StrategyReport();
		response.setString(FixTags.StrategyID, strategyId);
		if (clOrderId != null) {
			response.setField(new ClOrdID(clOrderId));
		}
		response.setString(FixTags.InvestorAccount, securityTradingIndex.getInvestorAccount());
		response.setString(FixTags.ProductAccount, securityTradingIndex.getProductAccount());
		response.setString(FixTags.AssetAccount, securityTradingIndex.getAssetAccount());
		response.setString(FixTags.AssetAccountType, securityTradingIndex.getAssetAccountType().name());
		response.setString(FixTags.TradingAccount, securityTradingIndex.getTradingAccount());
		response.setField(new StringField(FixTags.SubscribeID, subscribeId));
		response.setField(new StringField(FixTags.StrategyReport, strategyReport));
		sendMessage(response);
	}
	
	@Override
	public void responseStrategySubscribe(String requestID, boolean success) {
		StrategySubscribeResponse response = new StrategySubscribeResponse(new UserRequestID(requestID));
		response.set(new Text(success ? "true" : "false"));
		sendMessage(response);
	}
	
	@Override
	public void responseStrategyAlter(String requestID, boolean success, String strategyId, String clOrderId, StrategyState strategyState,
			String failCause) {
		StrategyAlterResponse response = new StrategyAlterResponse(new UserRequestID(requestID));
		response.setField(new BooleanField(FixTags.AlterReport, success));
		if (null != clOrderId) {
			response.setField(new ClOrdID(clOrderId));
		}
		if (null != strategyId) {
			response.setString(FixTags.StrategyID, strategyId);
		}
		if (null != failCause) {
			response.setField(new Text(failCause));
		}
		
		if (success) {
			String state = "";
			switch (strategyState) {
				case Running:
					state = "0";
					break;
				case Paused:
					state = "1";
					break;
				case Stopped:
					state = "2";
					break;
				case Initializing:
					state = "3";
					break;
				case Undefined:
					state = "4";
					break;
				default:
					state = "3";
					break;
			}
			response.setField(new StringField(FixTags.StrategyState, state));
		}
		sendMessage(response);
	}
	
	@Override
	public void responseStrategyQuery(String requestID, List<StrategyQueryRecord> records) {
		StrategyQueryResponse response = new StrategyQueryResponse();
		response.set(new UserRequestID(requestID));
		for (StrategyQueryRecord record : records) {
			NoStrategies strategy = new NoStrategies();
			strategy.setString(FixTags.StrategyID, record.getStrategyId());
			strategy.setString(FixTags.StrategyName, record.getStrategy());
			char strategyState;
			if (record.getStrategyState().equals(StrategyState.Running.value())) {
				strategyState = '0';
			} else if (record.getStrategyState().equals(StrategyState.Paused.value())) {
				strategyState = '1';
			} else if (record.getStrategyState().equals(StrategyState.Stopped.value())) {
				strategyState = '2';
			} else if (record.getStrategyState().equals(StrategyState.Initializing.value())) {
				strategyState = '3';
			} else {
				strategyState = '4';
			}
			strategy.setChar(FixTags.StrategyState, strategyState);
			response.addGroup(strategy);
		}
		sendMessage(response);
	}
	
	@Override
	public void responseStrategyNameQuery(String requestID, Set<String> strategyNames) {
		RegisterredStrategyListResponse response = new RegisterredStrategyListResponse();
		response.setField(new UserRequestID(requestID));
		for (String strategyName : strategyNames) {
			NoStrategyNames strategy = new NoStrategyNames();
			strategy.setString(FixTags.StrategyName, strategyName);
			response.addGroup(strategy);
		}
		sendMessage(response);
	}
	
}