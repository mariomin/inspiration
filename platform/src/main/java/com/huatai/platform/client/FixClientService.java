package com.huatai.platform.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

public class FixClientService implements IFixClientService {
	private static final Logger log = LoggerFactory.getLogger(FixClientService.class);
	private final SessionID sessionID;
	private IFixClientListener fixClientListener;
	private boolean state;
	
	public FixClientService(SessionID sessionID) {
		this.sessionID = sessionID;
	}
	
	@Override
	public void setFixClientListener(IFixClientListener fixClientListener) {
		this.fixClientListener = fixClientListener;
	}
	
	@Override
	public void sendMessage(Message message) throws Exception {
		Session.sendToTarget(message, sessionID);
	}
	
	@Override
	public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		log.info("Message from admin:{}", message.toString());
	}
	
	@Override
	public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		try {
			fixClientListener.processsFixMessage(message, sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onLogon(SessionID sessionId) {
		state = true;
	}
	
	@Override
	public void onLogout(SessionID sessionId) {
		state = false;
	}
	
	@Override
	public String getId() {
		return sessionID.toString();
	}
	
	@Override
	public boolean getState() {
		return state;
	}
	
	@Override
	public void onCreate(SessionID sessionId) {}
	
	@Override
	public void toAdmin(Message message, SessionID sessionId) {}
	
	@Override
	public void toApp(Message message, SessionID sessionId) throws DoNotSend {}
	
}
