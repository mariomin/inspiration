package com.huatai.common.fix.message;

import quickfix.field.MsgType;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.Message;

public class StrategyQueryRequest extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH105";
	
	public StrategyQueryRequest() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
	public void set(UserRequestID userRequestID) {
		setField(userRequestID);
	}
	
}
