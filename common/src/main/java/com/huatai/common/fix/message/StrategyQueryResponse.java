package com.huatai.common.fix.message;

import com.huatai.common.fix.tag.NoStrategies;

import quickfix.field.MsgType;
import quickfix.field.UserRequestID;
import quickfix.fix50sp2.Message;

public class StrategyQueryResponse extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH106";
	
	public StrategyQueryResponse() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
	public void set(UserRequestID userRequestID) {
		setField(userRequestID);
	}
	
	public void set(NoStrategies records) {
		addGroup(records);
	}
}
