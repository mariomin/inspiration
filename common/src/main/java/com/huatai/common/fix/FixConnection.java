package com.huatai.common.fix;

import static quickfix.Acceptor.SETTING_ACCEPTOR_TEMPLATE;
import static quickfix.Acceptor.SETTING_SOCKET_ACCEPT_ADDRESS;
import static quickfix.Acceptor.SETTING_SOCKET_ACCEPT_PORT;

import java.io.ByteArrayInputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.FileLogFactory;
import quickfix.LogFactory;
import quickfix.MemoryStoreFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RuntimeError;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.SocketInitiator;
import quickfix.mina.SessionConnector;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider.TemplateMapping;

public class FixConnection {
	private static final Logger log = LoggerFactory.getLogger(FixConnection.class);
	private final String settings;
	private Application app;
	private SessionConnector connection;
	private final Map<InetSocketAddress, List<TemplateMapping>> dynamicSessionMappings = new HashMap<InetSocketAddress, List<TemplateMapping>>();
	private String acceptHost;
	private int acceptPort = -1;
	
	public FixConnection(String settings) {
		this.settings = settings;
	}
	
	public String getAcceptHost() {
		return acceptHost;
	}
	
	public int getAcceptPort() {
		return acceptPort;
	}
	
	public void init() throws ConfigError, FieldConvertError {
		if (settings == null) {
			log.error("settings are null");
			throw new ConfigError("settings are null");
		}
		if (app == null) {
			log.error("application  is null");
			throw new ConfigError("application  is null");
		}
		
		SessionSettings sessionSettings = new SessionSettings(new ByteArrayInputStream(settings.getBytes()));
		String connectionType = sessionSettings.getString("ConnectionType");
		
		FileLogFactory logFactory = new FileLogFactory(sessionSettings);
		MessageFactory messageFactory = new ATSDefaultMessageFactory();
		MessageStoreFactory messageStoreFactory = new MemoryStoreFactory();
		
		if (connectionType.equals("acceptor")) {
			connection = new SocketAcceptor(app, messageStoreFactory, sessionSettings, logFactory, messageFactory);
			configureDynamicSessions(sessionSettings, app, messageStoreFactory, logFactory, messageFactory);
			try {
				acceptHost = sessionSettings.getString("SocketAcceptHost");
			} catch (ConfigError e) {
				acceptHost = "localhost";
			}
			acceptPort = (int) sessionSettings.getLong("SocketAcceptPort");
		} else if (connectionType.equals("initiator")) {
			connection = new SocketInitiator(app, messageStoreFactory, sessionSettings, logFactory, messageFactory);
		} else throw new ConfigError("unknow connection type:" + connectionType);
	}
	
	private void configureDynamicSessions(SessionSettings settings, Application application, MessageStoreFactory messageStoreFactory,
			LogFactory logFactory, MessageFactory messageFactory) throws ConfigError, FieldConvertError {
		
		Iterator<SessionID> sectionIterator = settings.sectionIterator();
		while (sectionIterator.hasNext()) {
			SessionID sessionID = sectionIterator.next();
			if (isSessionTemplate(settings, sessionID)) {
				InetSocketAddress address = getAcceptorSocketAddress(settings, sessionID);
				getMappings(address).add(new TemplateMapping(sessionID, sessionID));
			}
		}
		
		for (Map.Entry<InetSocketAddress, List<TemplateMapping>> entry : dynamicSessionMappings.entrySet()) {
			((SocketAcceptor) connection).setSessionProvider(entry.getKey(),
					new DynamicAcceptorSessionProvider(settings, entry.getValue(), application, messageStoreFactory, logFactory, messageFactory));
		}
	}
	
	private List<TemplateMapping> getMappings(InetSocketAddress address) {
		List<TemplateMapping> mappings = dynamicSessionMappings.get(address);
		if (mappings == null) {
			mappings = new ArrayList<TemplateMapping>();
			dynamicSessionMappings.put(address, mappings);
		}
		return mappings;
	}
	
	private InetSocketAddress getAcceptorSocketAddress(SessionSettings settings, SessionID sessionID) throws ConfigError, FieldConvertError {
		String acceptorHost = "0.0.0.0";
		if (settings.isSetting(sessionID, SETTING_SOCKET_ACCEPT_ADDRESS)) {
			acceptorHost = settings.getString(sessionID, SETTING_SOCKET_ACCEPT_ADDRESS);
		}
		int acceptorPort = (int) settings.getLong(sessionID, SETTING_SOCKET_ACCEPT_PORT);
		
		return new InetSocketAddress(acceptorHost, acceptorPort);
	}
	
	private boolean isSessionTemplate(SessionSettings settings, SessionID sessionID) throws ConfigError, FieldConvertError {
		return settings.isSetting(sessionID, SETTING_ACCEPTOR_TEMPLATE) && settings.getBool(sessionID, SETTING_ACCEPTOR_TEMPLATE);
	}
	
	public List<Session> getManagedSessions() {
		return connection.getManagedSessions();
	}
	
	public void start() throws RuntimeError, ConfigError {
		connection.start();
	}
	
	public void stop() {
		if (null != connection) {
			connection.stop(true);
		}
	}
	
	public Application getApp() {
		return app;
	}
	
	public void setApp(Application app) {
		this.app = app;
	}
	
	public List<SessionID> getSessions() {
		return connection.getSessions();
	}
	
}
