package com.huatai.common.fix.message;

import quickfix.field.ClOrdID;
import quickfix.field.MsgType;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.Message;

public class StrategyAlterRequest extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH100";
	
	public StrategyAlterRequest() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
	public StrategyAlterRequest(UserRequestID userRequestID) {
		this();
		setField(userRequestID);
	}
	
	public void set(ClOrdID clOrdID) {
		setField(clOrdID);
	}
}
