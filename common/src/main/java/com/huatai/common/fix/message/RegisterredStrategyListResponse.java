package com.huatai.common.fix.message;

import quickfix.field.MsgType;
import quickfix.fix50sp2.Message;

public class RegisterredStrategyListResponse extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH108";
	
	public RegisterredStrategyListResponse() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
}
