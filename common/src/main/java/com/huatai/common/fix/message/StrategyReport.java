package com.huatai.common.fix.message;

import quickfix.field.ClOrdID;
import quickfix.field.MsgType;
import quickfix.fix50sp2.Message;

public class StrategyReport extends Message {
	private static final long serialVersionUID = 1L;
	public static final String MSGTYPE = "UH102";
	
	public StrategyReport() {
		getHeader().setField(new MsgType(MSGTYPE));
	}
	
	public void set(ClOrdID clOrdID) {
		setField(clOrdID);
	}
	
}
