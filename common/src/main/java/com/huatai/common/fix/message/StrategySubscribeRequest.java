package com.huatai.common.fix.message;

import quickfix.field.MsgType;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.Message;

public class StrategySubscribeRequest extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH103";
	
	public StrategySubscribeRequest() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
	public StrategySubscribeRequest(UserRequestID userRequestID) {
		this();
		setField(userRequestID);
	}
	
	public void set(SubscriptionRequestType type) {
		setField(type);
	}
	
}
