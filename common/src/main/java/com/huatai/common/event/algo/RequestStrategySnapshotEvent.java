package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class RequestStrategySnapshotEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	
	public RequestStrategySnapshotEvent(String key) {
		super(key);
	}
	
}
