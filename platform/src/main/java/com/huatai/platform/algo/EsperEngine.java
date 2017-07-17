package com.huatai.platform.algo;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class EsperEngine {
	private final String config;
	private final String uri;
	private EPServiceProvider epService;
	
	public EsperEngine(String config, String uri) {
		this.config = config;
		this.uri = uri;
	}
	
	public void init() {
		if (epService == null) {
			Configuration configuration = new Configuration();
			configuration.configure(config);
			epService = EPServiceProviderManager.getProvider(uri, configuration);
			epService.initialize();
		}
	}
	
	public EPStatement createEPL(String stmt) {
		return epService.getEPAdministrator().createEPL(stmt);
	}
	
	public EPStatement createEPL(String stmt, String name) {
		return epService.getEPAdministrator().createEPL(stmt, name);
	}
	
	public void setSubscriber(EPStatement stmt, Object subscriber) {
		stmt.setSubscriber(subscriber);
	}
	
	public void addListener(EPStatement stmt, UpdateListener listener) {
		stmt.addListener(listener);
	}
	
	public void sendEvent(Object object) {
		epService.getEPRuntime().sendEvent(object);
	}
	
	public void uninit() {
		if (epService != null) {
			epService.destroy();
		}
	}
}
