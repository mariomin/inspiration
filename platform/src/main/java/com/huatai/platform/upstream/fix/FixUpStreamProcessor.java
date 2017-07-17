package com.huatai.platform.upstream.fix;

import com.huatai.common.business.command.StrategyAlterCommand;
import com.huatai.common.fix.FixConvertionException;
import com.huatai.common.fix.FixConvertor;
import com.huatai.common.fix.message.FixMessage;
import com.huatai.common.fix.message.RegisterredStrategyListRequest;
import com.huatai.common.fix.message.StrategyAlterRequest;
import com.huatai.common.fix.message.StrategyQueryRequest;
import com.huatai.common.fix.message.StrategySubscribeRequest;
import com.huatai.common.fix.tag.FixTags;
import com.huatai.common.index.SecurityTradingIndex;
import com.huatai.common.index.TradingAccountIndex;
import com.huatai.common.strategy.StrategySubscriptionType;
import com.huatai.platform.upstream.UpStreamProcessor;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.StringField;
import quickfix.field.BusinessRejectReason;
import quickfix.field.BusinessRejectRefID;
import quickfix.field.MsgSeqNum;
import quickfix.field.MsgType;
import quickfix.field.PosReqID;
import quickfix.field.PosReqType;
import quickfix.field.RefMsgType;
import quickfix.field.RefSeqNum;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Text;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.BusinessMessageReject;
import quickfix.fix50sp2.RequestForPositions;

public class FixUpStreamProcessor extends UpStreamProcessor {
	
	@Override
	public boolean processFixMessage(FixMessage fixMessage) throws FieldNotFound, FixConvertionException {
		StringField msgType = fixMessage.getMsgType();
		
		if (msgType.valueEquals(RequestForPositions.MSGTYPE)) {
			processPositionRequest(fixMessage);
		} else if (msgType.valueEquals(StrategyAlterRequest.MSGTYPE)) {
			processStrategyAlterRequest(fixMessage);
		} else if (msgType.valueEquals(StrategySubscribeRequest.MSGTYPE)) {
			processStrategySubscribeRequest(fixMessage);
		} else if (msgType.valueEquals(StrategyQueryRequest.MSGTYPE)) {
			processStrategyQueryRequest(fixMessage);
		} else if (msgType.valueEquals(RegisterredStrategyListRequest.MSGTYPE)) {
			processStrategyNameRequest(fixMessage);
		} else return false;
		
		return true;
	}
	
	private boolean processStrategyNameRequest(FixMessage fixMessage) throws FieldNotFound {
		Message message = fixMessage.getMessage();
		String requestID = message.getField(new UserRequestID()).getValue();
		
		listener.onStrategyNameRequest(requestID);
		return true;
	}
	
	private boolean processStrategyQueryRequest(FixMessage fixMessage) throws FieldNotFound {
		Message message = fixMessage.getMessage();
		String requestID = message.getField(new UserRequestID()).getValue();
		SecurityTradingIndex index = FixConvertor.parseSecurityTradingIndex(message);
		
		listener.onStrategyQuery(requestID, index);
		return true;
	}
	
	private boolean processStrategySubscribeRequest(FixMessage fixMessage) throws FieldNotFound {
		Message message = fixMessage.getMessage();
		String requestID = message.getField(new UserRequestID()).getValue();
		String subscribeID = message.getString(FixTags.SubscribeID);
		String strategyID = message.isSetField(FixTags.StrategyID) ? message.getString(FixTags.StrategyID) : null;
		SecurityTradingIndex securityTradingIndex = FixConvertor.parseSecurityTradingIndex(message);
		char type = message.getField(new SubscriptionRequestType()).getValue();
		
		StrategySubscriptionType subType = null;
		if (type == '1') {
			subType = StrategySubscriptionType.Subscribe;
		} else if (type == '2') {
			subType = StrategySubscriptionType.Unsubscribe;
		} else {
			StringField refMsgType = message.getHeader().getField(new MsgType());
			BusinessMessageReject reject = new BusinessMessageReject(new RefMsgType(refMsgType.getValue()),
					new BusinessRejectReason(BusinessRejectReason.OTHER));
			reject.set(new RefSeqNum(message.getHeader().getInt(MsgSeqNum.FIELD)));
			reject.setField(new Text("ClOrdID must start with account"));
			reject.set(new BusinessRejectRefID(requestID));
			sender.sendMessage(reject);
			return false;
		}
		
		listener.onStrategySubscribe(requestID, securityTradingIndex, subscribeID, strategyID, subType);
		return true;
	}
	
	private boolean processStrategyAlterRequest(FixMessage fixMessage) throws FieldNotFound {
		Message message = fixMessage.getMessage();
		StrategyAlterCommand strategyAlter = FixConvertor.parseStrategyAlterCommand(message);
		listener.onStrategyAlterCommand(strategyAlter);
		return true;
	}
	
	private boolean processPositionRequest(FixMessage fixMessage) throws FieldNotFound {
		Message message = fixMessage.getMessage();
		String posReqID = message.getField(new PosReqID()).getValue();
		int posReqType = message.getField(new PosReqType()).getValue();
		TradingAccountIndex tradingAccountIndex = FixConvertor.parseTradingAccountIndex(message);
		
		listener.onPositionRequest(posReqID, posReqType, tradingAccountIndex);
		return true;
	}
	
}