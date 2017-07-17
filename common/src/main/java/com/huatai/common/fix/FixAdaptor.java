package com.huatai.common.fix;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quickfixj.CharsetSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.stream.IStreamAdaptor;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

public abstract class FixAdaptor<T extends Application> implements Application, IStreamAdaptor<T> {
	protected static final Logger log = LoggerFactory.getLogger(FixAdaptor.class);
	protected final FixConnection fixConnection;
	// each fix session is mapped to an IDownStreamConnection object
	protected final List<T> receivers = new ArrayList<T>();
	protected final Map<SessionID, T> map = new HashMap<SessionID, T>();
	private String charset = CharsetSupport.getDefaultCharset();
	
	public FixAdaptor(String fixSettings) throws UnsupportedEncodingException {
		this.fixConnection = new FixConnection(fixSettings);
	}
	
	abstract protected T createFixSession(SessionID session);
	
	@Override
	public void init() throws FixException {
		try {
			CharsetSupport.setCharset(charset);
			fixConnection.setApp(this);
			fixConnection.init();
			fixConnection.start();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw new FixException(e.getMessage());
		}
	}
	
	@Override
	public void uninit() {
		fixConnection.stop();
		receivers.clear();
		map.clear();
	}
	
	public String getAcceptHost() {
		return this.fixConnection.getAcceptHost();
	}
	
	public int getAcceptPort() {
		return this.fixConnection.getAcceptPort();
	}
	
	@Override
	public List<T> getReceivers() {
		return receivers;
	}
	
	@Override
	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		Application receiver = map.get(arg1);
		if (null == receiver) {
			log.warn("fromAdmin: Cant location receiver for this SessionID: {}", arg1);
			return;
		}
		receiver.fromAdmin(arg0, arg1);
	}
	
	@Override
	public void fromApp(Message arg0, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		Application receiver = map.get(arg1);
		if (null == receiver) {
			log.error("fromApp: Cant location receiver for this SessionID: {}", arg1);
			return;
		}
		receiver.fromApp(arg0, arg1);
	}
	
	@Override
	public void onCreate(SessionID sessionID) {
		log.debug("Adding FIX session: {}", sessionID);
		T receiver = createFixSession(sessionID);
		receiver.onCreate(sessionID);
		receivers.add(receiver);
		map.put(sessionID, receiver);
	}
	
	@Override
	public void onLogon(SessionID arg0) {
		Application receiver = map.get(arg0);
		if (null == receiver) {
			log.error("onLogon: Cant location receiver for this SessionID: {}", arg0);
			return;
		}
		receiver.onLogon(arg0);
	}
	
	@Override
	public void onLogout(SessionID arg0) {
		Application receiver = map.get(arg0);
		if (null == receiver) {
			log.error("onLogout: Cant location receiver for this SessionID: {}", arg0);
			return;
		}
		receiver.onLogout(arg0);
	}
	
	@Override
	public void toAdmin(Message arg0, SessionID arg1) {
		Application receiver = map.get(arg1);
		if (null == receiver) {
			log.error("toAdmin: Cant location receiver for this SessionID: {}", arg1);
			return;
		}
		receiver.toAdmin(arg0, arg1);
	}
	
	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
		Application receiver = map.get(arg1);
		if (null == receiver) {
			log.error("toApp: Cant location receiver for this SessionID: {}", arg1);
			return;
		}
		receiver.toApp(arg0, arg1);
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
