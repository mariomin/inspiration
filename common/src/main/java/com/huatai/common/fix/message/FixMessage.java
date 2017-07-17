package com.huatai.common.fix.message;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.StringField;
import quickfix.field.MsgType;

public class FixMessage {
	private final SessionID sessionID;
	private final Message message;
	private final StringField msgType;
	
	public FixMessage(Message message, SessionID sessionID) throws FieldNotFound {
		this.sessionID = sessionID;
		this.message = message;
		msgType = message.getHeader().getField(new MsgType());
	}
	
	public SessionID getSessionID() {
		return sessionID;
	}
	
	public Message getMessage() {
		return message;
	}
	
	public StringField getMsgType() {
		return msgType;
	}
	
}
