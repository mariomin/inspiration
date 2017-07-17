package com.huatai.common.event.algo;

import com.huatai.common.event.base.AsyncEvent;

public class StrategySnapshotEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	
	public StrategySnapshotEvent(String key) {
		super(key);
		
	}
	
}
