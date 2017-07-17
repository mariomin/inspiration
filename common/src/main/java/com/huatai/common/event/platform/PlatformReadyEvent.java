package com.huatai.common.event.platform;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.base.EventPriority;

public class PlatformReadyEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	private final boolean ready;
	
	public PlatformReadyEvent(String key, boolean ready) {
		super(key);
		this.ready = ready;
		setPriority(EventPriority.HIGH);
	}
	
	public boolean isReady() {
		return ready;
	}
	
}
