package com.huatai.common.event.platform;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.base.EventPriority;

public class PluginReadyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final boolean ready;
	private final String name;
	
	public PluginReadyEvent(String key, String name, boolean ready) {
		super(key);
		this.name = name;
		this.ready = ready;
		setPriority(EventPriority.HIGH);
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public String getName() {
		return name;
	}
	
}
