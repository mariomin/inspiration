package com.huatai.common.fix.message;

import quickfix.field.MsgType;
import quickfix.fix50sp2.Message;

public class RegisterredStrategyListRequest extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH107";
	
	public RegisterredStrategyListRequest() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
}
