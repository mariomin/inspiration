package com.huatai.common.fix.message;

import quickfix.field.MsgType;
import quickfix.field.Text;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.Message;

public class StrategySubscribeResponse extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH104";
	
	public StrategySubscribeResponse() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
	public StrategySubscribeResponse(UserRequestID userRequestID) {
		this();
		setField(userRequestID);
	}
	
	public void set(Text text) {
		setField(text);
	}
}
