package com.huatai.platform.upstream.fix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huatai.common.SystemInfo;
import com.huatai.common.data.ErrorCode;
import com.huatai.common.fix.message.FixMessage;
import com.huatai.platform.upstream.common.IFixUpStreamConnection;
import com.huatai.platform.upstream.common.IUpStreamListener;
import com.huatai.platform.upstream.common.IUpStreamProcessor;
import com.huatai.platform.upstream.common.IUpStreamSender;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionID;

public class FixUpStreamConnection implements IFixUpStreamConnection {
	private static final Logger log = LoggerFactory.getLogger(FixUpStreamConnection.class);
	private final List<IUpStreamProcessor> upStreamProcessors;
	private final SessionID sessionID;
	private IUpStreamListener listener;
	private IUpStreamSender sender;
	
	public FixUpStreamConnection(SessionID sessionID, List<String> upStreamProcessorClasses, SystemInfo systemInfo) {
		this.sessionID = sessionID;
		upStreamProcessors = new ArrayList<IUpStreamProcessor>();
		
		for (String upStreamProcessorClass : upStreamProcessorClasses) {
			try {
				IUpStreamProcessor upStreamProcessor = (IUpStreamProcessor) Class.forName(upStreamProcessorClass).newInstance();
				upStreamProcessor.setSystemInfo(systemInfo);
				upStreamProcessors.add(upStreamProcessor);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				log.error("Failed to create instance for given class: {}, system is about to shutdown", upStreamProcessorClass);
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	@Override
	public IUpStreamSender setListener(IUpStreamListener listener) {
		this.listener = listener;
		if (listener == null) return null;
		
		try {
			listener.onState(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		sender = new FixUpStreamSender(sessionID);
		
		for (IUpStreamProcessor upStreamProcessor : upStreamProcessors) {
			upStreamProcessor.setSender(sender);
			upStreamProcessor.setListener(listener);
		}
		return sender;
	}
	
	@Override
	public void fromApp(Message message, SessionID sessionID) throws FieldNotFound {
		log.debug("Receive message={}, session={}", message, sessionID);
		FixMessage fixMessage = new FixMessage(message, sessionID);
		
		boolean processed = false;
		try {
			for (IUpStreamProcessor upStreamProcessor : upStreamProcessors) {
				processed = upStreamProcessor.processFixMessage(fixMessage);
				if (processed) {
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		if (!processed) {
			log.warn("Reject unsupported message={}, session={}", message, sessionID);
			sender.responseBusinessMessageReject(fixMessage.getMsgType().getValue(),
					ErrorCode.assemblyErrorInfo(ErrorCode.UnsupportedMessageTypeError, "unsupported message type"));
		}
	}
	
	@Override
	public void onLogon(SessionID sessionID) {
		log.debug("Session={} onLogon", sessionID);
		
		boolean on = true;
		sender.onState(on);
		try {
			if (listener != null) {
				listener.onState(on);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	@Override
	public void onLogout(SessionID sessionID) {
		log.debug("Session={} onLogout", sessionID);
		
		boolean on = false;
		sender.onState(on);
		try {
			if (listener != null) {
				listener.onState(on);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	@Override
	public String getId() {
		return sessionID.toString();
	}
	
	@Override
	public boolean getState() {
		return sender.getState();
	}
	
	@Override
	public void onCreate(SessionID sessionID) {}
	
	@Override
	public void toApp(Message message, SessionID sessionID) {}
	
	@Override
	public void fromAdmin(Message message, SessionID sessionID) {}
	
	@Override
	public void toAdmin(Message message, SessionID sessionID) {}
	
}
