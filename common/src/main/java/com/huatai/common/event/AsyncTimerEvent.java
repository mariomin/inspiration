package com.huatai.common.event;

import com.huatai.common.event.base.AsyncEvent;
import com.huatai.common.event.base.EventPriority;

public final class AsyncTimerEvent extends AsyncEvent {
	private static final long serialVersionUID = 1L;
	
	public AsyncTimerEvent() {
		super();
		setPriority(EventPriority.HIGH);
	}
	
}
